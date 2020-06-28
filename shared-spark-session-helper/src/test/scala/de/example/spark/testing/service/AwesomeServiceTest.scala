// Author: Gustavo Martin Morcuende
package de.example.spark.testing.service

import org.apache.spark.sql.types.{ArrayType, StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AwesomeServiceTest extends AnyFlatSpec with Matchers  {

  it should "rename columns to upper case" in {
    val awesomeService = new AwesomeService
    val someSchema = StructType(
      Array(
        StructField(
          "Level1ColumnA",
          StructType(
            Array(
              StructField(
                "Level2ColumnA",
                StructType(
                  Array(StructField("Level3ColumnA", StringType))
                )
              ))
          )
        ),
        StructField("Level1ColumnB", ArrayType(StringType))
      )
    )

    val expectedSchema = StructType(
      Array(
        StructField(
          "LEVEL1COLUMNA",
          StructType(
            Array(
              StructField(
                "LEVEL2COLUMNA",
                StructType(
                  Array(StructField("LEVEL3COLUMNA", StringType))
                )
              ))
          )
        ),
        StructField("LEVEL1COLUMNB", ArrayType(StringType))
      )
    )

    val schemaResult = awesomeService.renameColumnsToUpperCase(someSchema)

    schemaResult shouldBe expectedSchema
  }
}
