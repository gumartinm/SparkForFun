// Author: Gustavo Martin Morcuende
package de.example.spark.testing.job

import com.typesafe.scalalogging.LazyLogging
import de.example.spark.testing.job.AwesomeJob.{Database, Table}
import de.example.spark.testing.service.AwesomeService
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

private object AwesomeJob {
  private val Database = "testing"
  private val Table = "example"
}

class AwesomeJob(sourcePath: String, destinationPath: String, awesomeService: AwesomeService)(
    implicit sparkSession: SparkSession)
    extends LazyLogging {

  def run(): Unit = {
    logger.info("Running AwesomeJob")

    val jsonSchema = StructType(
      Array(
        StructField("name", StringType),
        StructField("surname", StringType)
      )
    )
    val dataFrame = sparkSession.read.schema(jsonSchema).json(sourcePath)
    val schema = dataFrame.schema

    val upperCaseSchema = awesomeService.renameColumnsToUpperCase(schema)
    val upperCaseDataFrame = sparkSession.createDataFrame(dataFrame.rdd, upperCaseSchema)

    sparkSession.sql(s"CREATE DATABASE IF NOT EXISTS $Database")
    sparkSession.sql(s"""
                        |CREATE TABLE IF NOT EXISTS `$Database`.`$Table` (`NAME` STRING, `SURNAME` STRING)
                        |USING PARQUET
                        |OPTIONS (
                        |  path '$destinationPath'
                        |)
                        |""".stripMargin)
    upperCaseDataFrame.write
      .mode(SaveMode.Overwrite)
      .insertInto(s"$Database.$Table")

    logger.info("End running AwesomeJob")
  }
}
