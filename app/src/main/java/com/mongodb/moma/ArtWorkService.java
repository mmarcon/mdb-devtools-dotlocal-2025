package com.mongodb.moma;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

@ApplicationScoped
public class ArtWorkService {

  @Inject
  MongoClient mongoClient;

  public List<Artwork> list() {
    List<Artwork> list = new ArrayList<>();
    MongoCursor<Document> cursor = getCollection().find().limit(100).iterator();

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

  public void add(Artwork artwork) {
    Document document = new Document()
      .append("Title", artwork.getTitle())
      .append("Artist", artwork.getArtist())
      .append("id", artwork.getId())
      .append("Medium", artwork.getMedium())
      .append("Date", artwork.getDate())
      .append("Classification", artwork.getClassification())
      .append("Dimensions", artwork.getDimensions())
      .append("Credit", artwork.getCredit())
      .append("Department", artwork.getDepartment())
      .append("ObjectNumber", artwork.getObjectNumber())
      .append("URL", artwork.getURL())
      .append("ImageURL", artwork.getImageURL())
      .append("ObjectID", artwork.getObjectID());
    getCollection().insertOne(document);
  }

  private MongoCollection getCollection() {
    return mongoClient.getDatabase("artworks").getCollection("moma");
  }
}
