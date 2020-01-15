#!/bin/bash
./mvnw clean package -Dmaven.test.skip=true
docker image build --tag falcon-server:0.0.1 .

