class AwesomeJob:

    def __init__(self, source_path, destination_path, spark, awesome_service):
        self.source_path = source_path
        self.destination_path = destination_path
        self.spark = spark
        self.awesome_service = awesome_service
        self.logger = AwesomeJob.__logger(spark)

    def run(self):
        self.logger.info("Running AwesomeJob")

        json_data_frame = self.spark.read.json(self.source_path)
        json_schema = json_data_frame.schema
        upper_case_json_schema = self.awesome_service.rename_columns_to_upper_case(json_schema)
        upper_case_json_data_frame = self.spark.createDataFrame(json_data_frame.rdd, upper_case_json_schema)
        upper_case_json_data_frame.write.json(self.destination_path)

        self.logger.info("End running AwesomeJob")

    @staticmethod
    def __logger(spark):
        log4j_logger = spark.sparkContext._jvm.org.apache.log4j
        return log4j_logger.LogManager.getLogger(__name__)
