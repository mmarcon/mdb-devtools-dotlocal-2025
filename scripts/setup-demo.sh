#!/usr/bin/env bash

if ! command -v brew &> /dev/null
then
    echo "Homebrew will be used to install the required dependencies. Please install Homebrew and try again."
    echo "Go to https://brew.sh/ to install Homebrew."
    exit 1
fi

# Check if Docker is installed
if ! command -v docker &> /dev/null
then
    echo "Docker could not be found. Installing it now..."
    brew install --cask docker
    exit 1
fi

# Check if MongoDB Atlas CLI is installed
if ! command -v atlas &> /dev/null
then
    echo "MongoDB Atlas CLI could not be found. Installing it now..."
    brew install mongodb-atlas
    exit 1
fi

# Check if ollama is installed
if ! command -v ollama &> /dev/null
then
    echo "ollama could not be found. Installing it now..."
    brew install ollama
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null
then
    echo "Java could not be found. Installing it now..."
    brew install java
    exit 1
fi

# Check if mongorestore is installed
if ! command -v mongorestore &> /dev/null
then
    echo "mongorestore could not be found. Installing it now..."
    brew tap mongodb/brew
    brew install mongodb-database-tools
    exit 1
fi

# Parse the arguments
# Arguments we are interested in:
# --demo-atlas-cluster <URI> - The URI of the Atlas cluster to use for the demo

while [[ "$#" -gt 0 ]]; do
    case $1 in
        --demo-atlas-cluster) DEMO_ATLAS_CLUSTER="$2"; shift ;;
        *) echo "Unknown parameter passed: $1"; exit 1 ;;
    esac
    shift
done

# Restore data to the Atlas cluster
if [ -n "$DEMO_ATLAS_CLUSTER" ]; then
    echo "Downloading the data archive..."
    curl -o /tmp/moma_embedded.archive -L https://github.com/mmarcon/mdb-devtools-dotlocal-2025/releases/download/no-tag/moma_embedded.archive 
    echo "Restoring data to the Atlas cluster..."
    echo $DEMO_ATLAS_CLUSTER
    mongorestore --uri "$DEMO_ATLAS_CLUSTER" --drop --archive=/tmp/moma_embedded.archive
    rm /tmp/moma_embedded.archive
    mongosh "$DEMO_ATLAS_CLUSTER" ../initdb/01-indexes.js
    mongosh "$DEMO_ATLAS_CLUSTER" ../initdb/02-enforce-schema.js
else
  echo "No Atlas cluster URI provided. Please provide a URI using the --demo-atlas-cluster parameter."
  echo "Example: ./setup-demo.sh --demo-atlas-cluster mongodb+srv://username:password@cluster.mongodb.net"
  exit 1
fi