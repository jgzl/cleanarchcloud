FROM harbor.dlihaifeng.cn/library/jdk8:1.0

MAINTAINER li7hai26@gmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /conversion-platform-application-demo

WORKDIR /conversion-platform-application-demo

EXPOSE 8020

ADD ./target/conversion-platform-application-demo.jar ./

CMD sleep 10;java -Xms1024m -Xmx1024m -Djava.security.egd=file:/dev/./urandom -jar conversion-platform-application-demo.jar