#!/bin/sh
exec java ${JAVA_OPTS} -XX:+UseContainerSupport -XX:+ExitOnOutOfMemoryError -Djava.security.egd=file:/dev/./urandom -Duser.timezone=$TZ -jar "app.jar" "$@"
