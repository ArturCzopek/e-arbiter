#!/bin/bash

# THIS IS NOT SCRIPT FOR RUN!! You can take instructions from it but don't run it in this way. To do it, use docker-compose.dev.yml file

# TODO: move comments to wiki guide
# TODO: Create images with databases on the proper volumes

# Network is needed! Run: docker network create -d bridge --subnet 192.168.0.0/24 --gateway 192.168.0.1 e-arbiter  JUST ONCE!

# Run in background, to see logs type: docker container logs <container-name>
# To stop container, type: docker container stop <first 3 signs of container id>
# You can get container id by typing: docker container ls
# Go to container

# -d -> --detach - run in background
# -e -> --environment - pass envs
# -i -> --interactive - go to container
# -t -> --tty
# -p -> port-where-it-will-be-available:port-from-container
# --rm -> remove container when is turned off
# -v -> set volume location <name:path>
# -n -> --name - container name

# docker container exec -it - command in existing container
# separated network would be ok
# volumes are persistent, dbs have default volumes in docker, so no problem with that approach to db

# for getting logs from proper container type: docker logs --details [container-id]
# develop client app should has a possibility to mount volume in system, command -v /path-where:/path-container

docker container run -p 192.168.0.1:${E_ARB_DEV_TOUR_MONGO_PORT}:27017 --name e-arbiter-mongo --network earbiter --rm earbiterinfo/e-arbiter-mongo:latest
docker container run -p ${E_ARB_CFG_PORT}:${E_ARB_CFG_PORT} --name e-arbiter-config --env E_ARB_HOST=$E_ARB_DOCKER_HOST  --env E_ARB_USR_PASS=$E_ARB_USR_PASS --network e-arbiter --rm earbiterinfo/e-arbiter-config:latest
docker container run -p ${E_ARB_EUR_PORT}:${E_ARB_EUR_PORT} --name e-arbiter-eureka --env E_ARB_HOST=$E_ARB_DOCKER_HOST  --network e-arbiter --rm earbiterinfo/e-arbiter-eureka:latest
docker container run -p ${E_ARB_API_PORT}:${E_ARB_API_PORT} --name e-arbiter-api-gateway --env E_ARB_HOST=$E_ARB_DOCKER_HOST  --network e-arbiter --rm earbiterinfo/e-arbiter-api-gateway:latest
docker container run -p ${E_ARB_AUTH_PORT}:${E_ARB_AUTH_PORT} --name e-arbiter-auth --env E_ARB_HOST=$E_ARB_DOCKER_HOST  --env E_ARB_DEV_GH_CLIENT_ID=$E_ARB_DOCKER_DEV_GH_CLIENT_ID --env E_ARB_DEV_GH_CLIENT_SECRET=$E_ARB_DOCKER_DEV_GH_CLIENT_SECRET --network e-arbiter --rm earbiterinfo/e-arbiter-auth:latest
docker container run -p ${E_ARB_EXEC_PORT}:${E_ARB_EXEC_PORT} -v /var/run/docker.sock:/var/run/docker.sock --name e-arbiter-executor --env E_ARB_HOST=$E_ARB_DOCKER_HOST  --network e-arbiter --rm earbiterinfo/e-arbiter-executor:latest
docker container run -p ${E_ARB_TOUR_PORT}:${E_ARB_TOUR_PORT} --name e-arbiter-tournament --env E_ARB_HOST=$E_ARB_DOCKER_HOST --env E_ARB_TOUR_MAIL_PASSWORD=$E_ARB_USR_PASS --network e-arbiter --rm earbiterinfo/e-arbiter-tournament:latest
docker container run -p ${E_ARB_TRES_PORT}:${E_ARB_TRES_PORT} --name e-arbiter-tournament-results --env E_ARB_HOST=$E_ARB_DOCKER_HOST --network e-arbiter --rm earbiterinfo/e-arbiter-tournament-results:latest
docker container run -p ${E_ARB_CLIENT_PORT}:80 --name e-arbiter-web --network e-arbiter --rm earbiterinfo/e-arbiter-web:latest