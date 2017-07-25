#!/bin/bash

cd $TRAVIS_BUILD_DIR

echo "Build script for e-Arbiter"
echo "Branch: "$TRAVIS_BRANCH", pull request: "$TRAVIS_PULL_REQUEST

# For builds, we need to run cfg server at first, for build tests, we need to start it as a test profile
# Later running app will be use different envs etc because there are needed at runtime!
./gradlew -p e-arbiter-config bootRun -Dspring.profiles.active=test &
sleep 75s

gradlew_output=$(./gradlew build); gradlew_return_code=$?

echo "$gradlew_output";

if (( gradlew_return_code != 0 ))
then
    echo "!! Gradle build - FAIL !!";
    exit 1;
else
    echo "Gradle build - SUCCESS";
fi

cd e-arbiter-web;
ng_output=$(ng build --env=dev --base-href "https://arturczopek.github.io/e-arbiter/" -aot); ng_return_code=$?

# TODO uncomment, you know when..:)
# - ng test --watch=false

echo "$ng_output";

if (( ng_return_code != 0 ))
then
    echo "Ng build - !! FAIL !!";
    exit 1;
else
    echo "Ng build - SUCCESS";
    exit 0;
fi

