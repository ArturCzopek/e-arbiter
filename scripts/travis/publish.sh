#!/bin/bash

cd $TRAVIS_BUILD_DIR

echo "Branch: "$TRAVIS_BRANCH" pull request: "$TRAVIS_PULL_REQUEST

if [[ $TRAVIS_BRANCH == 'master' && $TRAVIS_PULL_REQUEST == 'false' ]]
then
  echo "Start sonar analysis"
  ./gradlew sonarqube -Dsonar.host.url=https://sonarqube.com -Dsonar.organization=cyganki -Dsonar.login=$SONAR_TOKEN
  echo "Start pushing docker dev images to docker hub"
  ../docker/docker-publish.sh
else
  echo "No analysis and no docker images update"
fi
