# Demo for MongoDB Developer Tools at .local events for 2025

This is a simple Java application provided to demonstrate how to use MongoDB developer tools across the SDLC.

The application (built on [Quarkus](https://quarkus.io/)) provides a simple web frontend and an `/artworks` endpoint to search through MOMA Artworks. The dataset comes from https://github.com/MuseumofModernArt/collection.

## Initialize the local development environment

Requirements:
* Atlas CLI
* Docker
* Ollama

Run Ollama, which is used for generating embeddings:

```
ollama serve
ollama pull mxbai-embed-large
```

Setup the local Atlas deployment:

```
./init.sh
```

## Start the application for development

```
$ cd app
$ ./mvnw quarkus:dev
```

This should start the development server, reachable at `http://localhost:8080`.

The web application is reachable at `http://localhost:8080/artworks.html`.

## Supported use cases

Currently implemented are:

* Full-text search with fuzzy matching
* Semantic search on the artworks' title

### Good queries for demo

* `experiments with colors` to show the difference between full-text search and semantic search