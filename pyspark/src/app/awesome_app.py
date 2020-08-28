# Author: Gustavo Martin Morcuende
import argparse

from pyspark.sql import SparkSession

from app.awesome_job import AwesomeJob
from app.awesome_service import AwesomeService


def run(parsed_args):
    spark_session = SparkSession \
        .builder \
        .appName('awesome-app') \
        .enableHiveSupport() \
        .getOrCreate()

    awesome_service = AwesomeService()

    AwesomeJob(parsed_args.source, parsed_args.destination, spark_session, awesome_service).run()


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Awesome Spark application')
    parser.add_argument('--source', type=str, required=True, dest='source', help='Source path')
    parser.add_argument('--destination', type=str, required=True, dest='destination', help='Destination path')
    args = parser.parse_args()

    run(args)
