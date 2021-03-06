// Author: Gustavo Martin Morcuende
package org.apache.spark.metrics.sink

import org.apache.spark.SparkConf

class CustomStatsDSinkTest extends SharedSparkSessionHelper {

  protected override def sparkConf: SparkConf = {
    val conf = super.sparkConf
    // override default spark.app.id value in metrics :)
    conf.set("spark.metrics.namespace", "DATADOG_TEST")
  }

  it should "create table Hive using spark ranger" in {

    val dbName = "gustavo"
    val tableName = "example"

    createTable(dbName, tableName)

    val tableExists: Boolean = spark.catalog.tableExists(dbName, tableName)
    tableExists shouldBe true
  }

  private def createTable(dbName: String, tableName: String): Unit = {
    val dbName = "gustavo"
    val tableName = "example"
    val schema = "gustavo string, years bigint"
    val createDatabaseStatement = s"CREATE DATABASE IF NOT EXISTS $dbName"
    val createTableStatement =
      s"CREATE TABLE IF NOT EXISTS $dbName.$tableName " +
        s"($schema) " +
        "USING JSON " +
        "OPTIONS " +
        "( " +
        "'serialization.format' '1', " +
        s"'path' '$path' " +
        " ) "

    spark.sql(createDatabaseStatement)
    spark.sql(createTableStatement)
  }
}
