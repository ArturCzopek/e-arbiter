FROM alpine:3.6

RUN \
    apk update && \
    apk add gcc && \
    apk add g++ && \
    apk add python

ADD run.py .

RUN chmod 777 run.py
