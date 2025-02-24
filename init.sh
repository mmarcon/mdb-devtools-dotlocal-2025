#!/usr/bin/env bash

# Check if Docker is installed
if ! command -v docker &> /dev/null
then
    echo "Docker could not be found. Please install Docker Desktop and try again."
    exit 1
fi

# Check if MongoDB Atlas CLI is installed
if ! command -v atlas &> /dev/null
then
    echo "MongoDB Atlas CLI could not be found. Please install MongoDB Atlas CLI and try again."
    exit 1
fi

# Create the local deployment
atlas deployments setup devtools-dotlocal-2 \
  --type local \
  --mdbVersion 8.0 \
  --port 27417 \
  --initdb ./initdb \
  --force