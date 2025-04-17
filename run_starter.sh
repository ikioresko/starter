#!/usr/bin/env bash

docker compose rm -s -f starter || true
docker compose build --no-cache starter
docker compose up -d