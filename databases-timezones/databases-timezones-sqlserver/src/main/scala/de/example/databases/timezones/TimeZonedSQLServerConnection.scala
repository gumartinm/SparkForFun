// Author: Gustavo Martin Morcuende
package de.example.databases.timezones

import java.sql.{Blob, CallableStatement, Clob, Connection, DatabaseMetaData, NClob, PreparedStatement, SQLWarning,
                 SQLXML, Savepoint, Statement, Struct}
import java.util.Properties
import java.util.concurrent.Executor
import java.{sql, util}

// SQLServer using Europe/Madrid. Spark using UTC.

// scalastyle:off number.of.methods
class TimeZonedSQLServerConnection(delegate: Connection) extends Connection {

  override def createStatement(): Statement = delegate.createStatement()

  override def prepareStatement(sql: String): PreparedStatement = {
    val preparedStatement = delegate.prepareStatement(sql)
    new TimeZonedSQLPreparedStatement(preparedStatement)
  }

  override def prepareCall(sql: String): CallableStatement = delegate.prepareCall(sql)
  override def nativeSQL(sql: String): String = delegate.nativeSQL(sql)
  override def setAutoCommit(autoCommit: Boolean): Unit = delegate.setAutoCommit(autoCommit)
  override def getAutoCommit: Boolean = delegate.getAutoCommit
  override def commit(): Unit = delegate.commit()
  override def rollback(): Unit = delegate.rollback()
  override def close(): Unit = delegate.close()
  override def isClosed: Boolean = delegate.isClosed
  override def getMetaData: DatabaseMetaData = delegate.getMetaData
  override def setReadOnly(readOnly: Boolean): Unit = delegate.setReadOnly(readOnly)
  override def isReadOnly: Boolean = delegate.isReadOnly
  override def setCatalog(catalog: String): Unit = delegate.setCatalog(catalog)
  override def getCatalog: String = delegate.getCatalog
  override def setTransactionIsolation(level: Int): Unit = delegate.setTransactionIsolation(level)
  override def getTransactionIsolation: Int = delegate.getTransactionIsolation
  override def getWarnings: SQLWarning = delegate.getWarnings
  override def clearWarnings(): Unit = delegate.clearWarnings()
  override def createStatement(resultSetType: Int, resultSetConcurrency: Int): Statement = delegate.createStatement(resultSetType, resultSetConcurrency)
  override def prepareStatement(sql: String,
                                  resultSetType: Int,
                                  resultSetConcurrency: Int): PreparedStatement = {
    val preparedStatement = delegate.prepareStatement(sql, resultSetType, resultSetConcurrency)
    new TimeZonedSQLPreparedStatement(preparedStatement)
  }

  override def prepareCall(sql: String,
                             resultSetType: Int,
                             resultSetConcurrency: Int): CallableStatement = delegate.prepareCall(sql, resultSetConcurrency, resultSetConcurrency)
  override def getTypeMap: util.Map[String, Class[_]] = delegate.getTypeMap
  override def setTypeMap(map: util.Map[String, Class[_]]): Unit = delegate.setTypeMap(map)
  override def setHoldability(holdability: Int): Unit = delegate.setHoldability(holdability)
  override def getHoldability: Int = delegate.getHoldability
  override def setSavepoint(): Savepoint = delegate.setSavepoint()
  override def setSavepoint(name: String): Savepoint = delegate.setSavepoint(name)
  override def rollback(savepoint: Savepoint): Unit = delegate.rollback(savepoint)
  override def releaseSavepoint(savepoint: Savepoint): Unit = delegate.releaseSavepoint(savepoint)
  override def createStatement(resultSetType: Int,
                                 resultSetConcurrency: Int,
                                 resultSetHoldability: Int): Statement = delegate.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability)
  override def prepareStatement(sql: String,
                                  resultSetType: Int,
                                  resultSetConcurrency: Int,
                                  resultSetHoldability: Int): PreparedStatement = {
    val preparedStatement = delegate.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability)
    new TimeZonedSQLPreparedStatement(preparedStatement)
  }

  override def prepareCall(sql: String,
                             resultSetType: Int,
                             resultSetConcurrency: Int,
                             resultSetHoldability: Int): CallableStatement = delegate.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability)
  override def prepareStatement(sql: String,
                                  autoGeneratedKeys: Int): PreparedStatement = {
    val preparedStatement = delegate.prepareStatement(sql, autoGeneratedKeys)
    new TimeZonedSQLPreparedStatement(preparedStatement)
  }

  override def prepareStatement(sql: String,
                                  columnIndexes: Array[Int]): PreparedStatement = {
    val preparedStatement = delegate.prepareStatement(sql, columnIndexes)
    new TimeZonedSQLPreparedStatement(preparedStatement)
  }

  override def prepareStatement(sql: String,
                                  columnNames: Array[String]): PreparedStatement = {
    val preparedStatement = delegate.prepareStatement(sql, columnNames)
    new TimeZonedSQLPreparedStatement(preparedStatement)
  }

  override def createClob(): Clob = delegate.createClob()
  override def createBlob(): Blob = delegate.createBlob()
  override def createNClob(): NClob = delegate.createNClob()
  override def createSQLXML(): SQLXML = delegate.createSQLXML()
  override def isValid(timeout: Int): Boolean = delegate.isValid(timeout)
  override def setClientInfo(name: String, value: String): Unit = delegate.setClientInfo(name, value)
  override def setClientInfo(properties: Properties): Unit = delegate.setClientInfo(properties)
  override def getClientInfo(name: String): String = delegate.getClientInfo(name)
  override def getClientInfo: Properties = delegate.getClientInfo
  override def createArrayOf(typeName: String, elements: Array[AnyRef]): sql.Array = delegate.createArrayOf(typeName, elements)
  override def createStruct(typeName: String, attributes: Array[AnyRef]): Struct = delegate.createStruct(typeName, attributes)

  override def setSchema(schema: String): Unit = delegate.setSchema(schema)
  override def getSchema: String = delegate.getSchema
  override def abort(executor: Executor): Unit = delegate.abort(executor)
  override def setNetworkTimeout(executor: Executor, milliseconds: Int): Unit = delegate.setNetworkTimeout(executor, milliseconds)
  override def getNetworkTimeout: Int = delegate.getNetworkTimeout
  override def unwrap[T](iface: Class[T]): T = delegate.unwrap(iface)
  override def isWrapperFor(iface: Class[_]): Boolean = delegate.isWrapperFor(iface)
}
// scalastyle:on number.of.methods
