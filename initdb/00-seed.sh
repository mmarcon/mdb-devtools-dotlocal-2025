#!/bin/bash

FULL_PATH_TO_SCRIPT="$(realpath "${BASH_SOURCE[0]}")"
CURRENT_DIR="$(dirname "$FULL_PATH_TO_SCRIPT")"

CONNECTION_STRING=mongodb://localhost:27017/?directConnection=true

mongoimport \
  --uri $CONNECTION_STRING \
  --collection=moma \
  --db=artworks \
  --jsonArray \
  --file="${CURRENT_DIR}/data/Artworks.json" > /tmp/import-log.txt 2>&1