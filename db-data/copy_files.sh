#!/bin/bash

docker cp dump ms_postgres:dump
docker exec -it ms_postgres bash
exit

