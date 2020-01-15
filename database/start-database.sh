#!/bin/bash
docker container rm -f falcon-db
docker container run -d --name falcon-db -p 3306:3306 -m 64m -c 512 falcon-db:0.0.1
docker container run -d --name falcon-cache -p 6379:6379 -m 64m -c 512 redis
