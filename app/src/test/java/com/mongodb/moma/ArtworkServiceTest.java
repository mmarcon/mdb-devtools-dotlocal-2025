package com.mongodb.moma;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

import java.time.Duration;

import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import jakarta.inject.Inject;

import com.mongodb.client.MongoCursor;
import com.mongodb.utils.StgProfile;
import com.mongodb.utils.TestUtils;

@QuarkusTest
@TestProfile(StgProfile.class)
@Tag("stg")
public class ArtworkServiceTest {
  @Inject
  ArtWorkService artworkService;

  @Test
  @Disabled
  public void getArtworkByDepartmentAndArtistTest() {
    Artwork artwork = new Artwork();

    String title = TestUtils.generateRandomString();
    String artist = TestUtils.generateRandomString();
    String department = TestUtils.generateRandomString();



    artwork.setTitle(title);
    artwork.setArtist(new String[] { artist });
    artwork.setMedium("Test Medium");
    artwork.setDate("Test Date");
    artwork.setClassification("Test Classification");
    artwork.setDimensions("Test Dimensions");
    artwork.setDepartment(department);
    artwork.setURL("https://www.moma.com/animal-art/dogs/golden-retriever");
    artwork.setImageURL("https://www.moma.com/dog-pictures/golden-retriever.jpg");
    artwork.setObjectID(1);

    artworkService.add(artwork);

    Document document = assertTimeout(Duration.ofMillis(200), () -> {
      MongoCursor<Document> cursor = artworkService.getArtworkByDepartmentAndArtist(department, artist);
      Document doc = cursor.next();
      cursor.close();
      return doc;
    });
    Assertions.assertThat(document.getString("Title")).isEqualTo(title);
  }

}
