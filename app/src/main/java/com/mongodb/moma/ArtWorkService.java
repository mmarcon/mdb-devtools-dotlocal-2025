package com.mongodb.moma;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.search.*;
import com.mongodb.client.model.Filters;
import com.mongodb.moma.embeddings.EmbeddingsService;

import static com.mongodb.client.model.search.SearchOptions.searchOptions;
import java.util.Arrays;

@ApplicationScoped
public class ArtWorkService {

  @Inject
  MongoClient mongoClient;

  @Inject
  EmbeddingsService embeddingsService;

  private static class SearchType {
    public static final String FULL_TEXT = "fulltext";
    public static final String SEMANTIC = "semantic";
  }

  private MongoCursor<Document> fullTextSearch(String query) {
    return getCollection().aggregate(Arrays.asList(
        Aggregates.search(
            SearchOperator.text(SearchPath.fieldPath("Title"), query).fuzzy(),
            searchOptions().index("artwork_index"))),
        Document.class).iterator();
  }
  
  private MongoCursor<Document> semanticSearch(String query) {
    List<Double> embeddings = embeddingsService.generateEmbedding(query);
    String indexName = "semantic_search_title";
    FieldSearchPath fieldSearchPath = SearchPath.fieldPath("EmbeddedTitle");
    VectorSearchOptions options = VectorSearchOptions.approximateVectorSearchOptions(200);
    List<Bson> pipeline = Arrays.asList(
      Aggregates.vectorSearch(
        fieldSearchPath,
        embeddings,
        indexName,
        200,
        options));
    return getCollection().aggregate(pipeline, Document.class).iterator();
  }

  private MongoCursor<Document> getArtworkByDepartmentAndArtist(String department, String artist) {
    return getCollection().find(Filters.and(
      Filters.eq("Department", department),
      Filters.eq("Artist", artist)
    )).iterator();
  }

  public List<Artwork> list(String query, String type, String department, String artist) {
    System.out.println("Query: " + query + "- Search Type: " + type);
    List<Artwork> list = new ArrayList<>();
    MongoCursor<Document> cursor;

    if (query != null && !query.isEmpty()) {
      if (type.equals(SearchType.FULL_TEXT)) {
        cursor = fullTextSearch(query);
      } else if (type.equals(SearchType.SEMANTIC)) {
           cursor = semanticSearch(query);
      } else {
        cursor = getCollection().find().limit(100).iterator();
      }
    }
    
    else if (department != null && artist != null) {
      cursor = getArtworkByDepartmentAndArtist(department, artist);
    }
    
    else {
      System.out.println("Query is empty");
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
    return mongoClient.getDatabase("artworks").getCollection("moma_embedded");
  }
}
