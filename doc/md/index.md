## 初始化数据库
sql/create_database.sql,create_table.sql全部执行一遍
## 下载nacos注册配置中心
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
## 下载sentinel流控注册中心

## 配置setttings.xml在<servers></servers>中增加节点
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
##使用oauth2访问认证服务
###1.使用授权码模式登录

get访问 localhost:8030/oauth/authorize?response_type=code&client_id=app&redirect_uri=http://www.jianshu.com&scope=server

输入账户名密码 lihaifeng 123456 授权，获取到code，用来获取access_token

post访问 localhost:8030/oauth/token

body中的内容为
```text
code:aBEZvx
grant_type:authorization_code
redirect_uri:http://www.jianshu.com
scope:server
client_id:app
client_secret:app
```
获得access_token以及refresh_token
```json
{
    "access_token": "91d2b62a-4a54-4216-a68f-b2ee7856138b",
    "token_type": "bearer",
    "refresh_token": "cccef97b-9871-4912-8543-e9be37c2a532",
    "expires_in": 43188,
    "scope": "server",
    "license": "made by platform",
    "active": true,
    "user_id": null,
    "username": "lihaifeng"
}
```
###2.使用密码模式登录

使用get方式访问 http://127.0.0.1:8030/oauth/token?username=lihaifeng&password=123456&randomStr=17781578043033844&code=12&grant_type=password&scope=server

输入客户端账号密码 app app,获得access_token以及refresh_token
```json
{
  "access_token": "4ad6bab8-42ca-44ee-b852-c04469c65e36",
  "token_type": "bearer",
  "refresh_token": "e4ae892b-8d53-4d5f-90cc-46b0e135b01a",
  "expires_in": 43190,
  "scope": "server",
  "license": "made by platform",
  "active": true,
  "user_id": null,
  "username": "lihaifeng"
}
```