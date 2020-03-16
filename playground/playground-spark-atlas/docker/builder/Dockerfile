FROM maven:3.6.0-jdk-8

ARG ATLAS_VERSION=2.0.0

## Build Atlas
RUN mkdir -p /opt/download
WORKDIR /opt/download/
RUN wget -nv https://archive.apache.org/dist/atlas/${ATLAS_VERSION}/apache-atlas-${ATLAS_VERSION}-sources.tar.gz
RUN mkdir -p /opt/build/atlas
RUN tar zxf apache-atlas-${ATLAS_VERSION}-sources.tar.gz --strip-components 1 -C /opt/build/atlas
WORKDIR /opt/build/atlas
RUN sed -i "s#http://repo1.maven.org/maven2#https://repo1.maven.org/maven2#g" pom.xml
RUN mvn -DskipTests=true -Dmaven.test.skip=true clean package -Pdist,embedded-hbase-solr
RUN mkdir -p /opt/apache/atlas
RUN tar zxf /opt/build/atlas/distro/target/apache-atlas-${ATLAS_VERSION}-server.tar.gz --strip-components 1 -C /opt/apache/atlas
