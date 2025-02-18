package com.mongodb.moma;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/artworks")
public class ArtworkResource {

  @Inject
  ArtWorkService service;

  @GET
  public List<Artwork> list(@QueryParam("q") String query, @QueryParam("type") String type) {
    return service.list(query, type);
  }
}
