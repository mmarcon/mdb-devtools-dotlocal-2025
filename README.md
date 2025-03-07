# Demo for MongoDB Developer Tools at .local events for 2025

This is a simple Java application provided to demonstrate how to use MongoDB developer tools across the SDLC.

The application (built on [Quarkus](https://quarkus.io/)) provides a simple web frontend and an `/artworks` endpoint to search through MOMA Artworks. The dataset comes from https://github.com/MuseumofModernArt/collection.

## Setting up your laptop for the demo

*Note: this guide assumes you'll be running this demo on macOS. If you use a different operating system you can try to reproduce the same setup there.*

Clone this repository on your laptop and then do the following:

```
$ cd scripts
$ ./setup-demo.sh --demo-atlas-cluster "mongodb+srv://<user>:<pass>@your-cluster.12abc.mongodb.net"
```

The `--demo-atlas-cluster` points to the connection string for the Atlas cluster that you'll be able to demo Data Explorer.
An archive for the `moma_embedded` dataset will be downloaded from GitHub and restored into that cluster. This will take a few minutes.

If it all goes well, all the tools you need will be installed (using `brew`) and your system will be good to go.

## Initialize the local development environment

Requirements - they should all have :
* Atlas CLI
* Docker
* Ollama
* Java

Run Ollama, which is used for generating embeddings:

```
ollama serve
ollama pull mxbai-embed-large
```

Setup the local Atlas deployment:

```
./init.sh
```

At this point, everything should be ready to go. You can go on stage and have fun presenting your session!

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