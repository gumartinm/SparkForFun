# Author: Gustavo Martin Morcuende
from pyspark.sql.types import StringType, StructField, StructType


class AwesomeJob:
    __DATABASE = 'testing'
    __TABLE = 'example'

    def __init__(self, source_path, destination_path, spark_session, awesome_service):
        self.source_path = source_path
        self.destination_path = destination_path
        self.spark_session = spark_session
        self.awesome_service = awesome_service
        self.logger = AwesomeJob.__logger(spark_session)

    def run(self):
        self.logger.info('Running AwesomeJob')

        json_schema = StructType(
            [
                StructField('name', StringType()),
                StructField('surname', StringType())
            ]
        )
        data_frame = self.spark_session.read.schema(json_schema).json(path=self.source_path)
        schema = data_frame.schema

        upper_case_schema = self.awesome_service.rename_columns_to_upper_case(schema)
        upper_case_data_frame = self.spark_session.createDataFrame(data_frame.rdd, upper_case_schema)

        self.spark_session.sql(f'CREATE DATABASE IF NOT EXISTS {AwesomeJob.__DATABASE}')
        self.spark_session.sql(
            f'CREATE TABLE IF NOT EXISTS `{AwesomeJob.__DATABASE}`.`{AwesomeJob.__TABLE}` (`NAME` STRING, `SURNAME` STRING) '
            f'USING PARQUET '
            f'OPTIONS ( '
            f'path \'{self.destination_path}\' '
            f')')
        upper_case_data_frame.write \
            .mode('overwrite') \
            .insertInto(f'{AwesomeJob.__DATABASE}.{AwesomeJob.__TABLE}')

        self.logger.info('End running AwesomeJob')

    @staticmethod
    def __logger(spark_session):
        # pylint: disable=W0212
        log4j_logger = spark_session.sparkContext._jvm.org.apache.log4j
        return log4j_logger.LogManager.getLogger(__name__)
