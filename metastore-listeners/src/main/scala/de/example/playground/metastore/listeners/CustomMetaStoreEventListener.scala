// Author: Gustavo Martin Morcuende
package de.example.playground.metastore.listeners

import com.typesafe.scalalogging.LazyLogging
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hive.metastore.MetaStoreEventListener
import org.apache.hadoop.hive.metastore.events.{
  AddIndexEvent,
  AddPartitionEvent,
  AlterIndexEvent,
  AlterPartitionEvent,
  AlterTableEvent,
  CreateDatabaseEvent,
  CreateTableEvent,
  DropDatabaseEvent,
  DropIndexEvent,
  DropPartitionEvent,
  DropTableEvent,
  InsertEvent,
  LoadPartitionDoneEvent
}

class CustomMetaStoreEventListener(config: Configuration) extends MetaStoreEventListener(config) with LazyLogging {

  override def onCreateTable(tableEvent: CreateTableEvent): Unit = {
    logger.info("CreateTableEvent: {}", tableEvent.getTable.getTableName)
  }

  override def onDropTable(tableEvent: DropTableEvent): Unit = {
    logger.info("DropTableEvent: {}", tableEvent.getTable.getTableName)
  }

  override def onAlterTable(tableEvent: AlterTableEvent): Unit = {
    logger.info("AlterTableEvent: {} {}", tableEvent.getOldTable.getTableName, tableEvent.getNewTable.getTableName)
  }

  override def onAddPartition(partitionEvent: AddPartitionEvent): Unit = {
    logger.info("AddPartitionEvent: {} {}", partitionEvent.getTable.getTableName)
  }

  override def onDropPartition(partitionEvent: DropPartitionEvent): Unit = {
    logger.info("DropPartitionEvent: {} {}", partitionEvent.getTable.getTableName)
  }

  override def onAlterPartition(partitionEvent: AlterPartitionEvent): Unit = {
    logger.info("DropPartitionEvent: {} {}", partitionEvent.getTable.getTableName)
  }

  override def onCreateDatabase(dbEvent: CreateDatabaseEvent): Unit = {
    logger.info("CreateDatabaseEvent: {}", dbEvent.getDatabase.getName)
  }

  override def onDropDatabase(dbEvent: DropDatabaseEvent): Unit = {
    logger.info("DropDatabaseEvent: {}", dbEvent.getDatabase.getName)
  }

  override def onLoadPartitionDone(partSetDoneEvent: LoadPartitionDoneEvent): Unit = {
    logger.info("LoadPartitionDoneEvent: {}", partSetDoneEvent.getTable.getTableName)
  }

  override def onAddIndex(indexEvent: AddIndexEvent): Unit = {
    logger.info("LoadPartitionDoneEvent: {}", indexEvent.getIndex.toString)
  }

  override def onDropIndex(indexEvent: DropIndexEvent): Unit = {
    logger.info("DropIndexEvent: {}", indexEvent.getIndex.toString)
  }

  override def onAlterIndex(indexEvent: AlterIndexEvent): Unit = {
    logger.info("AlterIndexEvent: {} {}", indexEvent.getOldIndex, indexEvent.getNewIndex)
  }

  override def onInsert(insertEvent: InsertEvent): Unit = {
    logger.info("InsertEvent: {}", insertEvent.getTable)
  }

}
