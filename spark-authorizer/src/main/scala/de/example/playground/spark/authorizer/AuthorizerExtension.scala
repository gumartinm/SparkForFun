// Author: Gustavo Martin Morcuende
package de.example.playground.spark.authorizer

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.plans.logical.{Command, LogicalPlan}
import org.apache.spark.sql.catalyst.rules.Rule
import org.apache.spark.sql.execution.command._
import org.apache.spark.sql.execution.datasources.{InsertIntoDataSourceCommand, InsertIntoHadoopFsRelationCommand}

case class AuthorizerExtension(spark: SparkSession) extends Rule[LogicalPlan] {

  override def apply(plan: LogicalPlan): LogicalPlan = {
    if(!isPermittedOperation(plan)) {
      throw new NotAuthorizedException("You are not allowed to run this command", null)
    }

    plan
  }

  // scalastyle:off cyclomatic.complexity
  def isPermittedOperation(plan: LogicalPlan): Boolean = {
    plan match {
      case c: Command => c match {
        case _: AnalyzeColumnCommand => true
        case _: AnalyzeTableCommand => true
        case p if p.nodeName == "AnalyzePartitionCommand" => true
        case e: ExplainCommand => isPermittedOperation(e.logicalPlan)
        case _: InsertIntoDataSourceCommand => true
        case p if p.nodeName == "InsertIntoDataSourceDirCommand" => true
        case _: InsertIntoHadoopFsRelationCommand => true
        case p if p.nodeName == "InsertIntoHiveDirCommand" => true
        case p if p.nodeName == "InsertIntoHiveTable" => true
        case _: LoadDataCommand => true
        case p if p.nodeName == "SaveIntoDataSourceCommand" => true
        case s: SetCommand if s.kv.isEmpty || s.kv.get._2.isEmpty => true
        case _: SetDatabaseCommand => true
        case _: ShowCreateTableCommand => false
        case _: ShowColumnsCommand => true
        case _: ShowDatabasesCommand => true
        case _: ShowFunctionsCommand => true
        case _: ShowPartitionsCommand => true
        case _: ShowTablesCommand => true
        case _: ExplainCommand => true
        case _ =>
          false
      }
      case _ => true
    }
  }
  // scalastyle:on cyclomatic.complexity
}
