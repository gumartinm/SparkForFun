// Author: Gustavo Martin Morcuende
package de.example.playground.spark.authorizer
import de.example.playground.spark.commons.SharedSparkSessionHelper
import org.apache.spark.SparkConf

class SimpleAuthorizerTest extends SharedSparkSessionHelper {

  protected override def sparkConf: SparkConf = {
    val conf = super.sparkConf
    conf.set("spark.sql.extensions", "de.example.playground.spark.authorizer.SimpleAuthorizerSQLExtension")
  }

  it should "throw NotAuthorizedException" in {
    the[NotAuthorizedException] thrownBy {
      val dbName = "gustavo"
      val tableName = "example"

      createTable(dbName, tableName)

      val tableExists: Boolean = spark.catalog.tableExists(dbName, tableName)
      spark.sql(s"select * from $dbName.$tableName")
      tableExists shouldBe true
        spark.sql("show create table gustavo.example")
    } should have message "You are not allowed to run this command"
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
