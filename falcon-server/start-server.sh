#!/bin/bash
./stop-server.sh
./build-docker-image.sh
./mvnw clean package -Dmaven.test.skip=true
docker container rm -f falcon-server
docker container run -it -e SPRING_ACTIVE_PROVILES=development \
  --name falcon-server \
  -p 9091:80 \
  -m 128m \
  -c 512 \
  --link \
  falcon-db:mariadb \
  falcon-server:0.0.1
