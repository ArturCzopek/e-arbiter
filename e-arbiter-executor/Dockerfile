FROM earbiterinfo/e-arbiter-compile-box
LABEL mantainer="e-Arbiter dev team <earbiterinfo@gmail.com>"

# Default values. You can override it in start or in docker-compose
ENV E_ARB_HOST="http://192.168.0.1"
ENV E_ARB_CLIENT_PORT=4200
ENV E_ARB_API_PORT=8080
ENV E_ARB_EXEC_PORT=8091
ENV E_ARB_HYS_PORT=8989
ENV E_ARB_CFG_PORT=8096
ENV E_ARB_DEV_LOG_PATH="../logs"
ENV E_ARB_DEV_EXEC_BINDING=/compilebox/solution/
ENV EUREKA_URI="http://192.168.0.1:8761/eureka"
ENV SPRING_PROFILES_ACTIVE="dev"

#RUN touch /var/run/docker.sock
#RUN chmod 777 /var/run/docker.sock

ENV LANG=C.UTF-8
ENV JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk/jre
ENV PATH=$PATH:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin

ENV JAVA_VERSION=8u131
ENV JAVA_ALPINE_VERSION=8.131.11-r2

# add a simple script that can auto-detect the appropriate JAVA_HOME value
# based on whether the JDK or only the JRE is installed
RUN { \
		echo '#!/bin/sh'; \
		echo 'set -e'; \
		echo; \
		echo 'dirname "$(dirname "$(readlink -f "$(which javac || which java)")")"'; \
	} > /usr/local/bin/docker-java-home \
	&& chmod +x /usr/local/bin/docker-java-home

RUN set -x \
	&& apk add --no-cache \
		openjdk8-jre="$JAVA_ALPINE_VERSION" \
	&& [ "$JAVA_HOME" = "$(docker-java-home)" ]

ADD  ./build/libs/e-arbiter-executor.jar ./e-arbiter-executor.jar

RUN ls -a

CMD ["java", "-Xmx200m", "-jar", "e-arbiter-executor.jar"]

EXPOSE ${E_ARB_EXEC_PORT}