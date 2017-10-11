#!/bin/bash

cd $TRAVIS_BUILD_DIR

echo "Branch: "$TRAVIS_BRANCH" pull request: "$TRAVIS_PULL_REQUEST

if [[ $TRAVIS_BRANCH == 'master' && $TRAVIS_PULL_REQUEST == 'false' ]]
then
  echo "Start sonar analysis"
  ./gradlew sonarqube -Dsonar.host.url=https://sonarqube.com -Dsonar.organization=cyganki -Dsonar.login=$SONAR_TOKEN
  echo "Start deploying docker dev images"
  docker login -u earbiterinfo -p $E_ARB_USR_PASS
  docker push earbiterinfo/e-arbiter-config-dev
  docker push earbiterinfo/e-arbiter-eureka-dev
  docker push earbiterinfo/e-arbiter-auth-dev
  docker push earbiterinfo/e-arbiter-api-gateway-dev
  docker push earbiterinfo/e-arbiter-tour-res-dev
  docker push earbiterinfo/e-arbiter-tournament-dev
  docker push earbiterinfo/e-arbiter-web-dev
else
  echo "No analysis and no docker images update"
fi
