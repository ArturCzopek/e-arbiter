# Pull base image.
FROM ubuntu:16.04

# Install.
RUN \
    apt-get update && \
    apt-get install -y \
        gcc \
        g++ \
        python

ADD ["run.py", "/"]