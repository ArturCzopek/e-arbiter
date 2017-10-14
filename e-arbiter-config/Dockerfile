FROM openjdk:8-jre-alpine
LABEL mantainer="e-Arbiter dev team <earbiterinfo@gmail.com>"

RUN apk --update add curl

# Default values. You can override it in start or in docker-compose
ENV E_ARB_HOST="http://192.168.0.1"
ENV E_ARB_CFG_PORT=8096
ENV E_ARB_CFG_NAME="Config Server"
ENV E_ARB_SUB_LOG_PATH="../logs"
ENV EUREKA_URI="http://192.168.0.1:8761/eureka"
ENV E_ARB_REMOTE_CFG_PATH="https://github.com/ArturCzopek/e-arbiter-config"
ENV SPRING_PROFILES_ACTIVE="dev-docker"

ADD  ./build/libs/e-arbiter-config.jar /app/e-arbiter-config.jar
WORKDIR app

CMD ["java", "-Xmx200m", "-jar", "e-arbiter-config.jar"]

HEALTHCHECK --interval=5s --timeout=10s CMD curl -v  ${E_ARB_HOST}:${E_ARB_CFG_PORT}/health || exit 1

EXPOSE ${E_ARB_CFG_PORT}