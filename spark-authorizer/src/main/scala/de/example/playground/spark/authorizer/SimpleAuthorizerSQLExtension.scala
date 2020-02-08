// Author: Gustavo Martin Morcuende
package de.example.playground.spark.authorizer

import org.apache.ranger.authorization.spark.authorizer.Extensions
import org.apache.spark.sql.SparkSessionExtensions

class SimpleAuthorizerSQLExtension extends Extensions {

  override def apply(ext: SparkSessionExtensions): Unit = {
    ext.injectOptimizerRule(AuthorizerExtension)
  }

}
