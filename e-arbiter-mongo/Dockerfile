FROM mongo:3.4

LABEL mantainer="e-Arbiter dev team <earbiterinfo@gmail.com>"

ENV MONGODB_USER="cyganki"
ENV MONGODB_PASS="inzynier2017"
ENV MONGODB_DATABASE="e-arbiter"

ADD run.sh /run.sh
ADD set_mongodb_password.sh /set_mongodb_password.sh

CMD ["/run.sh"]