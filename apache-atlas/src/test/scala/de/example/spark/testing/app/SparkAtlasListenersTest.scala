// Author: Gustavo Martin Morcuende
package de.example.spark.testing.app

import de.example.spark.testing.app.SparkAtlasListenersTest._
import de.example.spark.testing.SharedSparkSessionHelper
import org.apache.spark.SparkConf
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SaveMode}

private object SparkAtlasListenersTest {
  val PartitionColumnFirstValue = "first"
  val PartitionColumnSecondValue = "second"
  val NameColumnBerenValue = "Beren"
  val NameColumnLuthienValue = "Lúthien"
  val ConditionExprFirstDataFrame = s"df = '$PartitionColumnFirstValue'"
  val conditionExprSecondDataFrame = s"df = '$PartitionColumnSecondValue'"
  val DbName = "gustavo"
  val TableName = "example"
  val PartitionColumnName = "df"
  val OriginPartitionColumnName = "df_origin"
  val SurNameColumnName = "surname"
  val NameColumnName = "name"
  val FatherColumnName = "father"
  val CreateDatabaseSqlStatement = s"CREATE DATABASE IF NOT EXISTS $DbName"
  val WaitForSparkAtlasConnector = 10000
}

class SparkAtlasListenersTest extends SharedSparkSessionHelper {

  protected override def sparkConf: SparkConf = {
    val conf = super.sparkConf
    conf
      .set("spark.extraListeners", "com.hortonworks.spark.atlas.SparkAtlasEventTracker")
      .set("spark.sql.queryExecutionListeners", "com.hortonworks.spark.atlas.SparkAtlasEventTracker")
  }

  it should "create table Hive with Spark" in {
    val schema = "gustavo string, years bigint"
    val createTableStatement =
      s"CREATE TABLE IF NOT EXISTS $DbName.$TableName " +
        s"($schema) " +
        "USING JSON " +
        s"LOCATION '$path' " +
        "PARTITIONED BY (years) "


    sparkSession.sql(CreateDatabaseSqlStatement)
    sparkSession.sql(createTableStatement)

    // Giving time to SparkAtlasEventTracker for sending events because it has its own background threads.
    Thread.sleep(WaitForSparkAtlasConnector)
  }

  it should "create table Hive with succes" in {
    val schema = "gustavo string, years bigint"
    val createTableStatement =
      s"CREATE TABLE IF NOT EXISTS $DbName.$TableName " +
        s"($schema) " +
        "USING HIVE " +
        "OPTIONS " +
        "( " +
        "'serde' 'org.apache.hive.hcatalog.data.JsonSerDe', " +
        s"'path' '$path' " +
        " ) "

    sparkSession.sql(CreateDatabaseSqlStatement)
    sparkSession.sql(createTableStatement)

  }

  private def saveAsTable(dataFrame: DataFrame,
                          dbName: String,
                          tableName: String,
                          path: String,
                          partitions: String*): Unit = {
    dataFrame.write
      .mode(SaveMode.Overwrite)
      .option("path", path)
      .format(source = "parquet")
      .partitionBy(partitions: _*)
      .saveAsTable(tableName = s"$dbName.$tableName")
  }

  private def createSecondDataFrameAsColumn: DataFrame = {

    val dataFrame = sparkSession.createDataFrame(
      sparkContext.parallelize(
        Seq(
          Row(NameColumnLuthienValue, "Tinúviel", "Elu Thingol", PartitionColumnSecondValue)
        )),
      StructType(
        List(
          StructField(NameColumnName, StringType, nullable = true),
          StructField(SurNameColumnName, StringType, nullable = true),
          StructField(FatherColumnName, StringType, nullable = true),
          StructField(OriginPartitionColumnName, StringType, nullable = true)
        )
      )
    )
    dataFrame.withColumn(PartitionColumnName, col(OriginPartitionColumnName))
  }
}
