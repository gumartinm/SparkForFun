// Author: Gustavo Martin Morcuende
package de.example.spark.testing.app

import de.example.spark.testing.job.AwesomeJob
import de.example.spark.testing.service.AwesomeService
import org.apache.spark.sql.SparkSession

object AwesomeApp extends App {
  private val sourcePath = args(0) // "s3a://some/awesome/source/path/"
  private val destinationPath = args(1) // "s3a://some/awesome/destination/path/"

  private implicit val sparkSession: SparkSession = SparkSession
    .builder()
    .appName("awesome-app")
    .enableHiveSupport()
    .getOrCreate()

  private val awesomeService = new AwesomeService

  new AwesomeJob(sourcePath, destinationPath, awesomeService).run()
}
