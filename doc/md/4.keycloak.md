##使用docker启动keycloak
###1.启动keycloak容器并且同时导出realm
```shell script
docker run -d -p 8080:8080 -e KEYCLOAK_USER=admin -e \
KEYCLOAK_PASSWORD=admin -v $(pwd):/tmp --name keycloak \
jboss/keycloak
```

###2.使用mysql+keycloak
####具体keycloak环境变量
```text
DB_VENDOR: mysql,postgres,maridb,oracle,mssql,h2
DB_ADDR: 使用IP或者IP:PORT形式，IP:PORT形式可以不用填写DB_PORT
DB_PORT: 数据库端口
DB_DATABASE: 数据库名字，默认是keycloak
DB_SCHEMA: Specify name of the schema to use for DB that support schemas (optional, default is public on Postgres).
DB_USER: Specify user to use to authenticate to the database (optional, default is ``).
DB_USER_FILE: Specify user to authenticate to the database via file input (alternative to DB_USER).
DB_PASSWORD: Specify user's password to use to authenticate to the database (optional, default is ``).
DB_PASSWORD_FILE: Specify user's password to use to authenticate to the database via file input (alternative to DB_PASSWORD)
```
####创建网络
```shell script
docker network create keycloak-network
```
####启动mysql容器:
```shell script
docker run --name mysql-keycloak -d -p 3306:3306 \
--net keycloak-network -e MYSQL_DATABASE=keycloak \
-e MYSQL_USER=keycloak -e MYSQL_PASSWORD=keycloak \
-e MYSQL_ROOT_PASSWORD=root mysql
```
####启动keycloak容器
```shell script
docker run --net keycloak-network  -d -p 8080:8080 \
-e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin \
-e DB_VENDOR=mysql -e DB_ADDR=mysql-keycloak \
-e DB_PORT=3306 -e DB_DATABASE=keycloak -e DB_USER=keycloak \
-e DB_PASSWORD=keycloak -v $(pwd):/tmp --name keycloak jboss/keycloak
```

####具体资料
1. Docker Hub jboss/keycloak [Docker镜像](https://hub.docker.com/r/jboss/keycloak)
2. keycloak官网下载地址 [Downloads](https://www.keycloak.org/downloads.html)