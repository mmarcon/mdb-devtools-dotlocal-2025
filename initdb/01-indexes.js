use('artworks');
db.moma.createSearchIndex(
  "artwork_index",
  {
    "mappings": {
      "dynamic": true,
      "fields": {}
    }
  }
);