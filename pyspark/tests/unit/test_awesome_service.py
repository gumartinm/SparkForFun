# Author: Gustavo Martin Morcuende
from app.awesome_service import AwesomeService

from pyspark.sql.types import StructType, StringType, StructField, ArrayType


class TestAwesomeService:

    def test_rename_columns_to_upper_case(self):
        service = AwesomeService()
        some_shema = StructType(
            [
                StructField(
                    "Level1ColumnA",
                    StructType(
                        [
                            StructField(
                                "Level2ColumnA",
                                StructType(
                                    [StructField("Level3ColumnA", StringType())]
                                )
                            )
                        ]
                    )
                ),
                StructField("Level1ColumnB", ArrayType(StringType()))
            ]
        )

        expected_schema = StructType(
            [
                StructField(
                    "LEVEL1COLUMNA",
                    StructType(
                        [
                            StructField(
                                "LEVEL2COLUMNA",
                                StructType(
                                    [StructField("LEVEL3COLUMNA", StringType())]
                                )
                            )
                        ]
                    )
                ),
                StructField("LEVEL1COLUMNB", ArrayType(StringType()))
            ]
        )

        result_schema = service.rename_columns_to_upper_case(some_shema)

        assert expected_schema == result_schema
