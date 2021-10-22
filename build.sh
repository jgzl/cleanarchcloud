#!/bin/bash
if [ $# -lt 1 ]; then
    echo '必须输入环境变量(dev,fat,uat,prod)'
    exit
fi

work_path=$(dirname $(readlink -f $0))
env=$1
echo 'env:'$env

build_mvn(){
  mvn clean package -Dmaven.test.skip=true -P$env
}

build_docker(){
  cd $work_path/cleanarch-infrastructure/cleanarch-infrastructure-gateway && mvn docker:build docker:push -Dmaven.test.skip -P$env
  cd $work_path/cleanarch-infrastructure/cleanarch-infrastructure-camunda && mvn docker:build docker:push -Dmaven.test.skip -P$env
  cd $work_path/cleanarch-infrastructure/cleanarch-infrastructure-codegen && mvn docker:build docker:push -Dmaven.test.skip -P$env
  cd $work_path/cleanarch-infrastructure/cleanarch-infrastructure-gateway && mvn docker:build docker:push -Dmaven.test.skip -P$env
  cd $work_path/cleanarch-infrastructure/cleanarch-infrastructure-upms && mvn docker:build docker:push -Dmaven.test.skip -P$env
  cd $work_path/cleanarch-business/cleanarch-business-demo && mvn docker:build docker:push -Dmaven.test.skip -P$env
  cd $work_path/cleanarch-business/cleanarch-business-ssoclient1 && mvn docker:build docker:push -Dmaven.test.skip -P$env
  cd $work_path/cleanarch-business/cleanarch-business-ssoclient2 && mvn docker:build docker:push -Dmaven.test.skip -P$env
}

build_all(){
  pwd
  build_mvn
  build_docker
}

build_all

