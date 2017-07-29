#!/usr/bin/env bash

# ELK stack run
$E_ARB_ES_RUN & sleep 10 & $E_ARB_KIB_RUN & sleep 10 & $E_ARB_LOG_RUN -f $E_ARB_LOG_CFG_PATH