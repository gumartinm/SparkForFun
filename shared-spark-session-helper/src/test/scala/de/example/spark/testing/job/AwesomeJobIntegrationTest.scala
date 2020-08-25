// Author: Gustavo Martin Morcuende
package de.example.spark.testing.job

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import de.example.spark.testing.SharedSparkSessionHelper
import de.example.spark.testing.service.AwesomeService
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.mockito.MockitoSugar
import org.mockito.scalatest.ResetMocksAfterEachTest

class AwesomeJobIntegrationTest
    extends SharedSparkSessionHelper
    with MockitoSugar
    with ResetMocksAfterEachTest
    with DataFrameSuiteBase {

  // Each set of tests may run with its own Spark configuration in an isolated way.
  override def sparkConf: SparkConf = {
    val conf = super.sparkConf
    conf.set("spark.sql.sources.partitionOverwriteMode", "dynamic")
  }

  it should "run awesome job with success" in {
    val sourcePath = getClass.getResource("/awesomejob/sourcepath//").toString
    val destinationPath = path + "/awesomejob/destinationpath/"
    val awesomeService = mock[AwesomeService]
    val schema = StructType(
      List(
        StructField("name", StringType),
        StructField("surname", StringType)
      )
    )
    val expectedSchema = StructType(
      List(
        StructField("NAME", StringType),
        StructField("SURNAME", StringType)
      )
    )
    when(awesomeService.renameColumnsToUpperCase(schema)).thenReturn(expectedSchema)

    val awesomeJob = new AwesomeJob(sourcePath, destinationPath, awesomeService)
    awesomeJob.run()

    val resultDataFrame = sparkSession.read.json(destinationPath)
    val expectedDataFrame = createExpectedDataFrame

    verify(awesomeService, times(wantedNumberOfInvocations = 1)).renameColumnsToUpperCase(schema)
    assertDataFrameEquals(expectedDataFrame, resultDataFrame)
  }

  private def createExpectedDataFrame: DataFrame =
    sparkSession.createDataFrame(
      sparkContext.parallelize(
        Seq(
          Row("John", "Doe"),
          Row("Jane", "Doe")
        )),
      StructType(
        List(
          StructField("NAME", StringType),
          StructField("SURNAME", StringType)
        )
      )
    )
}
