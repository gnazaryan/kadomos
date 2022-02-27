#!/bin/sh
cd $(dirname $0)

cd ./api-gw
./mvnw clean package

cd ../service-a
./mvnw clean package

cd ../service-b
./mvnw clean package
