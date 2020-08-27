import os

import pytest
from app.awesome_app import run
from collections import namedtuple
from pyspark.sql import Row
from pyspark.sql.types import StructField, StringType, StructType

from tests.sparktestingbase.sqltestcase import SQLTestCase


FIXTURES_DIR = os.path.join(
    os.path.dirname(os.path.realpath(__file__)),
    'fixtures',
)


@pytest.mark.datafiles(
    os.path.join(FIXTURES_DIR, 'awesomejob', "sourcepath"),
    keep_top_dir=True
)
def test_run_awesome_app_with_success(spark_session, spark_session_after_each, datafiles, path):
    source_path = str(datafiles.listdir()[0])
    destination_path = path / 'destinationpath/awesomejob/'

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

    ParsedArgs = namedtuple('ParsedArgs', 'source destination')
    parsed_args = ParsedArgs(source_path, destination_path)
    run(parsed_args)

    result_data_frame = spark_session.sql("SELECT * FROM testing.example")
    expected_data_frame = create_expected_data_frame()

    data_frame_suite = SQLTestCase()
    data_frame_suite.assertDataFrameEqual(expected=expected_data_frame, result=result_data_frame, tol=0)
