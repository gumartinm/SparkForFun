class AwesomeJob:

    def __init__(self, source_path, destination_path, spark_session, awesome_service):
        self.source_path = source_path
        self.destination_path = destination_path
        self.spark_session = spark_session
        self.awesome_service = awesome_service

    def run(self):
        json_data_frame = self.spark_session.read.json(self.source_path)
        json_schema = json_data_frame.schema
        upper_case_json_schema = self.awesome_service.rename_columns_to_upper_case(json_schema)
        upper_case_json_data_frame = self.spark_session.createDataFrame(json_data_frame.rdd, upper_case_json_schema)
        upper_case_json_data_frame.write.json(self.destination_path)
