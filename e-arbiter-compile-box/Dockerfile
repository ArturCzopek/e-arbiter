FROM alpine:3.5

# Install.
RUN \
    apt-get update && \
    apt-get install -y \
        gcc \
        g++ \
        python

ADD ["run.py", "/"]