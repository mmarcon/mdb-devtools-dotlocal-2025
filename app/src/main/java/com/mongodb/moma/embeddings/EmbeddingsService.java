package com.mongodb.moma.embeddings;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.data.embedding.Embedding;

@ApplicationScoped
public class EmbeddingsService {

    @Inject
    @ConfigProperty(name = "embeddings.api.url")
    String apiUrl;

    public void generateEmbedding(String text) {
        EmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl(apiUrl)
                .modelName("mxbai-embed-large")
                .build();
        Response<Embedding> response = embeddingModel.embed(text);
        Embedding embedding = response.content();
        int dimension = embedding.dimension(); // 768
        float[] vector = embedding.vector();
        System.out.println(dimension);
        System.out.println(embedding.vectorAsList());
    }
}