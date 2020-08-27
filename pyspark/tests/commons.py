# Author: Gustavo Martin Morcuende
from pyspark.sql import Row
from pyspark.sql.types import StructType, StructField, StringType

UPPER_CASE_SCHEMA = StructType(
    [
        StructField("NAME", StringType()),
        StructField("SURNAME", StringType())
    ]
)


def create_expected_data_frame(spark_session):
    return spark_session.createDataFrame(
        spark_session.sparkContext.parallelize(
            [
                Row("John", "Doe"),
                Row("Jane", "Doe")
            ]),
        UPPER_CASE_SCHEMA
    )
