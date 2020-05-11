// Author: Gustavo Martin Morcuende
package de.example.playground.spark.ranger

import de.example.playground.spark.commons.SharedSparkSessionHelper
import org.apache.spark.SparkConf

class CustomMetaStoreEventListenerTest extends SharedSparkSessionHelper {

  protected override def sparkConf: SparkConf = {
    val conf = super.sparkConf
    conf.set("hive.metastore.event.listeners", "de.example.playground.metastore.listeners.CustomMetaStoreEventListener")
    conf.set("hive.metastore.end.function.listeners",
             "de.example.playground.metastore.listeners.CustomMetaStoreEndFunctionListener")
  }

  it should "create table Hive using spark" in {

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
