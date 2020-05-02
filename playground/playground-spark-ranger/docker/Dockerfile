FROM apache-ranger-builder AS builder
FROM debian:stretch

# Repository
RUN apt-get clean
RUN apt-get update

# Tools
RUN apt-get install -y net-tools
RUN apt-get install -y python=2.7.13-2
RUN apt-get install -y build-essential=12.3
RUN apt-get install -y openjdk-8-jdk
ENV JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"

# PostgreSQL
RUN apt-get install -y postgresql
RUN apt-get install -y libpostgresql-jdbc-java

# Solr user
RUN useradd --no-log-init -r -d /opt/solr solr && \
    mkdir -p /var/log/solr && \
    chown -R solr: /var/log/solr

# From builder to end image
COPY --from=builder /opt/apache/ranger /opt/apache/ranger
COPY --from=builder --chown=solr:solr /opt/solr/ /opt/solr/

# Ranger configuration
WORKDIR /opt/apache/ranger
RUN sed -i "s|^DB_FLAVOR=MYSQL|DB_FLAVOR=POSTGRES|g" install.properties && \
    sed -i "s|^SQL_CONNECTOR_JAR=/usr/share/java/mysql-connector-java.jar|SQL_CONNECTOR_JAR=/usr/share/java/postgresql.jar|g" install.properties && \
    sed -i "s|^audit_solr_urls=|audit_solr_urls=http://localhost:6083/solr/ranger_audits|g" install.properties && \
    sed -i "s|^db_root_user=root|db_root_user=rangerroot|g" install.properties && \
    sed -i "s|^db_root_password=$|db_root_password=rangerroot|g" install.properties && \
    sed -i "s|^db_password=$|db_password=rangeradmin|g" install.properties && \
    sed -i "s|^db_host=localhost|db_host=localhost:5432|g" install.properties && \
    sed -i "s|^rangerAdmin_password=$|rangerAdmin_password=ranger123|g" install.properties && \
    sed -i "s|^rangerTagsync_password=$|rangerTagsync_password=ranger123|g" install.properties && \
    sed -i "s|^rangerUsersync_password=$|rangerUsersync_password=ranger123|g" install.properties && \
    sed -i "s|^keyadmin_password=$|keyadmin_password=ranger123|g" install.properties
RUN chmod 554 setup.sh

# PostgreSQL configuration
RUN echo 'host all rangerroot  127.0.0.1/32   trust' >> /etc/postgresql/9.6/main/pg_hba.conf && \
    echo 'host all rangeradmin  127.0.0.1/32   trust' >> /etc/postgresql/9.6/main/pg_hba.conf
ADD scripts/create_user.sql /var/lib/postgresql/

# Entrypoint
ADD scripts/entryproint.sh /

# Ranger admin site http: 6080
# Ranger admin site https: 6182
# solr Ranger: 6083
# PostgreSQL: 5432
EXPOSE 6080 6182 6083 5432

CMD /bin/bash
# ENTRYPOINT ["/entryproint.sh"]
