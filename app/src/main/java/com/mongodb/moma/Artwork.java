package com.mongodb.moma;

import java.util.Objects;

public class Artwork {
  private String Title;
  private String[] Artist;
  private String id;
  private String Medium;
  private String Date;
  private String Classification;
  private String Dimensions;
  private String Credit;
  private String Department;
  private String ObjectNumber;
  private String URL;
  private String ImageURL;
  private int ObjectID;

  public Artwork() {
  }

  public String getTitle() {
    return Title;
  }

  public void setTitle(String title) {
    Title = title;
  }

  public String[] getArtist() {
    return Artist;
  }

  public void setArtist(String[] artist) {
    Artist = artist;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMedium() {
    return Medium;
  }

  public void setMedium(String medium) {
    Medium = medium;
  }

  public String getDate() {
    return Date;
  }

  public void setDate(String date) {
    Date = date;
  }

  public String getClassification() {
    return Classification;
  }

  public void setClassification(String classification) {
    Classification = classification;
  }

  public String getDimensions() {
    return Dimensions;
  }

  public void setDimensions(String dimensions) {
    Dimensions = dimensions;
  }

  public String getCredit() {
    return Credit;
  }

  public void setCredit(String credit) {
    Credit = credit;
  }

  public String getDepartment() {
    return Department;
  }

  public void setDepartment(String department) {
    Department = department;
  }

  public String getObjectNumber() {
    return ObjectNumber;
  }

  public void setObjectNumber(String objectNumber) {
    ObjectNumber = objectNumber;
  }

  public String getURL() {
    return URL;
  }

  public void setURL(String URL) {
    this.URL = URL;
  }

  public String getImageURL() {
    return ImageURL;
  }

  public void setImageURL(String imageURL) {
    ImageURL = imageURL;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Artwork)) {
      return false;
    }

    Artwork other = (Artwork) obj;

    return Objects.equals(other.ObjectID, this.ObjectID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.ObjectID);
  }

  public void setObjectID(Integer integer) {
    ObjectID = integer;
  }

  public Object getObjectID() {
    return ObjectID;
  }
}
