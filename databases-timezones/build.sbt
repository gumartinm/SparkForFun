ThisBuild / scalaVersion := "2.11.8"
ThisBuild / organization := "de.example.databases.timezones"
ThisBuild / version := "0.1.0-SNAPSHOT"
name := "databases-timezones"


val sparkVersion = "2.4.0"

val sparkHiveProjectName = "databases-timezones-sqlserver"
lazy val sparkHiveProject = (project in file(sparkHiveProjectName))
  .withId(sparkHiveProjectName)
  .enablePlugins(GitVersioning, GitBranchPrompt)
  .settings(
    name := sparkHiveProjectName,
    organization := "de.example.databases.timezones.sqlserver",
    settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      // SQLServer
      "com.microsoft.sqlserver" % "mssql-jdbc" % "8.2.1.jre8"
    )
  )

lazy val commonDependencies = Seq(
  // Logging
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",

  // Spark
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "com.fasterxml.jackson.core" % "jackson-core" % "2.6.7",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.6.7",

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
    scalastyleConfig := file("scalastyle_config.xml"),
    (scalastyleFailOnError in Test) := true,
    (scalastyleFailOnWarning in Test) := true
  )


import sbt.Package.ManifestAttributes
import com.typesafe.sbt.SbtGit.git

lazy val commonSettings = Seq(
  resolvers += Resolver.mavenLocal,

  git.useGitDescribe := true,

  // Testing
  testForkedParallel in Test := false,
  testForkedParallel in IntegrationTest := false,
  // Always use in Test := true, otherwise tests run in the same process as sbt-launcher and
  // sometimes there are classpath problems because of it :(
  fork in Test := true,
  parallelExecution in Test := false,

  // Assembly
  test in assembly := {},
  packageOptions := Seq(ManifestAttributes(("Repository-Commit", git.gitHeadCommit.value.get)))
)
