// Author: Gustavo Martin Morcuende
package de.example.databases.timezones.oracle

class OracleTimezoneTest extends SharedSparkSessionHelper {

  it should "create json files from database" in {

    val dbName = "src_databasename"
    val tableName = "TABLE"

    val createDatabaseStatement = s"CREATE DATABASE IF NOT EXISTS $dbName"

    val createTableStatement = s"CREATE TABLE IF NOT EXISTS $dbName.$tableName " +
      "USING org.apache.spark.sql.jdbc " +
      "OPTIONS ( " +
      "driver \"oracle.jdbc.driver.TimeZonedOracleDriver\", " +
      "url \"jdbc:timezoned_oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=HOST.1)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=HOST.2)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=SERVICENAME)))\", " +
      "dbtable \"DATABASENAME.Table\", " +
      "user \"user\", " +
      "password \"password\", " +
      "fetchsize 100000 " +
      ")"

    val createTableLanding = "CREATE TABLE IF NOT EXISTS landing_databasename.Table " +
      "( SomeDate    TIMESTAMP " +
      "dt                  INT, " +
      "hour                INT " +
      ") " +
      "USING json " +
      "OPTIONS( path 'oracle/landing/databasename/Table' ) " +
      "PARTITIONED BY (dt, hour)"



    val insert = "INSERT OVERWRITE TABLE landing_databasename.Table PARTITION (dt, hour) " +
      "( SomeDate    TIMESTAMP " +
      "  date_format(SomeDate, 'yyyyMMdd') as dt, " +
      "  date_format(SomeDate, 'HH') as hour, " +
    " FROM src_databasename.Table " +
    " LIMIT 10"

    spark.sql(createDatabaseStatement)
    spark.sql("CREATE DATABASE IF NOT EXISTS landing_databasename")
    spark.sql(createTableStatement)
    spark.sql(createTableLanding)
    spark.sql(insert)
  }
}
