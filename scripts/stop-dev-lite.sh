#!/usr/bin/env bash

fuser $E_ARB_CLIENT_PORT/tcp -k &
fuser $E_ARB_API_PORT/tcp -k &
fuser $E_ARB_AUTH_PORT/tcp -k &
fuser $E_ARB_EXEC_PORT/tcp -k &
fuser $E_ARB_TOUR_PORT/tcp -k &
fuser $E_ARB_TRES_PORT/tcp -k &
fuser $E_ARB_EUR_PORT/tcp -k &
fuser $E_ARB_PORT/tcp -k