// Author: Gustavo Martin Morcuende
package de.example.databases.timezones

class SqlServerTimezoneTest extends SharedSparkSessionHelper {

  it should "create json files from database" in {
    val dbName = "src_databasename"
    val tableName = "Table"

    val createDatabaseStatement = s"CREATE DATABASE IF NOT EXISTS $dbName"

    val createTableStatement = s"CREATE TABLE IF NOT EXISTS $dbName.$tableName " +
      "USING org.apache.spark.sql.jdbc " +
      "OPTIONS ( " +
      "driver \"de.example.databases.timezones.TimeZonedSQLServerDriver\", " +
      "url \"jdbc:timezoned_sqlserver://hostname:1433;databaseName=DATABASENAME\", " +
      "dbtable \"DATABASENAME.dbo.Table\", " +
      "user \"username\", " +
      "password \"password\", " +
      "fetchsize 100" +
      ")"

    val createTableLanding = "CREATE TABLE IF NOT EXISTS landing_databasename.Table " +
      "( CancellationDate    TIMESTAMP, " +
      "  dt                        INT " +
      ") " +
      "USING json " +
      "OPTIONS( path 'sqlserver/landing/databasename/Table' ) " +
      "PARTITIONED BY (dt)"



    val insert = "INSERT OVERWRITE TABLE landing_databasename.Table PARTITION (dt) " +
      " SELECT " +
      "  CancellationDate, " +
      "  date_format(CancellationDate, 'yyyyMMdd') as dt " +
      " FROM src_databasename.Table " +
      "LIMIT 10 "


    spark.sql(createDatabaseStatement)
    spark.sql("CREATE DATABASE IF NOT EXISTS landing_databasename")
    spark.sql(createTableStatement)
    spark.sql(createTableLanding)
    spark.sql(insert)
  }
}
