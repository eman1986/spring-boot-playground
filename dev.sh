#!/usr/bin/env bash

set -e

./gradlew clean build -i --stacktrace

docker compose up -d --build
