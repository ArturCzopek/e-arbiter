 #!/bin/bash

echo "Branch: " + $TRAVIS_BRANCH
if [[ $TRAVIS_BRANCH == 'master' ]]
then
  echo "Start sonar analysis"
  cd ..
  ./gradlew sonarqube -Dsonar.host.url=https://sonarqube.com -Dsonar.organization=cyganki -Dsonar.login=$SONAR_TOKEN
  # NOTHING TO DEPLOY FOR NOW FOR FRONT
#  cd e-arbiter-web
#  angular-cli-ghpages --no-silent --repo=https://GH_TOKEN@github.com/arturczopek/e-arbiter.git
else
  echo "No analysis"
fi