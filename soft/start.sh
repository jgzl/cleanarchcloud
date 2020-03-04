#!/bin/bash
echo '启动sentinel'
java -Dserver.port=8888 -Dcsp.sentinel.dashboard.server=192.168.0.3:8888 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.7.1.jar > /tmp/sentinel.log 2>&1 &