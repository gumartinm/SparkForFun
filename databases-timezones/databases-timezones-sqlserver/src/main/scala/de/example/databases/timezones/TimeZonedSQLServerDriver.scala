// Author: Gustavo Martin Morcuende
package de.example.databases.timezones

import java.sql.{Connection, Driver, DriverPropertyInfo}
import java.util.Properties
import java.util.logging.Logger

import com.microsoft.sqlserver.jdbc.SQLServerDriver

// SQLServer using Europe/Madrid. Spark using UTC.
class TimeZonedSQLServerDriver extends Driver {
  val delegate = new SQLServerDriver()

  override def connect(url: String, properties: Properties): Connection = {
    val connection = delegate.connect(translateUrl(url), properties)
    new TimeZonedSQLServerConnection(connection)
  }

  override def acceptsURL(url: String): Boolean = delegate.acceptsURL(translateUrl(url))

  override def getPropertyInfo(url: String, properties: Properties): Array[DriverPropertyInfo] =
    delegate.getPropertyInfo(translateUrl(url), properties)

  override def getMajorVersion: Int = delegate.getMajorVersion

  override def getMinorVersion: Int = delegate.getMinorVersion

  override def jdbcCompliant(): Boolean = delegate.jdbcCompliant()

  override def getParentLogger: Logger = delegate.getParentLogger

  private def translateUrl(url: String): String = url.replace("timezoned_sqlserver", "sqlserver")
}
