# docker安装mysql
```shell
docker run -d -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=root  mysql:5.7.32
```

# 获取2.0.0的nacos脚本 
[mysql-nacos](https://gitee.com/mirrors/Nacos/raw/2.0.0/distribution/conf/nacos-mysql.sql)
```shell
docker run -d -p 8848:8848 -p 9848:9848 -p 9849:9849 \
-e MODE=standalone \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=10.144.98.59 \
-e MYSQL_SERVICE_DB_NAME=nacos \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=root \
--name nacos nacos/nacos-server:2.0.1
```

# docker安装nacos
```shell

docker run -d -p 8848:8848 \
-e MODE=standalone \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=10.144.98.59 \
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

# docker安装influxdb
```shell
docker run --rm 10.91.198.45/library/influxdb:1.8.6-alpine influxd config > influxdb.conf

docker run -d -p 8086:8086 --name influxdb \
-v $PWD/influxdb.conf:/etc/influxdb/influxdb.conf:ro \
10.91.198.45/library/influxdb:1.8.6-alpine -config /etc/influxdb/influxdb.conf

docker exec -it influxdb /bin/bash

influx

create user "root" with password 'root' with all privileges

create database sentinel_log
```

# docker安装openresty

```shell
docker run -v ~/data/openresty/conf.d:/etc/nginx/conf.d -p 80:80 -p 443:443 --name openresty -d openresty/openresty:1.19.3.2-alpine
```

