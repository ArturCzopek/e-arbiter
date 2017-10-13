#!/bin/bash

cd $TRAVIS_BUILD_DIR

echo "Branch: "$TRAVIS_BRANCH" pull request: "$TRAVIS_PULL_REQUEST

if [[ $TRAVIS_BRANCH == 'master' && $TRAVIS_PULL_REQUEST == 'false' ]]
then
  echo "Start sonar analysis"
  ./gradlew sonarqube -Dsonar.host.url=https://sonarqube.com -Dsonar.organization=cyganki -Dsonar.login=$SONAR_TOKEN
  echo "Start pushing docker dev images to docker hub"
  docker login -u earbiterinfo -p $E_ARB_USR_PASS
  docker push earbiterinfo/e-arbiter-mongo
  docker push earbiterinfo/e-arbiter-config
  docker push earbiterinfo/e-arbiter-eureka
  docker push earbiterinfo/e-arbiter-api-gateway
  docker push earbiterinfo/e-arbiter-auth
  docker push earbiterinfo/e-arbiter-tournament
  docker push earbiterinfo/e-arbiter-tournament-results
  docker push earbiterinfo/e-arbiter-web
else
  echo "No analysis and no docker images update"
fi
