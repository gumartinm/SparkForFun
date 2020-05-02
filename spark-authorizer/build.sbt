ThisBuild / scalaVersion := "2.11.8"
ThisBuild / organization := "de.example.playground.spark.authorizer"
ThisBuild / version := "0.1.0-SNAPSHOT"
name := "spark-authorizer"

resolvers += Resolver.mavenLocal

val sparkVersion = "2.4.5"

// Logging
libraryDependencies +="com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"

// Spark
libraryDependencies +="org.apache.spark" %% "spark-sql" % sparkVersion % Provided
libraryDependencies +="org.apache.spark" %% "spark-core" % sparkVersion % Provided
libraryDependencies +="org.apache.spark" %% "spark-hive" % sparkVersion % Provided


// Spark Authorizer (local build)
// https://github.com/apache/submarine/blob/master/submarine-security/spark-security/
libraryDependencies += "org.apache.submarine" % "submarine-spark-security" % "0.4.0-SNAPSHOT" % Provided
libraryDependencies += "net.java.dev.jna" % "jna" % "5.2.0" % Provided
libraryDependencies += "net.java.dev.jna" % "jna-platform" % "5.2.0" % Provided
libraryDependencies += "org.apache.ranger" % "ranger-plugins-common" % "2.0.0" % Provided intransitive()
libraryDependencies += "com.kstruct" % "gethostname4j" % "0.0.2" % Provided
libraryDependencies += "com.sun.jersey" % "jersey-bundle" % "1.19.3" % Provided

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

