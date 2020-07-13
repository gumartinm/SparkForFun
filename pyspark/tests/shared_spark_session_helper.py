from pyspark import SparkConf
from pyspark.sql import SparkSession
from pathlib import Path
import pytest
import tempfile
import uuid


class SharedSparkSessionHelper:

    def __init__(self):
        random_uuid = uuid.uuid4().urn
        self.__warehouse_path = Path("spark-warehouse", random_uuid)
        self.__metastore_path = Path("metastore_db", random_uuid)
        self.__spark = None
        self.path = None

    def spark_conf(self):
        return SparkConf() \
            .set("spark.unsafe.exceptionOnMemoryLeak", "true") \
            .set("spark.ui.enabled", "false") \
            .set("hive.stats.jdbc.timeout", "80") \
            .set("spark.sql.session.timeZone", "UTC") \
            .set("spark.sql.warehouse.dir", self.__warehouse_path.absolute()) \
            .set("javax.jdo.option.ConnectionURL",
                 "jdbc:derby:;databaseName=$1;create=true".format(self.__metastore_path.absolute()))

    @pytest.fixture(scope='session', autouse=True)
    def run_before_and_after_all(self):
        # Before All
        self.__spark = SparkSession \
            .builder \
            .master("local[2]") \
            .appName("test-sql-context") \
            .config(self.spark_conf) \
            .enableHiveSupport() \
            .getOrCreate()

        yield

        # After All
        self.__spark.stop()
        self.__warehouse_path.rmdir()
        self.__metastore_path.rmdir()

    @pytest.fixture(autouse=True)
    def run_before_and_after_each(self):
        # Before Each
        self.path = tempfile.TemporaryDirectory().name

        yield

        # After Each
        Path(self.path).rmdir()
        self.__spark.sharedState.cacheManager.clearCache()
        self.__spark.sessionState.catalog.reset()
