FROM registry.cn-shanghai.aliyuncs.com/saicmotor-dev/jdk8:1.0

MAINTAINER li7hai26@gmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /app

WORKDIR /app

EXPOSE 7000

ADD infrastructure-apm-admin.jar app.jar

CMD java -Xms1024m -Xmx1024m -Djava.security.egd=file:/dev/./urandom -jar app.jar