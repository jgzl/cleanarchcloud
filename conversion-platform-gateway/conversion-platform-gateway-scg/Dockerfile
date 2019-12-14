FROM harbor.dlihaifeng.cn/library/jdk8:1.0

MAINTAINER li7hai26@gmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /conversion-platform-gateway-scg

WORKDIR /conversion-platform-gateway-scg

EXPOSE 8000

ADD ./target/conversion-platform-gateway-scg.jar ./

CMD sleep 10;java -Xms1024m -Xmx1024m -Djava.security.egd=file:/dev/./urandom -jar conversion-platform-gateway-scg.jar