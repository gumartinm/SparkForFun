// Author: Gustavo Martin Morcuende
package de.example.databases.timezones

import java.io.{InputStream, Reader}
import java.net.URL
import java.sql
import java.sql.{Blob, Clob, Connection, Date, NClob, ParameterMetaData, PreparedStatement, Ref, ResultSet,
                 ResultSetMetaData, RowId, SQLWarning, SQLXML, Time, Timestamp}
import java.util.Calendar

// SQLServer using Europe/Madrid. Spark using UTC.

// scalastyle:off number.of.methods
class TimeZonedSQLPreparedStatement(delegate: PreparedStatement) extends PreparedStatement {

  override def executeQuery(): ResultSet = {
    val resultSet = delegate.executeQuery()
    new TimeZonedSQLServerResultSet(resultSet)
  }

  override def executeUpdate(): Int = delegate.executeUpdate()
  override def setNull(parameterIndex: Int, sqlType: Int): Unit = delegate.setNull(parameterIndex, sqlType)
  override def setBoolean(parameterIndex: Int, x: Boolean): Unit = delegate.setBoolean(parameterIndex, x)
  override def setByte(parameterIndex: Int, x: Byte): Unit = delegate.setByte(parameterIndex, x)
  override def setShort(parameterIndex: Int, x: Short): Unit = delegate.setShort(parameterIndex, x)
  override def setInt(parameterIndex: Int, x: Int): Unit = delegate.setInt(parameterIndex, x)
  override def setLong(parameterIndex: Int, x: Long): Unit = delegate.setLong(parameterIndex, x)
  override def setFloat(parameterIndex: Int, x: Float): Unit = delegate.setFloat(parameterIndex, x)
  override def setDouble(parameterIndex: Int, x: Double): Unit = delegate.setDouble(parameterIndex, x)
  override def setBigDecimal(parameterIndex: Int, x: java.math.BigDecimal): Unit = delegate.setBigDecimal(parameterIndex, x)
  override def setString(parameterIndex: Int, x: String): Unit = delegate.setString(parameterIndex, x)
  override def setBytes(parameterIndex: Int, x: Array[Byte]): Unit = delegate.setBytes(parameterIndex, x)
  override def setDate(parameterIndex: Int, x: Date): Unit = delegate.setDate(parameterIndex, x)
  override def setTime(parameterIndex: Int, x: Time): Unit = delegate.setTime(parameterIndex, x)
  override def setTimestamp(parameterIndex: Int, x: Timestamp): Unit = delegate.setTimestamp(parameterIndex, x)
  override def setAsciiStream(parameterIndex: Int, x: InputStream, length: Int): Unit = delegate.setAsciiStream(parameterIndex, x, length)
  override def setUnicodeStream(parameterIndex: Int, x: InputStream, length: Int): Unit = delegate.setUnicodeStream(parameterIndex, x, length)
  override def setBinaryStream(parameterIndex: Int, x: InputStream, length: Int): Unit = delegate.setBinaryStream(parameterIndex, x, length)
  override def clearParameters(): Unit = delegate.clearParameters()
  override def setObject(parameterIndex: Int, x: Any, targetSqlType: Int): Unit = delegate.setObject(parameterIndex, x, targetSqlType)
  override def setObject(parameterIndex: Int, x: Any): Unit = delegate.setObject(parameterIndex, x)
  override def execute(): Boolean = delegate.execute()
  override def addBatch(): Unit = delegate.addBatch()
  override def setCharacterStream(parameterIndex: Int, reader: Reader, length: Int): Unit = delegate.setCharacterStream(parameterIndex, reader, length)
  override def setRef(parameterIndex: Int, x: Ref): Unit = delegate.setRef(parameterIndex, x)
  override def setBlob(parameterIndex: Int, x: Blob): Unit = delegate.setBlob(parameterIndex, x)
  override def setClob(parameterIndex: Int, x: Clob): Unit = delegate.setClob(parameterIndex, x)
  override def setArray(parameterIndex: Int, x: sql.Array): Unit = delegate.setArray(parameterIndex, x)
  override def getMetaData: ResultSetMetaData = delegate.getMetaData
  override def setDate(parameterIndex: Int, x: Date, cal: Calendar): Unit = delegate.setDate(parameterIndex, x, cal)
  override def setTime(parameterIndex: Int, x: Time, cal: Calendar): Unit = delegate.setTime(parameterIndex, x, cal)
  override def setTimestamp(parameterIndex: Int, x: Timestamp, cal: Calendar): Unit = delegate.setTimestamp(parameterIndex, x, cal)

  override def setNull(parameterIndex: Int, sqlType: Int, typeName: String): Unit = delegate.setNull(parameterIndex, sqlType, typeName)
  override def setURL(parameterIndex: Int, x: URL): Unit = delegate.setURL(parameterIndex, x)
  override def getParameterMetaData: ParameterMetaData = delegate.getParameterMetaData()
  override def setRowId(parameterIndex: Int, x: RowId): Unit = delegate.setRowId(parameterIndex, x)
  override def setNString(parameterIndex: Int, value: String): Unit = delegate.setNString(parameterIndex, value)
  override def setNCharacterStream(parameterIndex: Int, value: Reader, length: Long): Unit = delegate.setNCharacterStream(parameterIndex, value, length)
  override def setNClob(parameterIndex: Int, value: NClob): Unit = delegate.setNClob(parameterIndex, value)
  override def setClob(parameterIndex: Int, reader: Reader, length: Long): Unit = delegate.setClob(parameterIndex, reader, length)
  override def setBlob(parameterIndex: Int, inputStream: InputStream, length: Long): Unit = delegate.setBlob(parameterIndex, inputStream, length)
  override def setNClob(parameterIndex: Int, reader: Reader, length: Long): Unit = delegate.setNClob(parameterIndex, reader, length)
  override def setSQLXML(parameterIndex: Int, xmlObject: SQLXML): Unit = delegate.setSQLXML(parameterIndex, xmlObject)
  override def setObject(parameterIndex: Int, x: Any, targetSqlType: Int, scaleOrLength: Int): Unit = delegate.setObject(parameterIndex, x, targetSqlType, scaleOrLength)
  override def setAsciiStream(parameterIndex: Int, x: InputStream, length: Long): Unit = delegate.setAsciiStream(parameterIndex, x, length)
  override def setBinaryStream(parameterIndex: Int, x: InputStream, length: Long): Unit = delegate.setBinaryStream(parameterIndex, x, length)
  override def setCharacterStream(parameterIndex: Int, reader: Reader, length: Long): Unit = delegate.setCharacterStream(parameterIndex, reader, length)
  override def setAsciiStream(parameterIndex: Int, x: InputStream): Unit = delegate.setAsciiStream(parameterIndex, x)
  override def setBinaryStream(parameterIndex: Int, x: InputStream): Unit = delegate.setBinaryStream(parameterIndex, x)
  override def setCharacterStream(parameterIndex: Int, reader: Reader): Unit = delegate.setCharacterStream(parameterIndex, reader)
  override def setNCharacterStream(parameterIndex: Int, value: Reader): Unit = delegate.setNCharacterStream(parameterIndex, value)
  override def setClob(parameterIndex: Int, reader: Reader): Unit = delegate.setClob(parameterIndex, reader)
  override def setBlob(parameterIndex: Int, inputStream: InputStream): Unit = delegate.setBlob(parameterIndex, inputStream)
  override def setNClob(parameterIndex: Int, reader: Reader): Unit = delegate.setNClob(parameterIndex, reader)

  override def executeQuery(sql: String): ResultSet = {
    val resultSet = delegate.executeQuery(sql)
    new TimeZonedSQLServerResultSet(resultSet)
  }

  override def executeUpdate(sql: String): Int = delegate.executeUpdate(sql)
  override def close(): Unit = delegate.close()
  override def getMaxFieldSize: Int = delegate.getMaxFieldSize
  override def setMaxFieldSize(max: Int): Unit = delegate.setMaxFieldSize(max)
  override def getMaxRows: Int = delegate.getMaxRows
  override def setMaxRows(max: Int): Unit = delegate.setMaxRows(max)
  override def setEscapeProcessing(enable: Boolean): Unit = delegate.setEscapeProcessing(enable)
  override def getQueryTimeout: Int = delegate.getQueryTimeout
  override def setQueryTimeout(seconds: Int): Unit = delegate.setQueryTimeout(seconds)
  override def cancel(): Unit = delegate.cancel()
  override def getWarnings: SQLWarning = delegate.getWarnings
  override def clearWarnings(): Unit = delegate.clearWarnings()
  override def setCursorName(name: String): Unit = delegate.setCursorName(name)
  override def execute(sql: String): Boolean = delegate.execute(sql)

  override def getResultSet: ResultSet = {
    val resultSet = delegate.getResultSet
    new TimeZonedSQLServerResultSet(resultSet)
  }

  override def getUpdateCount: Int = delegate.getUpdateCount
  override def getMoreResults: Boolean = delegate.getMoreResults
  override def setFetchDirection(direction: Int): Unit = delegate.setFetchDirection(direction)
  override def getFetchDirection: Int = delegate.getFetchDirection
  override def setFetchSize(rows: Int): Unit = delegate.setFetchSize(rows)
  override def getFetchSize: Int = delegate.getFetchSize
  override def getResultSetConcurrency: Int = delegate.getResultSetConcurrency
  override def getResultSetType: Int = delegate.getResultSetType
  override def addBatch(sql: String): Unit = delegate.addBatch(sql)
  override def clearBatch(): Unit = delegate.clearBatch()
  override def executeBatch(): Array[Int] = delegate.executeBatch()
  override def getConnection: Connection = delegate.getConnection
  override def getMoreResults(current: Int): Boolean = delegate.getMoreResults(current)

  override def getGeneratedKeys: ResultSet = {
    val resultSet = delegate.getGeneratedKeys
    new TimeZonedSQLServerResultSet(resultSet)
  }

  override def executeUpdate(sql: String, autoGeneratedKeys: Int): Int = delegate.executeUpdate(sql, autoGeneratedKeys)
  override def executeUpdate(sql: String, columnIndexes: Array[Int]): Int = delegate.executeUpdate(sql, columnIndexes)
  override def executeUpdate(sql: String, columnNames: Array[String]): Int = delegate.executeUpdate(sql, columnNames)
  override def execute(sql: String, autoGeneratedKeys: Int): Boolean = delegate.execute(sql, autoGeneratedKeys)
  override def execute(sql: String, columnIndexes: Array[Int]): Boolean = delegate.execute(sql, columnIndexes)
  override def execute(sql: String, columnNames: Array[String]): Boolean = delegate.execute(sql, columnNames)
  override def getResultSetHoldability: Int = delegate.getResultSetHoldability
  override def isClosed: Boolean = delegate.isClosed
  override def setPoolable(poolable: Boolean): Unit = delegate.setPoolable(poolable)
  override def isPoolable: Boolean = delegate.isPoolable
  override def closeOnCompletion(): Unit = delegate.closeOnCompletion()
  override def isCloseOnCompletion: Boolean = delegate.isCloseOnCompletion
  override def unwrap[T](iface: Class[T]): T = delegate.unwrap(iface)
  override def isWrapperFor(iface: Class[_]): Boolean = delegate.isWrapperFor(iface)
}
// scalastyle:on number.of.methods

