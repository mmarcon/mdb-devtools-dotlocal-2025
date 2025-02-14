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

# Check if jq is installed
if ! command -v jq &> /dev/null
then
    echo "jq could not be found. Please install jq and try again."
    exit 1
fi

# Create the local deployment
atlas deployments setup devtools-dotlocal \
  --type local \
  --mdbVersion 8.0 \
  --port 27317 \
  --initdb ./initdb \
  --force

# # Grab the connection string
# connection_string=$(atlas deployments connect devtools-dotlocal --connectWith connectionString)

# # Generate JSON array with one object
# json_array=$(jq -n --arg connStr $connection_string \
# '[
#   {
#     "name": {
#       "type": "string",
#       "description": "devtools-dotlocal"
#     },
#     "connectionString": {
#       "type": "string",
#       "description": $connStr
#     }
#   }
# ]')

# # Print the JSON array
# echo "$json_array"