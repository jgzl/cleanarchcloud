FROM harbor.dlihaifeng.cn/library/jdk8:1.0

MAINTAINER li7hai26@gmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /conversion-platform-infrastructure-apm-admin

WORKDIR /conversion-platform-infrastructure-apm-admin

EXPOSE 8010

ADD ./target/conversion-platform-infrastructure-apm-admin.jar ./

CMD sleep 10;java -Xms1024m -Xmx1024m -Djava.security.egd=file:/dev/./urandom -jar conversion-platform-infrastructure-apm-admin.jar