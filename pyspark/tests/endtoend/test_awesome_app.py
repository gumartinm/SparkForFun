# Author: Gustavo Martin Morcuende
import os
from collections import namedtuple

import pytest
from app.awesome_app import run

from tests.commons import create_expected_data_frame
from tests.holdenkarau.sqltestcase import SQLTestCase

FIXTURES_DIR = os.path.join(
    os.path.dirname(os.path.realpath(__file__)),
    'fixtures',
)


@pytest.mark.datafiles(
    os.path.join(FIXTURES_DIR, 'awesomeapp', 'sourcepath'),
    keep_top_dir=True
)
@pytest.mark.parametrize('spark_custom_confs', [[]], scope='class')
class TestAwesomeApp:

    def test_run_awesome_app_with_success(self, spark_session, spark_session_after_each, datafiles, path):
        source_path = str(datafiles.listdir()[0])
        destination_path = path / 'destinationpath/awesomeapp/'

        ParsedArgs = namedtuple('ParsedArgs', 'source destination')
        parsed_args = ParsedArgs(source_path, destination_path)
        run(parsed_args)

        result_data_frame = spark_session.sql('SELECT * FROM testing.example')
        expected_data_frame = create_expected_data_frame(spark_session)

        data_frame_suite = SQLTestCase()
        data_frame_suite.assertDataFrameEqual(expected=expected_data_frame, result=result_data_frame, tol=0)
