#!/bin/sh

echo "The application will start in ${STARTUP_SLEEP}s..." && sleep ${STARTUP_SLEEP}
java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar "app.jar"
