use('artworks');

// Create index for full-text search
db.moma_embedded.createSearchIndex(
  "artwork_index",
  {
    "mappings": {
      "dynamic": true,
      "fields": {}
    }
  }
);

// Create index for semantic search
db.moma_embedded.createSearchIndex(
  "semantic_search_title",
  "vectorSearch",
  {
    "fields": [
      {
        "type": "vector",
        "path": "EmbeddedTitle",
        "numDimensions": 1024,
        "similarity": "dotProduct"
      }
    ]
  }
);