#!/bin/bash

cd $TRAVIS_BUILD_DIR

echo "Build script for e-Arbiter"
echo "Branch: "$TRAVIS_BRANCH", pull request: "$TRAVIS_PULL_REQUEST

# For builds, we need to run cfg server at first, for build tests, we need to start it as a test profile
# Later running app will be use different envs etc because there are needed at runtime!
./gradlew -p e-arbiter-config bootRun -Dspring.profiles.active=test &
sleep 75s

gradlew_output=$(./gradlew clean build -i); gradlew_return_code=$?

echo "$gradlew_output";

if (( gradlew_return_code != 0 ))
then
    echo "!! Gradle build - FAIL !!";
    exit 1;
else
    echo "Gradle build - SUCCESS";
fi

cd $TRAVIS_BUILD_DIR/e-arbiter-web;
ng_output=$(ng build --env docker-dev --base-href http://192.168.0.1:4200); ng_return_code=$?


echo "$ng_output";

if (( ng_return_code != 0 ))
then
    echo "Ng build - !! FAIL !!";
    exit 1;
else
    echo "Ng build - SUCCESS";
fi

cd $TRAVIS_BUILD_DIR;

docker_output=$(./scripts/docker/docker-build-dev.sh); docker_return_code=$?

echo "$docker_output";
if (( docker_return_code != 0 ))
then
    echo "Docker build - !! FAIL !!";
    exit 1;
else
    echo "Docker build - SUCCESS";
    exit 0;
fi

