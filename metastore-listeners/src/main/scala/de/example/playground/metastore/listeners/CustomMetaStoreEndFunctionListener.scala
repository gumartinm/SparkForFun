// Author: Gustavo Martin Morcuende
package de.example.playground.metastore.listeners

import com.typesafe.scalalogging.LazyLogging
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hive.metastore.{MetaStoreEndFunctionContext, MetaStoreEndFunctionListener}

class CustomMetaStoreEndFunctionListener(config: Configuration) extends MetaStoreEndFunctionListener(config) with LazyLogging {

  override def onEndFunction(functionName: String, context: MetaStoreEndFunctionContext): Unit =
    logger.info("CreateTableEvent: {} {} {}", functionName, context.getInputTableName, context.isSuccess)
}
