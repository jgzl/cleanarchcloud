##JWT生成密钥
###1.生成JKS文件
```shell script
keytool -genkeypair -alias [your alias] -keyalg RSA -keypass [your key pass] -keystore [your jks filename] -storepass [your store pass]
```
###2.导出工钥
```shell script
keytool -list -rfc --keystore [your jks filename] | openssl x509 -inform pem -pubkey
```
###3.生成公钥文件
生成文件pubkey.txt
###4.使用公钥文件
将 pubkey.txt 放到客户端 resources 目录下, 通过公钥解析 jwt token。
客户端也可以在启动时，通过访问认证服务的 token_key 资源路径获取公钥，通过公钥解析 jwt token。