FROM bde2020/spark-base:2.4.5-hadoop2.7

LABEL maintainer="Gustavo Martin Morcuende"

RUN apk update
RUN apk add procps

ENV SPARK_MASTER_NAME spark-master
ENV SPARK_MASTER_HOST spark-master
ENV SPARK_MASTER_PORT 7077
ENV SPARK_MASTER_WEBUI_PORT 8082
ENV SPARK_NO_DAEMONIZE true

COPY spark-defaults.conf /spark/conf
COPY log4j.properties /spark/conf
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 6060 10000

ENTRYPOINT ["/entrypoint.sh"]
CMD ["/bin/bash", "/spark/sbin/start-thriftserver.sh"]
