ThisBuild / scalaVersion := "2.11.8"
ThisBuild / organization := "de.example.playground.metastore.listeners"
ThisBuild / version := "0.1.0-SNAPSHOT"
name := "metastore-listeners"

resolvers += Resolver.mavenLocal

val sparkVersion = "2.4.5"

// Logging
libraryDependencies +="com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"

// Spark
libraryDependencies +="org.apache.spark" %% "spark-sql" % sparkVersion % Provided
libraryDependencies +="org.apache.spark" %% "spark-core" % sparkVersion % Provided
libraryDependencies +="org.apache.spark" %% "spark-hive" % sparkVersion % Provided


// Test
libraryDependencies +="org.scalatest" %% "scalatest" % "3.0.5" % Test
libraryDependencies +="org.mockito" %% "mockito-scala" % "1.0.4" % Test
libraryDependencies +="junit" % "junit" % "4.12" % Test
libraryDependencies +="org.scalacheck" %% "scalacheck" % "1.14.2" % Test

// Settings

// Scalastyle
scalastyleFailOnError := true
scalastyleFailOnWarning := true
(scalastyleFailOnError in Test) := true
(scalastyleFailOnWarning in Test) := true

// Testing
testForkedParallel in Test := false
testForkedParallel in IntegrationTest := false
fork in Test := true
parallelExecution in Test := false

// Assembly
import sbt.Package.ManifestAttributes
import com.typesafe.sbt.SbtGit.git
test in assembly := {}
packageOptions := Seq(ManifestAttributes(("Repository-Commit", git.gitHeadCommit.value.get)))

