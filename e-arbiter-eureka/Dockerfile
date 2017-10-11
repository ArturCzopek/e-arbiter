FROM openjdk:8-jre-alpine
LABEL mantainer="e-Arbiter dev team <earbiterinfo@gmail.com>"

# Default values. You can override it in start or in docker-compose
ENV E_ARB_HOST="http://192.168.0.1"
ENV E_ARB_EUR_PORT=8761
ENV E_ARB_CFG_PORT=8096
ENV E_ARB_DEV_LOG_PATH="/var/logs"
ENV SPRING_PROFILES_ACTIVE="prod"

ADD  ./build/libs/e-arbiter-eureka.jar /app/e-arbiter-eureka.jar
WORKDIR app

CMD ["java", "-Xmx200m", "-jar", "e-arbiter-eureka.jar"]

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f ${E_ARB_HOST}:${E_ARB_EUR_PORT}/health || exit 1

EXPOSE ${E_ARB_EUR_PORT}