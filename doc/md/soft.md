# docker安装mysql5.7
```shell
docker run -d -p 3307:3306 --name mysql5.7 -e MYSQL_ROOT_PASSWORD=root  mysql:5.7.32
```

# docker安装mysql8

```shell
docker run -d -p 3308:3306 --name mysql8 -e MYSQL_ROOT_PASSWORD=root  mysql:8.0.25
```

# 获取2.0.1的nacos脚本 

[mysql-nacos](https://gitee.com/mirrors/Nacos/raw/2.0.1/distribution/conf/nacos-mysql.sql)

```shell
docker run -d -p 8848:8848 -p 9848:9848 -p 9849:9849 --link mysql8:mysql  \
-e MODE=standalone \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=mysql \
-e MYSQL_SERVICE_DB_NAME=nacos \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=root \
--name nacos nacos/nacos-server:2.0.1
```

# docker安装redis
```shell
docker run -d -p 6379:6379 --name redis redis:6.2.4-alpine --requirepass 123456
```

# docker安装openresty

```shell
docker run -v ~/data/openresty/conf.d:/etc/nginx/conf.d -p 80:80 -p 443:443 --name openresty -d openresty/openresty:1.19.3.2-alpine
```

# docker安装elasticsearch7

```shell
docker run -d --name=elasticsearch \
  --restart=always \
  -p 9200:9200 -p 9300:9300 \
  -e "discovery.type=single-node" \
  -v $PWD/elasticsearch/data:/usr/share/elasticsearch/data \
  -v $PWD/elasticsearch/logs:/usr/share/elasticsearch/logs \
elasticsearch:7.5.1
```

# docker安装skywalking(elasticsearch7)

```shell
docker run --name skywalking -d -p 1234:1234 -p 11800:11800 -p 12800:12800 --restart always --link elasticsearch:elasticsearch -e SW_STORAGE=elasticsearch -e SW_STORAGE_ES_CLUSTER_NODES=elasticsearch:9200 apache/skywalking-oap-server:8.6.0-es7 
```

# docker安装influxdb

```shell
docker run --rm influxdb:1.8.6-alpine influxd config > $PWD/influxdb/influxdb.conf

docker run -d -p 8086:8086 --name influxdb \
-v $PWD/influxdb/influxdb.conf:/etc/influxdb/influxdb.conf:ro \
-v $PWD/influxdb/data:/var/lib/influxdb/data \
influxdb:1.8.6-alpine -config /etc/influxdb/influxdb.conf

docker exec -it influxdb /bin/bash

influx

create user "root" with password 'root' with all privileges

create database skywalking
create database sentinel_log
```

# docker安装skywalking(influxdb)

```shell
docker run --name skywalking -d -p 11800:11800 -p 12800:12800 --restart always --link influxdb:influxdb -e SW_STORAGE=influxdb -e SW_STORAGE_INFLUXDB_URL=http://influxdb:8086 -e SW_STORAGE_INFLUXDB_USER=root -e SW_STORAGE_INFLUXDB_PASSWORD=root -e SW_STORAGE_INFLUXDB_DATABASE=skywalking apache/skywalking-oap-server:8.6.0-es7
```

# docker安装skywalking-ui

```shell
docker run --name skywalking-ui -d -p 8080:8080 --link skywalking:skywalking -e SW_OAP_ADDRESS=skywalking:12800 --restart always apache/skywalking-ui:8.6.0
```

# 应用修改JVM配置增加skwywalking agent进行监控

```shell
-javaagent:/Users/lihaifeng/data/skywalking/agent/skywalking-agent.jar -Dskywalking.agent.service_name=cleanarch-infrastructure-gateway -Dskywalking.collector.backend_service=localhost:11800
```

```shell
-javaagent:/Users/lihaifeng/data/skywalking/agent/skywalking-agent.jar -DSW_AGENT_NAME=cleanarch-infrastructure-gateway -DSW_AGENT_COLLECTOR_BACKEND_SERVICES=localhost:11800
```

# docker安装zookeeper

```shell
docker run -d --name zookeeper -p 2181:2181 -t wurstmeister/zookeeper
```

```shell
docker run -d --name kafka \
-p 9092:9092 --link zookeeper:zookeeper \
-e KAFKA_BROKER_ID=0 \
-e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://10.144.98.59:9092 \
-e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 wurstmeister/kafka
```
