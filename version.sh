#!/bin/sh
version=$1
if [ "$version" ]; then
  echo 输入的新版本号为$version
else
  echo 必须要输入新版本号
  exit 0
fi
mvn clean install -Drevision=$1 -Dmaven.test.skip=true