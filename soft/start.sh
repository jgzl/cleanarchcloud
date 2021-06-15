#!/bin/bash
echo '启动sentinel'
java -Dserver.port=8888 \
-Dcsp.sentinel.dashboard.server=localhost:8888 \
-Dproject.name=sentinel-dashboard \
-Dnacos.serverAddr=localhost:8848 \
-Dnacos.namespace=sentinel-dev \
-Dspring.influx.url=http://localhost:8086 \
-Dspring.influx.user=root \
-Dspring.influx.password=root \
-Dspring.influx.database=sentinel_log \
-jar sentinel-dashboard.jar > /tmp/sentinel.log 2>&1 &