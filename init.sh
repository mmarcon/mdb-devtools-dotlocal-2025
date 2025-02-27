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

# Check if ollama is installed
if ! command -v ollama &> /dev/null
then
    echo "ollama could not be found. Please install ollama and try again."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null
then
    echo "Java could not be found. Please install Java and try again."
    exit 1
fi

# Create the local deployment
atlas deployments setup devtools-dotlocal \
  --type local \
  --mdbVersion 8.0 \
  --port 27317 \
  --initdb ./initdb \
  --force