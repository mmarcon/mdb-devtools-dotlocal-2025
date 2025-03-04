package com.mongodb.utils;

import io.quarkus.test.junit.QuarkusTestProfile;

public class StgProfile implements QuarkusTestProfile {
  @Override
  public String getConfigProfile() {
    return "stg";
  }
}