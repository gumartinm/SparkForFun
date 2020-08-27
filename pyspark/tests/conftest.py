import tempfile
import uuid
from pathlib import Path

import pytest
import shutil
from pyspark import SparkConf
from pyspark.sql import SparkSession


@pytest.fixture(scope='function')
def path():
    # Before each
    path = tempfile.TemporaryDirectory()

    yield Path(path.name)

    # After each
    path.cleanup()


@pytest.fixture(scope='function')
def spark_session_after_each(spark_session):

    yield spark_session

    # After each
    jvm_session = spark_session._jvm.SparkSession.getActiveSession().get()
    jvm_session.sharedState().cacheManager().clearCache()
    jvm_session.sessionState().catalog().reset()


@pytest.fixture(scope='class')
def spark_session():
    # Before All
    shutil.rmtree(path=Path("spark-warehouse"), ignore_errors=True)
    shutil.rmtree(path=Path("metastore_db"), ignore_errors=True)
    random_uuid = str(uuid.uuid4())
    warehouse_path = Path("spark-warehouse", random_uuid)
    metastore_path = Path("metastore_db", random_uuid)

    spark_conf = SparkConf() \
        .set("spark.unsafe.exceptionOnMemoryLeak", "true") \
        .set("spark.ui.enabled", "false") \
        .set("hive.stats.jdbc.timeout", "80") \
        .set("spark.sql.session.timeZone", "UTC") \
        .set("spark.sql.warehouse.dir", str(warehouse_path.absolute())) \
        .set("javax.jdo.option.ConnectionURL",
             "jdbc:derby:;databaseName={0};create=true".format(str(metastore_path.absolute())))

    spark_session = SparkSession \
        .builder \
        .master("local[2]") \
        .appName("test-sql-context") \
        .config(conf=spark_conf) \
        .enableHiveSupport() \
        .getOrCreate()

    yield spark_session

    # After All
    spark_session.stop()
    jvm_session = spark_session._jvm.SparkSession.getActiveSession().get()
    jvm_session.clearActiveSession()
    jvm_session.clearDefaultSession()
    shutil.rmtree(path=warehouse_path, ignore_errors=True)
    shutil.rmtree(path=metastore_path, ignore_errors=True)