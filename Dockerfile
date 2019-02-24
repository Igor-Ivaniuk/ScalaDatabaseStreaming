FROM openjdk:11-jdk

LABEL maintainer="vivisector.ua@gmail.com"

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    STARTUP_SLEEP=10 \
    JAVA_OPTS=""

VOLUME /tmp
WORKDIR /opt
ADD start.sh start.sh
ADD ./target/*.jar app.jar
ENTRYPOINT ["./start.sh"]

