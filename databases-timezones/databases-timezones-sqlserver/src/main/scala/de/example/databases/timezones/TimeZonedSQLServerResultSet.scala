// Author: Gustavo Martin Morcuende
package de.example.databases.timezones

import java.io.{InputStream, Reader}
import java.net.URL
import java.sql.{Blob, Clob, Date, NClob, Ref, ResultSet, ResultSetMetaData, RowId, SQLWarning, SQLXML, Statement,
                 Time, Timestamp}
import java.util.{Calendar, TimeZone}
import java.{sql, util}

// SQLServer using Europe/Madrid. Spark using UTC.
object TimeZonedSQLServerResultSet {
  val TimeZone = "Europe/Madrid"
}

// scalastyle:off number.of.methods
class TimeZonedSQLServerResultSet(delegate: ResultSet) extends ResultSet {
  override def next(): Boolean = delegate.next()
  override def close(): Unit = delegate.close()
  override def wasNull(): Boolean = delegate.wasNull()
  override def getString(columnIndex: Int): String = delegate.getString(columnIndex)
  override def getBoolean(columnIndex: Int): Boolean = delegate.getBoolean(columnIndex)
  override def getByte(columnIndex: Int): Byte = delegate.getByte(columnIndex)
  override def getShort(columnIndex: Int): Short = delegate.getShort(columnIndex)
  override def getInt(columnIndex: Int): Int = delegate.getInt(columnIndex)
  override def getLong(columnIndex: Int): Long = delegate.getLong(columnIndex)
  override def getFloat(columnIndex: Int): Float = delegate.getFloat(columnIndex)
  override def getDouble(columnIndex: Int): Double = delegate.getDouble(columnIndex)
  override def getBigDecimal(columnIndex: Int, scale: Int): java.math.BigDecimal = delegate.getBigDecimal(columnIndex, scale)
  override def getBytes(columnIndex: Int): Array[Byte] = delegate.getBytes(columnIndex)
  override def getDate(columnIndex: Int): Date = delegate.getDate(columnIndex)

  override def getTime(columnIndex: Int): Time = {
    val timeZone = TimeZone.getTimeZone("Europe/Madrid")
    val calendar = Calendar.getInstance(timeZone)
    delegate.getTime(columnIndex, calendar)
  }

  override def getTimestamp(columnIndex: Int): Timestamp = {
    val timeZone = TimeZone.getTimeZone("Europe/Madrid")
    val calendar = Calendar.getInstance(timeZone)
    delegate.getTimestamp(columnIndex, calendar)
  }

  override def getAsciiStream(columnIndex: Int): InputStream = delegate.getAsciiStream(columnIndex)
  override def getUnicodeStream(columnIndex: Int): InputStream = delegate.getUnicodeStream(columnIndex)
  override def getBinaryStream(columnIndex: Int): InputStream = delegate.getBinaryStream(columnIndex)
  override def getString(columnLabel: String): String = delegate.getString(columnLabel)
  override def getBoolean(columnLabel: String): Boolean = delegate.getBoolean(columnLabel)
  override def getByte(columnLabel: String): Byte = delegate.getByte(columnLabel)
  override def getShort(columnLabel: String): Short = delegate.getShort(columnLabel)
  override def getInt(columnLabel: String): Int = delegate.getInt(columnLabel)
  override def getLong(columnLabel: String): Long = delegate.getLong(columnLabel)
  override def getFloat(columnLabel: String): Float = delegate.getFloat(columnLabel)
  override def getDouble(columnLabel: String): Double = delegate.getDouble(columnLabel)
  override def getBigDecimal(columnLabel: String, scale: Int): java.math.BigDecimal = delegate.getBigDecimal(columnLabel)
  override def getBytes(columnLabel: String): Array[Byte] = delegate.getBytes(columnLabel)
  override def getDate(columnLabel: String): Date = delegate.getDate(columnLabel)

  override def getTime(columnLabel: String): Time = {
    val timeZone = TimeZone.getTimeZone("Europe/Madrid")
    val calendar = Calendar.getInstance(timeZone)
    delegate.getTime(columnLabel, calendar)
  }


  override def getTimestamp(columnLabel: String): Timestamp = {
    val timeZone = TimeZone.getTimeZone("Europe/Madrid")
    val calendar = Calendar.getInstance(timeZone)
    delegate.getTimestamp(columnLabel, calendar)
  }

  override def getAsciiStream(columnLabel: String): InputStream = delegate.getAsciiStream(columnLabel)
  override def getUnicodeStream(columnLabel: String): InputStream = delegate.getUnicodeStream(columnLabel)
  override def getBinaryStream(columnLabel: String): InputStream = delegate.getBinaryStream(columnLabel)
  override def getWarnings: SQLWarning = delegate.getWarnings
  override def clearWarnings(): Unit = delegate.clearWarnings()
  override def getCursorName: String = delegate.getCursorName
  override def getMetaData: ResultSetMetaData = delegate.getMetaData
  override def getObject(columnIndex: Int): AnyRef = delegate.getObject(columnIndex)
  override def getObject(columnLabel: String): AnyRef = delegate.getObject(columnLabel)
  override def findColumn(columnLabel: String): Int = delegate.findColumn(columnLabel)
  override def getCharacterStream(columnIndex: Int): Reader = delegate.getCharacterStream(columnIndex)
  override def getCharacterStream(columnLabel: String): Reader = delegate.getCharacterStream(columnLabel)
  override def getBigDecimal(columnIndex: Int): java.math.BigDecimal = delegate.getBigDecimal(columnIndex)
  override def getBigDecimal(columnLabel: String): java.math.BigDecimal = delegate.getBigDecimal(columnLabel)
  override def isBeforeFirst: Boolean = delegate.isBeforeFirst
  override def isAfterLast: Boolean = delegate.isAfterLast
  override def isFirst: Boolean = delegate.isFirst
  override def isLast: Boolean = delegate.isLast
  override def beforeFirst(): Unit = delegate.beforeFirst()
  override def afterLast(): Unit = delegate.afterLast()
  override def first(): Boolean = delegate.first()
  override def last(): Boolean = delegate.last()
  override def getRow: Int = delegate.getRow
  override def absolute(row: Int): Boolean = delegate.absolute(row)
  override def relative(rows: Int): Boolean = delegate.relative(rows)
  override def previous(): Boolean = delegate.previous()
  override def setFetchDirection(direction: Int): Unit = delegate.setFetchDirection(direction)
  override def getFetchDirection: Int = delegate.getFetchDirection
  override def setFetchSize(rows: Int): Unit = delegate.setFetchSize(rows)
  override def getFetchSize: Int = delegate.getFetchSize
  override def getType: Int = delegate.getType
  override def getConcurrency: Int = delegate.getConcurrency
  override def rowUpdated(): Boolean = delegate.rowUpdated()
  override def rowInserted(): Boolean = delegate.rowInserted()
  override def rowDeleted(): Boolean = delegate.rowDeleted()
  override def updateNull(columnIndex: Int): Unit = delegate.updateNull(columnIndex)
  override def updateBoolean(columnIndex: Int, x: Boolean): Unit = delegate.updateBoolean(columnIndex, x)
  override def updateByte(columnIndex: Int, x: Byte): Unit = delegate.updateByte(columnIndex, x)
  override def updateShort(columnIndex: Int, x: Short): Unit = delegate.updateShort(columnIndex, x)
  override def updateInt(columnIndex: Int, x: Int): Unit = delegate.updateInt(columnIndex, x)
  override def updateLong(columnIndex: Int, x: Long): Unit = delegate.updateLong(columnIndex, x)
  override def updateFloat(columnIndex: Int, x: Float): Unit = delegate.updateFloat(columnIndex, x)
  override def updateDouble(columnIndex: Int, x: Double): Unit = delegate. updateDouble(columnIndex, x)
  override def updateBigDecimal(columnIndex: Int, x: java.math.BigDecimal): Unit = delegate.updateBigDecimal(columnIndex, x)
  override def updateString(columnIndex: Int, x: String): Unit = delegate.updateString(columnIndex, x)
  override def updateBytes(columnIndex: Int, x: Array[Byte]): Unit = delegate.updateBytes(columnIndex, x)
  override def updateDate(columnIndex: Int, x: Date): Unit = delegate.updateDate(columnIndex, x)
  override def updateTime(columnIndex: Int, x: Time): Unit = delegate.updateTime(columnIndex, x)
  override def updateTimestamp(columnIndex: Int, x: Timestamp): Unit = delegate.updateTimestamp(columnIndex, x)
  override def updateAsciiStream(columnIndex: Int, x: InputStream, length: Int): Unit = delegate.updateAsciiStream(columnIndex, x, length)
  override def updateBinaryStream(columnIndex: Int, x: InputStream, length: Int): Unit = delegate.updateBinaryStream(columnIndex, x, length)
  override def updateCharacterStream(columnIndex: Int, x: Reader, length: Int): Unit = delegate.updateCharacterStream(columnIndex, x, length)
  override def updateObject(columnIndex: Int, x: Any, scaleOrLength: Int): Unit = delegate.updateObject(columnIndex, x, scaleOrLength)
  override def updateObject(columnIndex: Int, x: Any): Unit = delegate.updateObject(columnIndex, x)
  override def updateNull(columnLabel: String): Unit = delegate.updateNull(columnLabel)
  override def updateBoolean(columnLabel: String, x: Boolean): Unit = delegate.updateBoolean(columnLabel, x)
  override def updateByte(columnLabel: String, x: Byte): Unit = delegate.updateByte(columnLabel, x)
  override def updateShort(columnLabel: String, x: Short): Unit = delegate.updateShort(columnLabel, x)
  override def updateInt(columnLabel: String, x: Int): Unit = delegate.updateInt(columnLabel, x)
  override def updateLong(columnLabel: String, x: Long): Unit = delegate.updateLong(columnLabel, x)
  override def updateFloat(columnLabel: String, x: Float): Unit = delegate.updateFloat(columnLabel, x)
  override def updateDouble(columnLabel: String, x: Double): Unit = delegate.updateDouble(columnLabel, x)
  override def updateBigDecimal(columnLabel: String, x: java.math.BigDecimal): Unit = delegate.updateBigDecimal(columnLabel, x)
  override def updateString(columnLabel: String, x: String): Unit = delegate.updateString(columnLabel, x)
  override def updateBytes(columnLabel: String, x: Array[Byte]): Unit = delegate.updateBytes(columnLabel, x)
  override def updateDate(columnLabel: String, x: Date): Unit = delegate.updateDate(columnLabel, x)
  override def updateTime(columnLabel: String, x: Time): Unit = delegate.updateTime(columnLabel, x)
  override def updateTimestamp(columnLabel: String, x: Timestamp): Unit = delegate.updateTimestamp(columnLabel, x)
  override def updateAsciiStream(columnLabel: String,
                                   x: InputStream,
                                   length: Int): Unit = delegate.updateAsciiStream(columnLabel, x, length)
  override def updateBinaryStream(columnLabel: String,
                                    x: InputStream,
                                    length: Int): Unit = delegate.updateBinaryStream(columnLabel, x, length)
  override def updateCharacterStream(columnLabel: String,
                                       reader: Reader,
                                       length: Int): Unit = delegate.updateCharacterStream(columnLabel, reader, length)
  override def updateObject(columnLabel: String, x: Any, scaleOrLength: Int): Unit = delegate.updateObject(columnLabel, x, scaleOrLength)
  override def updateObject(columnLabel: String, x: Any): Unit = delegate.updateObject(columnLabel, x)
  override def insertRow(): Unit = delegate.insertRow()
  override def updateRow(): Unit = delegate.updateRow()
  override def deleteRow(): Unit = delegate.deleteRow()
  override def refreshRow(): Unit = delegate.refreshRow()
  override def cancelRowUpdates(): Unit = delegate.cancelRowUpdates()
  override def moveToInsertRow(): Unit = delegate.moveToInsertRow()
  override def moveToCurrentRow(): Unit = delegate.moveToCurrentRow()
  override def getStatement: Statement = delegate.getStatement
  override def getObject(columnIndex: Int,
                           map: util.Map[String, Class[_]]): AnyRef = delegate.getObject(columnIndex, map)
  override def getRef(columnIndex: Int): Ref = delegate.getRef(columnIndex)
  override def getBlob(columnIndex: Int): Blob = delegate.getBlob(columnIndex)
  override def getClob(columnIndex: Int): Clob = delegate.getClob(columnIndex)
  override def getArray(columnIndex: Int): sql.Array = delegate.getArray(columnIndex)
  override def getObject(columnLabel: String,
                           map: util.Map[String, Class[_]]): AnyRef = delegate.getObject(columnLabel, map)
  override def getRef(columnLabel: String): Ref = delegate.getRef(columnLabel)
  override def getBlob(columnLabel: String): Blob = delegate.getBlob(columnLabel)
  override def getClob(columnLabel: String): Clob = delegate.getClob(columnLabel)
  override def getArray(columnLabel: String): sql.Array = delegate.getArray(columnLabel)
  override def getDate(columnIndex: Int, cal: Calendar): Date = delegate.getDate(columnIndex, cal)
  override def getDate(columnLabel: String, cal: Calendar): Date = getDate(columnLabel, cal)
  override def getTime(columnIndex: Int, cal: Calendar): Time = delegate.getTime(columnIndex, cal)
  override def getTime(columnLabel: String, cal: Calendar): Time = delegate.getTime(columnLabel, cal)
  override def getTimestamp(columnIndex: Int, cal: Calendar): Timestamp = delegate.getTimestamp(columnIndex, cal)
  override def getTimestamp(columnLabel: String,
                              cal: Calendar): Timestamp = delegate.getTimestamp(columnLabel, cal)
  override def getURL(columnIndex: Int): URL = delegate.getURL(columnIndex)
  override def getURL(columnLabel: String): URL = delegate.getURL(columnLabel)
  override def updateRef(columnIndex: Int, x: Ref): Unit = delegate.updateRef(columnIndex, x)
  override def updateRef(columnLabel: String, x: Ref): Unit = delegate.updateRef(columnLabel, x)
  override def updateBlob(columnIndex: Int, x: Blob): Unit = delegate.updateBlob(columnIndex, x)
  override def updateBlob(columnLabel: String, x: Blob): Unit = delegate.updateBlob(columnLabel, x)
  override def updateClob(columnIndex: Int, x: Clob): Unit = delegate.updateClob(columnIndex, x)
  override def updateClob(columnLabel: String, x: Clob): Unit = delegate.updateClob(columnLabel, x)
  override def updateArray(columnIndex: Int, x: sql.Array): Unit = delegate.updateArray(columnIndex, x)
  override def updateArray(columnLabel: String, x: sql.Array): Unit = delegate.updateArray(columnLabel, x)
  override def getRowId(columnIndex: Int): RowId = delegate.getRowId(columnIndex)
  override def getRowId(columnLabel: String): RowId = delegate.getRowId(columnLabel)
  override def updateRowId(columnIndex: Int, x: RowId): Unit = delegate.updateRowId(columnIndex, x)
  override def updateRowId(columnLabel: String, x: RowId): Unit = delegate.updateRowId(columnLabel, x)
  override def getHoldability: Int = delegate.getHoldability
  override def isClosed: Boolean = delegate.isClosed
  override def updateNString(columnIndex: Int, nString: String): Unit = delegate.updateNString(columnIndex, nString)
  override def updateNString(columnLabel: String, nString: String): Unit = delegate.updateNString(columnLabel, nString)
  override def updateNClob(columnIndex: Int, nClob: NClob): Unit = delegate.updateNClob(columnIndex, nClob)
  override def updateNClob(columnLabel: String, nClob: NClob): Unit = delegate.updateNClob(columnLabel, nClob)
  override def getNClob(columnIndex: Int): NClob = delegate.getNClob(columnIndex)
  override def getNClob(columnLabel: String): NClob = delegate.getNClob(columnLabel)
  override def getSQLXML(columnIndex: Int): SQLXML = delegate.getSQLXML(columnIndex)
  override def getSQLXML(columnLabel: String): SQLXML = delegate.getSQLXML(columnLabel)
  override def updateSQLXML(columnIndex: Int, xmlObject: SQLXML): Unit = delegate.updateSQLXML(columnIndex, xmlObject)
  override def updateSQLXML(columnLabel: String, xmlObject: SQLXML): Unit = delegate.updateSQLXML(columnLabel, xmlObject)
  override def getNString(columnIndex: Int): String = delegate.getNString(columnIndex)
  override def getNString(columnLabel: String): String = delegate.getNString(columnLabel)
  override def getNCharacterStream(columnIndex: Int): Reader = delegate.getNCharacterStream(columnIndex)
  override def getNCharacterStream(columnLabel: String): Reader = delegate.getNCharacterStream(columnLabel)
  override def updateNCharacterStream(columnIndex: Int, x: Reader, length: Long): Unit = delegate.updateNCharacterStream(columnIndex, x, length)
  override def updateNCharacterStream(columnLabel: String,
                                        reader: Reader,
                                        length: Long): Unit = delegate.updateNCharacterStream(columnLabel, reader, length)
  override def updateAsciiStream(columnIndex: Int, x: InputStream, length: Long): Unit = delegate.updateAsciiStream(columnIndex, x, length)
  override def updateBinaryStream(columnIndex: Int, x: InputStream, length: Long): Unit = delegate.updateBinaryStream(columnIndex, x, length)
  override def updateCharacterStream(columnIndex: Int, x: Reader, length: Long): Unit = delegate.updateCharacterStream(columnIndex, x, length)
  override def updateAsciiStream(columnLabel: String,
                                   x: InputStream,
                                   length: Long): Unit = delegate.updateAsciiStream(columnLabel, x, length)
  override def updateBinaryStream(columnLabel: String,
                                    x: InputStream,
                                    length: Long): Unit = delegate.updateBinaryStream(columnLabel, x, length)
  override def updateCharacterStream(columnLabel: String,
                                       reader: Reader,
                                       length: Long): Unit = delegate.updateCharacterStream(columnLabel, reader, length)
  override def updateBlob(columnIndex: Int, inputStream: InputStream, length: Long): Unit = delegate.updateBlob(columnIndex, inputStream, length)
  override def updateBlob(columnLabel: String,
                            inputStream: InputStream,
                            length: Long): Unit = delegate.updateBlob(columnLabel, inputStream, length)
  override def updateClob(columnIndex: Int, reader: Reader, length: Long): Unit = delegate.updateClob(columnIndex, reader, length)
  override def updateClob(columnLabel: String, reader: Reader, length: Long): Unit = delegate.updateClob(columnLabel, reader, length)
  override def updateNClob(columnIndex: Int, reader: Reader, length: Long): Unit = delegate.updateNClob(columnIndex, reader, length)
  override def updateNClob(columnLabel: String, reader: Reader, length: Long): Unit = delegate.updateNClob(columnLabel, reader, length)
  override def updateNCharacterStream(columnIndex: Int, x: Reader): Unit = delegate.updateNCharacterStream(columnIndex, x)
  override def updateNCharacterStream(columnLabel: String, reader: Reader): Unit = delegate.updateNCharacterStream(columnLabel, reader)
  override def updateAsciiStream(columnIndex: Int, x: InputStream): Unit = delegate.updateAsciiStream(columnIndex, x)
  override def updateBinaryStream(columnIndex: Int, x: InputStream): Unit = delegate.updateBinaryStream(columnIndex, x)
  override def updateCharacterStream(columnIndex: Int, x: Reader): Unit = delegate.updateCharacterStream(columnIndex, x)
  override def updateAsciiStream(columnLabel: String, x: InputStream): Unit = delegate.updateAsciiStream(columnLabel, x)
  override def updateBinaryStream(columnLabel: String, x: InputStream): Unit = delegate.updateBinaryStream(columnLabel, x)
  override def updateCharacterStream(columnLabel: String, reader: Reader): Unit = delegate.updateCharacterStream(columnLabel, reader)
  override def updateBlob(columnIndex: Int, inputStream: InputStream): Unit = delegate.updateBlob(columnIndex, inputStream)
  override def updateBlob(columnLabel: String, inputStream: InputStream): Unit = delegate.updateBlob(columnLabel, inputStream)
  override def updateClob(columnIndex: Int, reader: Reader): Unit = delegate.updateClob(columnIndex, reader)
  override def updateClob(columnLabel: String, reader: Reader): Unit = delegate.updateClob(columnLabel, reader)
  override def updateNClob(columnIndex: Int, reader: Reader): Unit = delegate.updateNClob(columnIndex, reader)
  override def updateNClob(columnLabel: String, reader: Reader): Unit = delegate.updateNClob(columnLabel, reader)
  override def getObject[T](columnIndex: Int, `type`: Class[T]): T = delegate.getObject(columnIndex, `type`)
  override def getObject[T](columnLabel: String, `type`: Class[T]): T = delegate.getObject(columnLabel, `type`)
  override def unwrap[T](iface: Class[T]): T = delegate.unwrap(iface)
  override def isWrapperFor(iface: Class[_]): Boolean = delegate.isWrapperFor(iface)
}
// scalastyle:on number.of.methods
