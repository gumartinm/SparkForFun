#!/bin/bash

set -euo pipefail

export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64

/etc/init.d/postgresql start

FILE='/opt/first_time'
if [ ! -f "$FILE" ]; then
    su -c 'psql -f create_user.sql' - postgres
    /opt/apache/ranger/setup.sh
    touch /opt/first_time
fi

# Run solr
# /opt/solr/ranger_audit_server/scripts/start_solr.sh
export SOLR_INCLUDE=/opt/solr/ranger_audit_server/scripts/solr.in.sh
/opt/solr/bin/solr start

# Run Apache Ranger
/opt/apache/ranger/ews/ranger-admin-services.sh start

sleep 10
tail -F /opt/apache/ranger/ews/logs/*.log
