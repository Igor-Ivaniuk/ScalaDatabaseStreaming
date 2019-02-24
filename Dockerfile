FROM openjdk:11-jdk

LABEL maintainer="vivisector.ua@gmail.com"

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    STARTUP_SLEEP=0 \
    JAVA_OPTS=""

VOLUME /tmp
WORKDIR /opt
COPY ./target/*.jar /opt/app.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/app.jar"]

