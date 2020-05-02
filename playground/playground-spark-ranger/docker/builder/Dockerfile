FROM debian:stretch

# Repository
RUN apt-get update

# Build tools
RUN apt-get install -y build-essential
RUN apt-get install -y openjdk-8-jdk
RUN apt-get install -y maven
RUN apt-get install -y wget
RUN apt-get install -y python
RUN apt-get install -y git
ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"

# Build Ranger
RUN mkdir -p /opt/download
WORKDIR /opt/download/
RUN wget -nv http://apache.rediris.es/ranger/2.0.0/apache-ranger-2.0.0.tar.gz -O apache-ranger-2.0.0.tar.gz
RUN mkdir -p /opt/build/ranger
RUN tar -xf apache-ranger-2.0.0.tar.gz --strip-components 1 -C /opt/build/ranger
WORKDIR /opt/build/ranger
RUN mvn -DskipTests=true -Dmaven.test.skip=true clean compile package install assembly:assembly
RUN mkdir -p /opt/apache/ranger
RUN tar -xf target/ranger-2.0.0-admin.tar.gz --strip-components 1 -C /opt/apache/ranger

# Build Solr
WORKDIR /opt/apache/ranger/contrib/solr_for_audit_setup/
RUN sed -i 's|SOLR_INSTALL=false|SOLR_INSTALL=true|' install.properties
RUN sed -i "s|^SOLR_DOWNLOAD_URL=|SOLR_DOWNLOAD_URL=http://archive.apache.org/dist/lucene/solr/5.2.1/solr-5.2.1.tgz|" install.properties
RUN chmod 554 setup.sh
RUN ./setup.sh
