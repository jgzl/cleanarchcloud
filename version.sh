#!/bin/sh
version=$1
if [ "$version" ]; then
  echo 输入的新版本号为$version
else
  echo 必须要输入新版本号
  exit 0
fi
# 参数 						默认值 					说明
# allowSnapshots 			false 					是否更新-snapshot快照版
# artifactId 				${project.artifactId} 	指定artifactId
# generateBackupPoms 		true 					是否生成备份文件用于回退版本号
# groupId 					${project.groupId} 		指定groupId
# newVersion 				设置的新版本号
# nextSnapshot 				false 					更新版本号为下一个快照版本号
# oldVersion 				${project.version} 		指定需要更新的版本号可以使用缺省'*'
# processAllModules 		false 					是否更新目录下所有模块无论是否声明父子节点
# processDependencies 		true 					是否更新依赖其的版本号
# processParent 			true 					是否更新父节点的版本号
# processPlugins 			true 					是否更新插件中的版本号
# processProject 			true 					是否更新模块自身的版本号
# removeSnapshot 			false 					移除snapshot快照版本，使之为release稳定版
# updateMatchingVersions 	true 					是否更新在子模块中显式指定的匹配版本(如/项目/版本)

# 方便改参数
#mvn -f "pom.xml" versions:set -DoldVersion=* -DnewVersion=$1 -DprocessAllModules=true -DallowSnapshots=true -DgenerateBackupPoms=true -DupdateMatchingVersions=true

#简化版命令：
mvn versions:set -DnewVersion=$1

#回退版本号
# mvn versions:revert

#确认修改过的版本号
# mvn versions:commit