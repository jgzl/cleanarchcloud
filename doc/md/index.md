# 初始化数据库
sql/create_database.sql,create_table.sql全部执行一遍
# 下载nacos注册配置中心
```shell script
wget https://github.com/alibaba/nacos/releases/download/1.1.4/nacos-server-1.1.4.tar.gz
tar -zxvf nacos-server-1.1.4.tar.gz
cd nacos-server-1.1.4/conf
vim application.properties
#增加mysql配置
spring.datasource.platform=mysql
db.num=1
db.url.0=jdbc:mysql://192.168.0.5:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user=root
db.password=root
```
# 下载sentinel流控注册中心

# 配置setttings.xml在<servers></servers>中增加节点
```xml
<server>
    <id>docker-hub</id>
    <username>dlhf</username>
    <password>952726SS507.</password>
    <configuration>
        <email>li7hai26@mail.com</email>
    </configuration>
</server>
<server>
    <id>aliyun-registry</id>
    <username>鼎峯云科技</username>
    <password>Root1q2w</password>
    <configuration>
        <email>li7hai26@mail.com</email>
    </configuration>
</server>
<server>
    <id>harbor-registry</id>
    <username>admin</username>
    <password>Harbor12345</password>
    <configuration>
        <email>li7hai26@mail.com</email>
    </configuration>
</server>
```