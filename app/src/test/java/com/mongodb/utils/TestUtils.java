package com.mongodb.utils;

import java.util.Random;

public class TestUtils {
  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final int LENGTH = 10;

  public static String generateRandomString() {
    Random random = new Random();
    StringBuilder sb = new StringBuilder(LENGTH);
    for (int i = 0; i < LENGTH; i++) {
      sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
    }
    return sb.toString();
  }
}
