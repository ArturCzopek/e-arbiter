#!/usr/bin/env bash

fuser $E_ARB_ES_PORT/tcp -k &
fuser $E_ARB_KIB_PORT/tcp -k &
fuser $E_ARB_LOG_PORT/tcp -k