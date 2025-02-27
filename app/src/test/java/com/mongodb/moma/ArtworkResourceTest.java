package com.mongodb.moma;

import java.util.List;

import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@QuarkusTest
public class ArtworkResourceTest {
  @ParameterizedTest
  @ValueSource(strings = { "/artworks" })
  public void testList(String path) {
    System.out.println("Path: " + path);
    Artwork[] artworks = RestAssured.get(path)
            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.SC_OK)
            .extract().body().as(Artwork[].class);

    Assertions.assertThat(artworks).isNotNull();
    Assertions.assertThat(artworks.length).isGreaterThan(0);
    Assertions.assertThat(artworks[0].getTitle()).isNotBlank();
  }

  @ParameterizedTest
  @ValueSource(strings = { "/artworks" })
  public void postArtwork(String path) {
    Artwork artwork = new Artwork();

    List<Double> embeddedTitle = List.of(0.1, 0.2, 0.3, 0.4, 0.5);

    artwork.setTitle("[TEST] Test Title");
    artwork.setArtist(new String[] { "Test Artist" });
    artwork.setMedium("Test Medium");
    artwork.setDate("Test Date");
    artwork.setClassification("Test Classification");
    artwork.setDimensions("Test Dimensions");
    artwork.setDepartment("Test Department");
    artwork.setURL("https://www.moma.com/animal-art/dogs/golden-retriever");
    artwork.setImageURL("https://www.moma.com/dog-pictures/golden-retriever.jpg");
    artwork.setObjectID(1);
    artwork.setEmbeddedTitle(embeddedTitle);

    Artwork response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(artwork)
            .post(path)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract().body().as(Artwork.class);

    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getId()).isNotBlank();
  }
}
