  #!/bin/bash

  docker login -u earbiterinfo -p $E_ARB_USR_PASS
  docker push earbiterinfo/e-arbiter-mongo
  docker push earbiterinfo/e-arbiter-compile-box
  docker push earbiterinfo/e-arbiter-config
  docker push earbiterinfo/e-arbiter-eureka
  docker push earbiterinfo/e-arbiter-api-gateway
  docker push earbiterinfo/e-arbiter-auth
  docker push earbiterinfo/e-arbiter-executor
  docker push earbiterinfo/e-arbiter-tournament
  docker push earbiterinfo/e-arbiter-tournament-results
  docker push earbiterinfo/e-arbiter-web
