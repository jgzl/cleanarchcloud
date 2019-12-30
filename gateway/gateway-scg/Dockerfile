FROM registry.cn-shanghai.aliyuncs.com/saicmotor-dev/jdk8:1.0

MAINTAINER li7hai26@gmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /gateway-scg

WORKDIR /gateway-scg

EXPOSE 8000

ADD ./target/gateway-scg.jar ./

CMD sleep 10;java -Xms1024m -Xmx1024m -Djava.security.egd=file:/dev/./urandom -jar gateway-scg.jar