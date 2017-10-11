#!/bin/bash

cd $E_ARB_ROOT_FOLDER
cd e-arbiter-web
ng build --env docker-dev --base-href http://192.168.0.1:4200
cd $E_ARB_ROOT_FOLDER

docker build --file "e-arbiter-config/Dockerfile-dev" -t earbiterinfo/e-arbiter-config-dev:latest ./e-arbiter-config
docker build --file "e-arbiter-eureka/Dockerfile-dev" -t earbiterinfo/e-arbiter-eureka-dev:latest ./e-arbiter-eureka
docker build --file "e-arbiter-auth/Dockerfile-dev" -t earbiterinfo/e-arbiter-auth-dev:latest ./e-arbiter-auth
docker build --file "e-arbiter-api-gateway/Dockerfile-dev" -t earbiterinfo/e-arbiter-api-gateway-dev:latest ./e-arbiter-api-gateway
docker build --file "e-arbiter-tournament/Dockerfile-dev" -t earbiterinfo/e-arbiter-tournament-dev:latest ./e-arbiter-tournament
docker build --file "e-arbiter-tournament-results/Dockerfile-dev" -t earbiterinfo/e-arbiter-tour-res-dev:latest ./e-arbiter-tournament-results
docker build --file "e-arbiter-web/Dockerfile-dev" -t earbiterinfo/e-arbiter-web-dev:latest ./e-arbiter-web


#docker build -t e-arbiter/compilebox:latest ./e-arbiter-compile-box        # one type of compilebox, probably won't change
# TODO: add compilebox to executor image
#chmod +777 //var/run/docker.sock

