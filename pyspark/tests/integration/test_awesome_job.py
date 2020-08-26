import os

import pytest
from app.awesome_job import AwesomeJob
from app.awesome_service import AwesomeService

FIXTURES_DIR = os.path.join(
    os.path.dirname(os.path.realpath(__file__)),
    'fixtures',
)


@pytest.mark.datafiles(
    os.path.join(FIXTURES_DIR, 'awesomejob', "sourcepath"),
    keep_top_dir=True
)
def test_run_awesome_job_with_success(spark_session, datafiles, path):
    source_path = str(datafiles.listdir()[0])
    service = AwesomeService()

    AwesomeJob(source_path, path + '/destinationpath/awesomejob/', spark_session, service).run()
