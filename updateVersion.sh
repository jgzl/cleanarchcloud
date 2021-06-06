#!/bin/bash
workdir=$(cd $(dirname $0); pwd)
echo '工作目录:' $workdir
cd $workdir/cleanarch-core/cleanarch-starter-dependencies && mvn versions:set -DgenerateBackupPoms=false -DnewVersion=$1
cd $workdir/cleanarch-core && mvn versions:set -DgenerateBackupPoms=false -DnewVersion=$1
cd $workdir && mvn versions:set -DgenerateBackupPoms=false -DnewVersion=$1
mvn clean $2