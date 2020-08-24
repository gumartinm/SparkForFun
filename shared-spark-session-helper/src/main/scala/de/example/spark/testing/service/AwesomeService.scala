// Author: Gustavo Martin Morcuende
package de.example.spark.testing.service

import org.apache.spark.sql.types.{ArrayType, StructField, StructType}

class AwesomeService {

  def renameColumnsToUpperCase(schema: StructType): StructType =
    renameAllCols(schema, toUpperCase)

  private def renameAllCols(schema: StructType, rename: String => String): StructType = {

    def recurRename(schema: StructType): Seq[StructField] =
      schema.fields
        .map {
          case StructField(name, dataType: StructType, nullable, meta) =>
            StructField(rename(name), StructType(recurRename(dataType)), nullable, meta)

          case StructField(name, dataType: ArrayType, nullable, meta)
              if dataType.elementType.isInstanceOf[StructType] =>
            StructField(
              rename(name),
              ArrayType(
                StructType(recurRename(dataType.elementType.asInstanceOf[StructType])),
                nullable
              ),
              nullable,
              meta
            )

          case StructField(name, dtype, nullable, meta) =>
            StructField(rename(name), dtype, nullable, meta)
        }

    StructType(recurRename(schema))
  }

  private def toUpperCase(str: String): String =
    str.toUpperCase
}
