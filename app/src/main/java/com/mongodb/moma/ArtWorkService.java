package com.mongodb.moma;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.search.*;
import com.mongodb.moma.embeddings.EmbeddingsService;

import static com.mongodb.client.model.search.SearchOptions.searchOptions;
import java.util.Arrays;

@ApplicationScoped
public class ArtWorkService {

  @Inject
  MongoClient mongoClient;

  @Inject
  EmbeddingsService embeddingsService;

  public List<Artwork> list(String query) {
    System.out.println("Query: " + query);
    List<Artwork> list = new ArrayList<>();
    MongoCursor<Document> cursor;

    if (query != null && !query.isEmpty()) {
      embeddingsService.generateEmbedding(query);
      cursor = getCollection().aggregate(Arrays.asList(
          Aggregates.search(
              SearchOperator.text(SearchPath.fieldPath("Title"), query).fuzzy(),
              searchOptions().index("artwork_index")
          )
      ), Document.class).iterator();
    } else {
      cursor = getCollection().find().limit(100).iterator();
    }

    try {
      while (cursor.hasNext()) {
        Document document = cursor.next();
        Artwork artwork = new Artwork();
        artwork.setTitle(document.getString("Title"));
        artwork.setArtist(document.getList("Artist", String.class).toArray(new String[0]));
        artwork.setId(document.getString("id"));
        artwork.setMedium(document.getString("Medium"));
        artwork.setDate(document.getString("Date"));
        artwork.setClassification(document.getString("Classification"));
        artwork.setDimensions(document.getString("Dimensions"));
        artwork.setCredit(document.getString("Credit"));
        artwork.setDepartment(document.getString("Department"));
        artwork.setObjectNumber(document.getString("ObjectNumber"));
        artwork.setURL(document.getString("URL"));
        artwork.setImageURL(document.getString("ImageURL"));
        artwork.setObjectID(document.getInteger("ObjectID"));
        list.add(artwork);
      }
    } finally {
      cursor.close();
    }
    return list;
  }

  private MongoCollection<Document> getCollection() {
    return mongoClient.getDatabase("artworks").getCollection("moma");
  }
}
