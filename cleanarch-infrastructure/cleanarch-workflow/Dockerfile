FROM docker.io/adoptopenjdk:8u292-b10-jdk-hotspot

MAINTAINER li7hai26@gmail.com

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /app

WORKDIR /app

EXPOSE 8014

ADD target/app.jar app.jar

CMD java $JAVA_OPTS -jar app.jar