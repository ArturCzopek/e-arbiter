#!/bin/sh

docker build -t e-arbiter/compilebox .
chmod +777 //var/run/docker.sock