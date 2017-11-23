  #!/bin/bash

  docker login -u earbiterinfo -p $E_ARB_USR_PASS
  docker push earbiterinfo/e-arbiter-mongo:demo
  docker push earbiterinfo/e-arbiter-compile-box:demo
  docker push earbiterinfo/e-arbiter-config:demo
  docker push earbiterinfo/e-arbiter-eureka:demo
  docker push earbiterinfo/e-arbiter-api-gateway:demo
  docker push earbiterinfo/e-arbiter-auth:demo
  docker push earbiterinfo/e-arbiter-executor:demo
  docker push earbiterinfo/e-arbiter-tournament:demo
  docker push earbiterinfo/e-arbiter-tournament-results:demo
  docker push earbiterinfo/e-arbiter-web:demo

