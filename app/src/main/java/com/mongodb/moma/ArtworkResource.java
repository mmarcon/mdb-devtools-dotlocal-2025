package com.mongodb.moma;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/artworks")
public class ArtworkResource {

  @Inject
  ArtWorkService service;

  @GET
  public List<Artwork> list() {
    return service.list();
  }

  @POST
  public void add(Artwork artwork) {
    service.add(artwork);
  }
}
