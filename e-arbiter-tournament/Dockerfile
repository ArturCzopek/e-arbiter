FROM openjdk:8-jre-alpine
LABEL mantainer="e-Arbiter dev team <earbiterinfo@gmail.com>"

# Default values. You can override it in start or in docker-compose
ENV E_ARB_HOST="http://192.168.0.1"
ENV E_ARB_CLIENT_PORT=4200
ENV E_ARB_API_PORT=8080
ENV E_ARB_HYS_PORT=8989
ENV E_ARB_TOUR_PORT=8092
ENV E_ARB_CFG_PORT=8096
ENV E_ARB_DEV_LOG_PATH="../logs"
ENV E_ARB_DEV_TOUR_MONGO_HOST="192.168.0.1"
ENV E_ARB_DEV_TOUR_MONGO_PORT=27017
ENV E_ARB_DEV_TOUR_MONGO_USERNAME="cyganki"
ENV E_ARB_DEV_TOUR_MONGO_PASSWORD="inzynier2017"
ENV E_ARB_DEV_TOUR_MONGO_SALT="1, 2, 3, 4"
ENV E_ARB_TOUR_MAIL_HOST="smtp.gmail.com"
ENV E_ARB_TOUR_MAIL_PORT=587
ENV E_ARB_TOUR_MAIL_USERNAME="earbiterinfo@gmail.com"
ENV E_ARB_TOUR_MAIL_TIMEOUTS=5000
ENV EUREKA_URI="http://192.168.0.1:8761/eureka"
ENV SPRING_PROFILES_ACTIVE="dev"


ADD  ./build/libs/e-arbiter-tournament.jar /app/e-arbiter-tournament.jar
WORKDIR app

CMD ["java", "-Xmx200m", "-jar", "e-arbiter-tournament.jar"]

EXPOSE ${E_ARB_TOUR_PORT}