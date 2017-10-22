#!/usr/bin/env bash

fuser $E_ARB_CLIENT_PORT/tcp -k &
fuser $E_ARB_HYS_PORT/tcp -k &
fuser $E_ARB_KIB_PORT/tcp -k &
fuser $E_ARB_ES_PORT/tcp -k &
fuser $E_ARB_LOG_PORT/tcp -k &
fuser $E_ARB_API_PORT/tcp -k &
fuser $E_ARB_AUTH_PORT/tcp -k &
fuser $E_ARB_EXEC_PORT/tcp -k &
fuser $E_ARB_TOUR_PORT/tcp -k &
fuser $E_ARB_TRES_PORT/tcp -k &
fuser $E_ARB_EUR_PORT/tcp -k &
fuser $E_ARB_CFG_PORT/tcp -k
