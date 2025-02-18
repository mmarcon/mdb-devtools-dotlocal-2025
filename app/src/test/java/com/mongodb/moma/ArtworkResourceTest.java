package com.mongodb.moma;

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
}
