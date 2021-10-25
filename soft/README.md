#使用集群版sentinel进行集群流控
集群版Sentinel源码地址：[Gitee](https://gitee.com/laofeng/hasentinel.git)
#1.Influxdb环境搭建

##通过Docker安装Influxdb

```shell
docker pull influxdb
#安装第一个节点
docker run -d -p 18083:8083 -p18086:8086 --name influxDbService1 influxdb
#安装第二个节点
docker run -d -p 28083:8083 -p28086:8086 --name influxDbService2 influxdb
```

##连接到Influxdb中，两个节点中都创建名为“sentinel_db”的数据库：
```shell
fenglibin$ influx -host 127.0.0.1 -port 18086
Visit https://enterprise.influxdata.com to register for updates, InfluxDB server management, and monitoring.
Connected to http://127.0.0.1:28086 version 1.7.9
InfluxDB shell version: 1.1.1
> show databases;
name: databases
name
----
_internal
> create database sentinel_db
> use sentinel_db
Using database sentinel_db
> show measurements
> 
```

#2.Zookeeper搭建

##通过Docker安装Zookeeper。

```shell
docker pull zookeeper
docker run -d --name zookeeper --p 2181:2181  -d zookeeper
```

##在ZK中创建如下节点：
```shell
SENTINEL-GROUP
SENTINEL-GROUP/APP-MACHINES
SENTINEL-GROUP/AUTHORITY-RULES
SENTINEL-GROUP/DEGRADE-RULES
SENTINEL-GROUP/FLOW-RULES
SENTINEL-GROUP/HOT-RULES
SENTINEL-GROUP/SYSTEM-RULES
```
##具体操作如下,执行ZK的客户端命令进入控制台：
 ```shell
zkCli.sh
```
##在控制台通过创建节点的命令创建节点：
```shell
[zk: localhost:2181(CONNECTED) 1] create /SENTINEL-GROUP
Created /SENTINEL-GROUP
[zk: localhost:2181(CONNECTED) 2] create /SENTINEL-GROUP/APP-MACHINES
Created /SENTINEL-GROUP/APP-MACHINES
```
#３、Redis搭建
##通过Docker安装Redis。
```shell
docker pull redis docker run -d --name
redis -p 6379:6379 redis
```