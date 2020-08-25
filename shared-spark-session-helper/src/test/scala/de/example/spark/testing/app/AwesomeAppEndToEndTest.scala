// Author: Gustavo Martin Morcuende
package de.example.spark.testing.app

import com.holdenkarau.spark.testing.DataFrameSuiteBase
import de.example.spark.testing.SharedSparkSessionHelper
import org.apache.spark.SparkConf
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row}

class AwesomeAppEndToEndTest extends SharedSparkSessionHelper with DataFrameSuiteBase {

  // Each set of tests may run with its own Spark configuration in an isolated way.
  override def sparkConf: SparkConf = {
    val conf = super.sparkConf
    conf
  }

  it should "run awesome app with success" in {
    val sourcePath = getClass.getResource("/sourcepath/awesomejob/").toString
    val destinationPath = path + "/destinationpath/awesomejob/"
    val args = Array(sourcePath, destinationPath)

    AwesomeApp.main(args)

    val resultDataFrame = sparkSession.read.json(destinationPath)
    val expectedDataFrame = createExpectedDataFrame
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
