ThisBuild / scalaVersion := "2.11.8"
ThisBuild / organization := "de.example.playground"
ThisBuild / version := "0.1.0-SNAPSHOT"
name := "playground"

val sparkVersion = "2.3.2"


val commonsProjectName = "playground-commons"
lazy val commonsProject = (project in file(commonsProjectName))
  .withId(commonsProjectName)
  .enablePlugins(GitVersioning, GitBranchPrompt)
  .settings(
    name := commonsProjectName,
    organization := "de.example.playground.commons",
    settings,
    libraryDependencies ++= commonDependencies,
    publishArtifact in Test := true
  )

val sparkHiveProjectName = "playground-spark-hive"
lazy val sparkHiveProject = (project in file(sparkHiveProjectName))
  .withId(sparkHiveProjectName)
  .enablePlugins(GitVersioning, GitBranchPrompt)
  .settings(
    name := sparkHiveProjectName,
    organization := "de.example.playground.spark.hive",
    settings,
    libraryDependencies ++= commonDependencies
  )
  .dependsOn(
    commonsProject,
    commonsProject % "compile->compile;test->test"
  )

val sparkKafkaProjectName = "playground-spark-kafka"
lazy val sparkKafkaProject = (project in file(sparkKafkaProjectName))
  .withId(sparkKafkaProjectName)
  .enablePlugins(GitVersioning, GitBranchPrompt)
  .settings(
    name := sparkKafkaProjectName,
    organization := "de.example.playground.spark.kafka",
    settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      // Spark Kafka
      "org.apache.spark" % "spark-sql-kafka-0-10_2.11" % sparkVersion
    )
  )
  .dependsOn(
    commonsProject,
    commonsProject % "compile->compile;test->test"
  )

val sparkAtlasProjectName = "playground-spark-atlas"
lazy val sparkAtlasProject = (project in file(sparkAtlasProjectName))
  .withId(sparkAtlasProjectName)
  .enablePlugins(GitVersioning, GitBranchPrompt)
  .settings(
    name := sparkAtlasProjectName,
    organization := "de.example.playground.spark.atlas",
    settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      // Atlas Connector (local build)
      "com.hortonworks.spark" %% "spark-atlas-connector" % "0.1.0-SNAPSHOT" intransitive(),
      "org.apache.atlas" % "atlas-client-v2" % "2.0.0" intransitive(),
      "org.apache.atlas" % "atlas-intg" % "2.0.0" intransitive(),
      "org.apache.atlas" % "atlas-client-common" % "2.0.0" intransitive(),
      "com.sun.jersey.contribs" % "jersey-multipart" % "1.19",
      "com.fasterxml.jackson.jaxrs" % "jackson-jaxrs-json-provider" % "2.6.7"
    )
  )
  .dependsOn(
    commonsProject,
    commonsProject % "compile->compile;test->test"
  )

val sparkRangerProjectName = "playground-spark-ranger"
lazy val sparkRangerProject = (project in file(sparkRangerProjectName))
  .withId(sparkRangerProjectName)
  .enablePlugins(GitVersioning, GitBranchPrompt)
  .settings(
    name := sparkRangerProjectName,
    organization := "de.example.playground.spark.ranger",
    settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      // Spark Authorizer (local build)
      "yaooqinn" % "spark-authorizer" % "2.1.1" intransitive(),
      "org.apache.ranger" % "ranger-hive-plugin" % "1.2.0" intransitive(),
      "org.apache.ranger" % "ranger-plugins-common" % "1.2.0" intransitive(),
      "org.apache.ranger" % "ranger-plugins-audit" % "1.2.0" intransitive(),
      "org.apache.httpcomponents" % "httpcore" % "4.4.6" intransitive()
    )
  )
  .dependsOn(
    commonsProject,
    commonsProject % "compile->compile;test->test"
  )


lazy val commonDependencies = Seq(
  // Hortonworks Atlas Connector
  // val sparkVersion = "2.3.2"

  // Logging
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",

  // Spark
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "com.fasterxml.jackson.core" % "jackson-core" % "2.6.7",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.6.7",

  // Hive
  "org.apache.hive.hcatalog" % "hive-hcatalog-core" % "1.2.1",

  // Test
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "org.mockito" %% "mockito-scala" % "1.0.4" % Test,
  "junit" % "junit" % "4.12" % Test,
  "org.scalacheck" %% "scalacheck" % "1.14.2" % Test
)

lazy val settings = scalaStyleSettings ++ commonSettings

lazy val scalaStyleSettings =
  Seq(
    scalastyleFailOnError := true,
    scalastyleFailOnWarning := true,
    (scalastyleFailOnError in Test) := true,
    (scalastyleFailOnWarning in Test) := true
  )

lazy val commonSettings = Seq(
  resolvers += Resolver.mavenLocal,
  resolvers += "Spring Plugins" at "https://repo.spring.io/plugins-release/",
  git.useGitDescribe := true,
  // Testing
  testForkedParallel in Test := false,
  testForkedParallel in IntegrationTest := false,
  // Always use in Test := true, otherwise tests run in the same process as sbt-launcher and
  // sometimes there are classpath problems because of it :(
  fork in Test := true,
  parallelExecution in Test := false
)
