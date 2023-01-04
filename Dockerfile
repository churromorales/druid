FROM alpine:3.14
WORKDIR /
COPY distribution/target/*-bin.tar.gz apache-druid-bin.tar.gz
