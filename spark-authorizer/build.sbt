ThisBuild / scalaVersion := "2.11.8"
ThisBuild / organization := "de.example.playground.spark.authorizer"
ThisBuild / version := "0.1.0-SNAPSHOT"
name := "spark-authorizer"

resolvers += Resolver.mavenLocal
// resolvers += "Hortonworks" at "https://repo.hortonworks.com/content/repositories/releases"

// val sparkVersion = "2.3.2.3.1.0.0-78"
val sparkVersion = "2.4.4"

// Logging
libraryDependencies +="com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"

// Spark
libraryDependencies +="org.apache.spark" %% "spark-sql" % sparkVersion
libraryDependencies +="org.apache.spark" %% "spark-core" % sparkVersion
libraryDependencies +="org.apache.spark" %% "spark-hive" % sparkVersion
libraryDependencies +="com.fasterxml.jackson.core" % "jackson-core" % "2.6.7"
libraryDependencies +="com.fasterxml.jackson.core" % "jackson-databind" % "2.6.7"
libraryDependencies +="com.fasterxml.jackson.core" % "jackson-annotations" % "2.6.7"

// Hive
// libraryDependencies +="org.apache.hive.hcatalog" % "hive-hcatalog-core" % "3.1.0.3.1.0.0-78" exclude("org.spark-project.hive", "hive-exec")
libraryDependencies +="org.spark-project.hive.hcatalog" % "hive-hcatalog-core" % "1.2.1.spark2"



// dependencyOverrides += "org.apache.hadoop" % "hadoop-aws" % "3.1.1.3.1.0.0-78"
// excludeDependencies += "org.spark-project.hive" % "hive-exec"

libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % "2.10.0"


// Spark Authorizer (local build)
// https://github.com/yaooqinn/spark-authorizer
libraryDependencies += "yaooqinn" % "spark-authorizer" % "2.1.1" intransitive()
libraryDependencies +=  "org.apache.ranger" % "ranger-hive-plugin" % "1.2.0" intransitive()
libraryDependencies += "org.apache.ranger" % "ranger-plugins-common" % "1.2.0" intransitive()
libraryDependencies += "org.apache.ranger" % "ranger-plugins-audit" % "1.2.0" intransitive()
libraryDependencies += "org.apache.httpcomponents" % "httpcore" % "4.4.6" intransitive()

// Test
libraryDependencies +="org.scalatest" %% "scalatest" % "3.0.5" % Test
libraryDependencies +="org.mockito" %% "mockito-scala" % "1.0.4" % Test
libraryDependencies +="junit" % "junit" % "4.12" % Test
libraryDependencies +="org.scalacheck" %% "scalacheck" % "1.14.2" % Test


scalastyleFailOnError := true
scalastyleFailOnWarning := true
(scalastyleFailOnError in Test) := true
(scalastyleFailOnWarning in Test) := true

