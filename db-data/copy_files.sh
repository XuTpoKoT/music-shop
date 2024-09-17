#!/bin/bash

docker cp dump2/. ms_postgres:dump
docker exec -it ms_postgres bash
exit

