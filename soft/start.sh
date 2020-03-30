#!/bin/bash
echo '启动sentinel'
java -Dserver.port=8888 -Dcsp.sentinel.dashboard.server=127.0.0.1:8888 -Dproject.name=sentinel-dashboard -Dnacos.serverAddr=106.14.155.108:8848 -jar sentinel-dashboard.jar > /tmp/sentinel.log 2>&1 &