#!/usr/bin/env bash

#### ATTENTION!!!
# THIS VERSION REQUIRES LOTS OF RAM, SO BE CAREFUL WITH STARTING IT
# sleeps are longer, otherwise your machine could explode :)
####

function pingService {
    pingResponse=""

    while [ "$pingResponse" != "ok" ]; do
        sleep 1s
        echo "[$2:$1]: Waiting for service..."
        pingResponse=`curl -s $E_ARB_HOST:$1/ping`
    done
}

# We cannot run other modules without config server

./gradlew bootRun -p e-arbiter-config &
pingService $E_ARB_CFG_PORT "Config Server"
echo "Config server is working! Port: $E_ARB_CFG_PORT"

# Next, we should start eureka. However, there is a problem with
# adding controllers to eureka (they are not visible...).
# So we run it at first, and after 10 seconds we'll run other
# microservices
# Next microservices has small delay to. Your PC won't burn :)
./gradlew bootRun -p e-arbiter-eureka &
sleep 75s

./gradlew bootRun -p e-arbiter-auth &
sleep 75s

./gradlew bootRun -p e-arbiter-executor &
sleep 75s

./gradlew bootRun -p e-arbiter-tournament &
sleep 75s

./gradlew bootRun -p e-arbiter-tournament-results &
sleep 75s

./gradlew bootRun -p e-arbiter-api-gateway &
sleep 75s

# Admin stuff - extra stuff in comparision to lite version

# Hystrix
./gradlew bootRun -p e-arbiter-monitoring &
sleep 75s

# ELK stack - these envs are usually whole commands so it's ok
$E_ARB_ES_RUN &
sleep 75s

$E_ARB_KIB_RUN &
sleep 75s

$E_ARB_LOG_RUN -f $E_ARB_LOG_CFG_PATH &
sleep 75s

# Web client

cd e-arbiter-web &&
ng serve