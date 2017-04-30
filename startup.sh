#!/usr/bin/env bash
./gradlew build
./gradlew bootRun -p e-arbiter-eureka &
./gradlew bootRun -p e-arbiter-auth &
./gradlew bootRun -p e-arbiter-executor &
./gradlew bootRun -p e-arbiter-tournament &
./gradlew bootRun -p e-arbiter-tournament-results &
./gradlew bootRun -p e-arbiter-solution-repository &
./gradlew bootRun -p e-arbiter-api-gateway &
cd e-arbiter-web &&
ng serve
