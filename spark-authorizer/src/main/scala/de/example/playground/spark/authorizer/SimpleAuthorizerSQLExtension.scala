// Author: Gustavo Martin Morcuende
package de.example.playground.spark.authorizer

import org.apache.spark.sql.SparkSessionExtensions

class SimpleAuthorizerSQLExtension {

  def apply(ext: SparkSessionExtensions): Unit = {
    ext.injectOptimizerRule(AuthorizerExtension)
  }

}
