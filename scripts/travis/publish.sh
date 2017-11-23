#!/bin/bash

cd $TRAVIS_BUILD_DIR

echo "=> Branch: "$TRAVIS_BRANCH" pull request: "$TRAVIS_PULL_REQUEST

if [[ $TRAVIS_BRANCH == 'master' && $TRAVIS_PULL_REQUEST == 'false' ]]
then
  echo "=> Sonar analysis - start"
  ./gradlew sonarqube -Dsonar.host.url=https://sonarqube.com -Dsonar.organization=cyganki -Dsonar.login=$SONAR_TOKEN
  echo "=> Sonar analysis - finish"
  echo "=> Create Docker images/latest - start"
  ./scripts/docker/docker-build.sh
  echo "=> Create Docker images/latest - finish"
  echo "=> Push Docker images/latest - start"
  ./scripts/docker/docker-publish.sh
  echo "=> Push Docker images - finish"
elif [[ $TRAVIS_BRANCH == 'release' && $TRAVIS_PULL_REQUEST == 'false' ]]
then
  echo "=> Create Docker images/demo - start"
  ./scripts/docker/docker-build-demo.sh
  echo "=> Create Docker images/demo - finish"
  echo "=> Push Docker images/demo - start"
  ./scripts/docker/docker-publish-demo.sh
  echo "=> Push Docker images/demo - finish"
else
  echo "=> No analysis and docker images build and push"
fi



