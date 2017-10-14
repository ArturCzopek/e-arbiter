#!/bin/bash

docker container run -p 192.168.0.1:${E_ARB_DEV_TOUR_MONGO_PORT}:27017 --name e-arbiter-mongo --network earbiter --rm earbiterinfo/e-arbiter-mongo:latest
docker container run -p ${E_ARB_CFG_PORT}:${E_ARB_CFG_PORT} --name e-arbiter-config --env E_ARB_HOST=$E_ARB_DOCKER_HOST  --env E_ARB_USR_PASS=$E_ARB_USR_PASS --network e-arbiter --rm earbiterinfo/e-arbiter-config:latest
docker container run -p ${E_ARB_EUR_PORT}:${E_ARB_EUR_PORT} --name e-arbiter-eureka --env E_ARB_HOST=$E_ARB_DOCKER_HOST  --network e-arbiter --rm earbiterinfo/e-arbiter-eureka:latest
docker container run -p ${E_ARB_API_PORT}:${E_ARB_API_PORT} --name e-arbiter-api-gateway --env E_ARB_HOST=$E_ARB_DOCKER_HOST  --network e-arbiter --rm earbiterinfo/e-arbiter-api-gateway:latest
docker container run -p ${E_ARB_AUTH_PORT}:${E_ARB_AUTH_PORT} --name e-arbiter-auth --env E_ARB_HOST=$E_ARB_DOCKER_HOST  --env E_ARB_DEV_GH_CLIENT_ID=$E_ARB_DOCKER_DEV_GH_CLIENT_ID --env E_ARB_DEV_GH_CLIENT_SECRET=$E_ARB_DOCKER_DEV_GH_CLIENT_SECRET --network e-arbiter --rm earbiterinfo/e-arbiter-auth:latest
docker container run -p ${E_ARB_EXEC_PORT}:${E_ARB_EXEC_PORT} -v /var/run/docker.sock:/var/run/docker.sock --name e-arbiter-executor --env E_ARB_HOST=$E_ARB_DOCKER_HOST  --network e-arbiter --rm earbiterinfo/e-arbiter-executor:latest
docker container run -p ${E_ARB_TOUR_PORT}:${E_ARB_TOUR_PORT} --name e-arbiter-tournament --env E_ARB_HOST=$E_ARB_DOCKER_HOST --env E_ARB_TOUR_MAIL_PASSWORD=$E_ARB_USR_PASS --network e-arbiter --rm earbiterinfo/e-arbiter-tournament:latest
docker container run -p ${E_ARB_TRES_PORT}:${E_ARB_TRES_PORT} --name e-arbiter-tournament-results --env E_ARB_HOST=$E_ARB_DOCKER_HOST --network e-arbiter --rm earbiterinfo/e-arbiter-tournament-results:latest
docker container run -p ${E_ARB_CLIENT_PORT}:80 --name e-arbiter-web --network e-arbiter --rm earbiterinfo/e-arbiter-web:latest
