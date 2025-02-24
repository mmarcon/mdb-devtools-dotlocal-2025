package com.mongodb.moma.embeddings;

import io.quarkus.test.junit.QuarkusTest;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import jakarta.inject.Inject;

@QuarkusTest
public class EmbeddingServiceTest {
  
  @Inject
  EmbeddingsService embeddingsService;

  @Test
  public void testEmbeddingGeneration() {
      List<Double> embeddings = embeddingsService.generateEmbedding("some search query");
      Assertions.assertThat(embeddings.size()).isEqualTo(1024);
  }
}
