#!/bin/bash

curl -o /tmp/moma_embedded.archive -L https://github.com/mmarcon/mdb-devtools-dotlocal-2025/releases/download/no-tag/moma_embedded_mini.archive

mongorestore \
  --port 27017 \
  --archive=/tmp/moma_embedded.archive > /tmp/restore-log.txt 2>&1