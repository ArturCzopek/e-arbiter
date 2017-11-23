#!/bin/bash

cd $E_ARB_MAIN_PROJECT_FOLDER

docker build --file "e-arbiter-mongo/Dockerfile" -t earbiterinfo/e-arbiter-mongo:demo ./e-arbiter-mongo
docker build --file "e-arbiter-compile-box/Dockerfile" -t earbiterinfo/e-arbiter-compile-box:demo ./e-arbiter-compile-box
docker build --file "e-arbiter-config/Dockerfile" -t earbiterinfo/e-arbiter-config:demo ./e-arbiter-config
docker build --file "e-arbiter-eureka/Dockerfile" -t earbiterinfo/e-arbiter-eureka:demo ./e-arbiter-eureka
docker build --file "e-arbiter-api-gateway/Dockerfile" -t earbiterinfo/e-arbiter-api-gateway:demo ./e-arbiter-api-gateway
docker build --file "e-arbiter-auth/Dockerfile" -t earbiterinfo/e-arbiter-auth:demo ./e-arbiter-auth
docker build --file "e-arbiter-executor/Dockerfile" -t earbiterinfo/e-arbiter-executor:demo ./e-arbiter-executor
docker build --file "e-arbiter-tournament/Dockerfile" -t earbiterinfo/e-arbiter-tournament:demo ./e-arbiter-tournament
docker build --file "e-arbiter-tournament-results/Dockerfile" -t earbiterinfo/e-arbiter-tournament-results:demo ./e-arbiter-tournament-results
docker build --file "e-arbiter-web/Dockerfile" -t earbiterinfo/e-arbiter-web:demo ./e-arbiter-web

