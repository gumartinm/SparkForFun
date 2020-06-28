// Author: Gustavo Martin Morcuende
package de.example.spark.testing.service

import org.apache.spark.sql.types.{ArrayType, StructField, StructType}

class AwesomeService {

  def renameColumnsToUpperCase(schema: StructType): StructType = {

    def recurRename(schema: StructType): Seq[StructField] =
      schema.fields
        .map {
          case StructField(name, dtype: StructType, nullable, meta) =>
            StructField(toUpperCase(name), StructType(recurRename(dtype)), nullable, meta)

          case StructField(name, dtype: ArrayType, nullable, meta)
            if dtype.elementType.isInstanceOf[StructType] =>
            StructField(
              toUpperCase(name),
              ArrayType(
                StructType(recurRename(dtype.elementType.asInstanceOf[StructType])),
                nullable
              ),
              nullable,
              meta
            )

          case StructField(name, dtype, nullable, meta) =>
            StructField(toUpperCase(name), dtype, nullable, meta)
        }

    StructType(recurRename(schema))
  }

  private def toUpperCase(str: String): String =
    str.toUpperCase
}
