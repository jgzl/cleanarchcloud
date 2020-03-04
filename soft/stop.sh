#!/bin/bash
echo '停止sentinel-dashboard服务'
ps aux|grep sentinel-dashboard|grep -v grep|awk '{print $2}'|xargs kill -9