FROM registry.dc/jdk/21-openjre-alpine:latest

ENV TZ=Etc/UTC \
	  LANG=en_US.UTF-8 \
	  LANGUAGE=en_US:en \
      JAVA_OPTS=""

# add directly the war
COPY --chmod=755 src/main/docker/entrypoint.sh /entrypoint.sh
COPY build/libs/*.jar /app.jar

ENTRYPOINT ["/entrypoint.sh"]
