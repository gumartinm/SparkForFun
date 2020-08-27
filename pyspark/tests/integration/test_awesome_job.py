import os

import mock
import pytest
from app.awesome_job import AwesomeJob
from pyspark.sql import Row
from pyspark.sql.types import StringType, StructField, StructType

from tests.sparktestingbase.sqltestcase import SQLTestCase

FIXTURES_DIR = os.path.join(
    os.path.dirname(os.path.realpath(__file__)),
    'fixtures',
)


@pytest.mark.datafiles(
    os.path.join(FIXTURES_DIR, 'awesomejob', "sourcepath"),
    keep_top_dir=True
)
def test_run_awesome_job_with_success(spark_session, spark_session_after_each, datafiles, path):

    source_path = str(datafiles.listdir()[0])
    destination_path = path / 'destinationpath/awesomejob/'
    schema = StructType(
        [
            StructField("name", StringType()),
            StructField("surname", StringType())
        ]
    )
    expected_schema = StructType(
        [
            StructField("NAME", StringType()),
            StructField("SURNAME", StringType())
        ]
    )

    def create_expected_data_frame():
        return spark_session.createDataFrame(
            spark_session.sparkContext.parallelize(
                [
                    Row("John", "Doe"),
                    Row("Jane", "Doe")
                ]),
            StructType(
                [
                    StructField("NAME", StringType()),
                    StructField("SURNAME", StringType())
                ]
            )
        )

    with mock.patch('app.awesome_service.AwesomeService', autospec=True) as service_mock:
        service = service_mock.return_value
        service.rename_columns_to_upper_case.return_value = expected_schema
        AwesomeJob(source_path, destination_path, spark_session, service).run()

        result_data_frame = spark_session.sql("SELECT * FROM testing.example")
        expected_data_frame = create_expected_data_frame()

        service.rename_columns_to_upper_case.assert_called_once_with(schema)
        data_frame_suite = SQLTestCase()
        data_frame_suite.assertDataFrameEqual(expected=expected_data_frame, result=result_data_frame, tol=0)
