#!/bin/bash

cd $TRAVIS_BUILD_DIR

echo "Build script for e-Arbiter"
echo "Branch: "$TRAVIS_BRANCH

./gradlew build
cd e-arbiter-web
ng build --env=dev --base-href "https://arturczopek.github.io/e-arbiter/" -aot
# TODO uncomment, you know when..:)
# - ng test --watch=false

