#!/bin/bash

cd $TRAVIS_BUILD_DIR

echo "=> Branch: "$TRAVIS_BRANCH" pull request: "$TRAVIS_PULL_REQUEST

if [[ $TRAVIS_BRANCH == 'master' && $TRAVIS_PULL_REQUEST == 'false' ]]
then
  echo "=> Sonar analysis - start"
  ./gradlew sonarqube -Dsonar.host.url=https://sonarqube.com -Dsonar.organization=cyganki -Dsonar.login=$SONAR_TOKEN
  echo "=> Sonar analysis - finish"
  echo "=> Create Docker images - start"
  ./scripts/docker/docker-build.sh
  echo "=> Create Docker images - finish"
  echo "=> Push Docker images - start"
  ./scripts/docker/docker-publish.sh
  echo "=> Push Docker images - finish"
else
  echo "=> No analysis and docker images build and push"
fi
