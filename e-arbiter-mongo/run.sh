#!/bin/bash
set -m

mongod --storageEngine wiredTiger --httpinterface --rest --master --auth &

if [ ! -f /data/db/.mongodb_password_set ]; then
    /set_mongodb_password.sh
fi

fg