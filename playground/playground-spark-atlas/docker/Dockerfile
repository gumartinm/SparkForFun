FROM apache-atlas-builder AS builder
FROM debian:stretch

# Repository
RUN apt-get clean
RUN apt-get update

# Tools
RUN apt-get install -y net-tools
RUN apt-get install -y python
RUN apt-get install -y build-essential
RUN apt-get install -y openjdk-8-jdk
ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"

# Atlas user
RUN groupadd -r atlas -g 21000
RUN useradd --no-log-init -r -g atlas -u 21000 -d /opt/apache/atlas atlas

# From builder to end image
COPY --from=builder --chown=atlas:atlas /opt/apache/atlas /opt/apache/atlas

# Atlas configuration
USER atlas:atlas

WORKDIR /opt/apache/atlas

EXPOSE 21000

CMD /bin/bash
# ENTRYPOINT ["/bin/bash", "-c", "/opt/apache/atlas/bin/atlas_start.py; tail -fF /opt/apache/atlas/logs/atlas*.out"]
