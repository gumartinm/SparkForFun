// Author: Gustavo Martin Morcuende
package oracle.jdbc.driver

import java.sql.{Connection, Driver, DriverPropertyInfo}
import java.util.logging.Logger
import java.util.{Properties, TimeZone}

// Oracle using Europe/Madrid. Spark using UTC.

class TimeZonedOracleDriver extends Driver {
  val delegate = new OracleDriver()

  override def connect(url: String, properties: Properties): Connection = {
    val connection = delegate.connect(translateUrl(url), properties)

    val timeZone = TimeZone.getTimeZone("Europe/Madrid")
    val oracleConnection = connection.asInstanceOf[T4CConnection]
    oracleConnection.setDefaultTimeZone(timeZone)

    connection
  }

  override def acceptsURL(url: String): Boolean = delegate.acceptsURL(translateUrl(url))

  override def getPropertyInfo(url: String, properties: Properties): Array[DriverPropertyInfo] =
    delegate.getPropertyInfo(translateUrl(url), properties)

  override def getMajorVersion: Int = delegate.getMajorVersion

  override def getMinorVersion: Int = delegate.getMinorVersion

  override def jdbcCompliant(): Boolean = delegate.jdbcCompliant()

  override def getParentLogger: Logger = delegate.getParentLogger

  private def translateUrl(url: String): String = url.replace("timezoned_oracle", "oracle")
}
