#!/bin/bash

cd $E_ARB_ROOT_FOLDER

# THIS IS NOT SCRIPT FOR RUN!! You can take instructions from it but don't run it in this way. To do it, use docker-compose.dev.yml file

# TODO: move comments to wiki guide
# TODO: Create images with databases on the proper volumes

# Network is needed! Run: docker network create -d bridge --subnet 192.168.0.0/24 --gateway 192.168.0.1 earbiter-dev  JUST ONCE!

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

docker container run -p ${E_ARB_CFG_PORT}:${E_ARB_CFG_PORT} --name e-arbiter-config-dev --env E_ARB_USR_PASS=$E_ARB_USR_PASS --network earbiter-dev --rm earbiterinfo/e-arbiter-config-dev:latest
docker container run -p ${E_ARB_EUR_PORT}:${E_ARB_EUR_PORT} --name e-arbiter-eureka-dev --network earbiter-dev --rm earbiterinfo/e-arbiter-eureka-dev:latest
docker container run -p ${E_ARB_API_PORT}:${E_ARB_API_PORT} --name e-arbiter-api-gateway-dev --network earbiter-dev --rm earbiterinfo/e-arbiter-api-gateway-dev:latest
docker container run -p 192.168.0.1:${E_ARB_DEV_TOUR_MONGO_PORT}:27017 --name e-arbiter-mongo-dev --network earbiter-dev --rm earbiterinfo/e-arbiter-mongo-dev:latest

docker container run -p ${E_ARB_AUTH_PORT}:${E_ARB_AUTH_PORT} --name e-arbiter-auth-dev --env E_ARB_DEV_GH_CLIENT_ID=$E_ARB_DOCK_DEV_GH_CLIENT_ID --env E_ARB_DEV_GH_CLIENT_SECRET=$E_ARB_DOCK_DEV_GH_CLIENT_SECRET --network earbiter-dev --rm earbiterinfo/e-arbiter-auth-dev:latest
docker container run -p ${E_ARB_TRES_PORT}:${E_ARB_TRES_PORT} --name e-arbiter-tour-res-dev --network earbiter-dev --rm earbiterinfo/e-arbiter-tour-res-dev:latest
docker container run -p ${E_ARB_TOUR_PORT}:${E_ARB_TOUR_PORT} --name e-arbiter-tournament-dev --env E_ARB_TOUR_MAIL_PASSWORD=$E_ARB_USR_PASS --network earbiter-dev --rm earbiterinfo/e-arbiter-tournament-dev:latest
docker container run -p ${E_ARB_CLIENT_PORT}:80 --name e-arbiter-web-dev --network earbiter-dev --rm earbiterinfo/e-arbiter-web-dev:latest
