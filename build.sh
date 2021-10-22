#!/bin/bash
if [ $# -lt 1 ]; then
    echo '必须输入环境变量(dev,fat,uat,prod)'
    exit
fi

work_path=$(dirname $(readlink -f $0))
env=$1
base_opts='-Dmaven.test.skip=true -P'$env

echo 'env:'$env

mvn_docker_build_push(){
  mvn docker:build docker:push $base_opts
}

build_mvn(){
  mvn clean package $base_opts
}

build_docker(){
  dirArray=(
    cleanarch-infrastructure/cleanarch-infrastructure-gateway
    cleanarch-infrastructure/cleanarch-infrastructure-camunda
    cleanarch-infrastructure/cleanarch-infrastructure-codegen
    cleanarch-infrastructure/cleanarch-infrastructure-gateway
    cleanarch-infrastructure/cleanarch-infrastructure-upms
    cleanarch-business/cleanarch-business-demo
    cleanarch-business/cleanarch-business-ssoclient1
    cleanarch-business/cleanarch-business-ssoclient2
  )
  for dir in ${dirArray[@]} ; do
    echo 'dir:'$dir
    mvn docker:build docker:push -pl $dir $base_opts
  done
}

build_mvn
build_docker