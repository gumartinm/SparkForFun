// Author: Gustavo Martin Morcuende
package de.example.spark.testing.job

import com.typesafe.scalalogging.LazyLogging
import de.example.spark.testing.service.AwesomeService
import org.apache.spark.sql.SparkSession

class AwesomeJob(sourcePath: String, destinationPath: String, awesomeService: AwesomeService)(
    implicit sparkSession: SparkSession)
    extends LazyLogging {

  def run(): Unit = {
    logger.info("Running AwesomeJob")

    val jsonDataFrame = sparkSession.read.json(sourcePath)
    val jsonSchema = jsonDataFrame.schema
    val upperCaseJsonSchema = awesomeService.renameColumnsToUpperCase(jsonSchema)
    val upperCaseJsonDataFrame = sparkSession.createDataFrame(jsonDataFrame.rdd, upperCaseJsonSchema)
    upperCaseJsonDataFrame.write.json(destinationPath)

    logger.info("End running AwesomeJob")
  }
}
