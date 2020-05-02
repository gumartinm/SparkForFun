// Author: Gustavo Martin Morcuende
package de.example.playground.spark.ranger

import de.example.playground.spark.commons.SharedSparkSessionHelper
import org.apache.spark.SparkConf

class SparkRangerTest extends SharedSparkSessionHelper {

  protected override def sparkConf: SparkConf = {
    val conf = super.sparkConf
    conf.set("spark.sql.extensions", "org.apache.submarine.spark.security.api.RangerSparkSQLExtension")
    conf.set("hive.security.authorization.enabled", "true")
    //conf.set("hive.security.authorization.manager", "org.apache.ranger.authorization.hive.authorizer.RangerHiveAuthorizerFactory")
    //conf.set("hive.security.authenticator.manager", "org.apache.hadoop.hive.ql.security.SessionStateUserAuthenticator")
    conf.set("hive.conf.restricted.list", "hive.security.authorization.enabled,hive.security.authorization.manager,hive.security.authenticator.manager")
    //conf.set("spark.hadoop.hive.metastore.uris", "thrift://192.168.0.214:9083,thrift://192.168.0.115:9083") // Connector does not work with embedded Hive
    //conf.set("spark.sql.warehouse.dir", "/apps/hive/warehouse")
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
