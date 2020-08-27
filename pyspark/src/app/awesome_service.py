from pyspark.sql.types import StructType, StructField, ArrayType


class AwesomeService:

    @classmethod
    def rename_columns_to_upper_case(cls, schema):
        return cls.__rename_all_cols(schema, cls.__to_upper_case)

    @staticmethod
    def __rename_all_cols(schema, rename):

        def recur_rename(recur_schema):
            return list(map(do_rename, recur_schema.fields))

        def do_rename(field):
            if isinstance(field.dataType, StructType):
                return StructField(rename(field.name), StructType(recur_rename(field.dataType)), field.nullable,
                                   field.metadata)

            if isinstance(field.dataType, ArrayType) and isinstance(field.dataType.elementType, StructType):
                return StructField(
                    rename(field.name),
                    ArrayType(
                        StructType(recur_rename(field.dataType.elementType)),
                        field.nullable
                    ),
                    field.nullable,
                    field.metadata
                )

            return StructField(rename(field.name), field.dataType, field.nullable, field.metadata)

        return StructType(recur_rename(schema))

    @staticmethod
    def __to_upper_case(string):
        return string.upper()
