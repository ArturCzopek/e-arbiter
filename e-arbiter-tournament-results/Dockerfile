FROM openjdk:8-jre-alpine
LABEL mantainer="e-Arbiter dev team <earbiterinfo@gmail.com>"

# Default values. You can override it in start or in docker-compose
ENV E_ARB_HOST="http://192.168.0.1"
ENV E_ARB_CLIENT_PORT=4200
ENV E_ARB_API_PORT=8080
ENV E_ARB_HYS_PORT=8989
ENV E_ARB_TRES_PORT=8093
ENV E_ARB_CFG_PORT=8096
ENV E_ARB_SUB_LOG_PATH="../logs"
ENV EUREKA_URI="http://192.168.0.1:8761/eureka"
ENV SPRING_PROFILES_ACTIVE="dev"

ADD  ./build/libs/e-arbiter-tournament-results.jar /app/e-arbiter-tournament-results.jar
WORKDIR app

CMD ["java", "-Xmx200m", "-jar", "e-arbiter-tour-res.jar"]

EXPOSE ${E_ARB_TRES_PORT}