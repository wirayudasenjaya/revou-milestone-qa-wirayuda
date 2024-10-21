package com.example;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PingTests {
  @Test(description = "Health Check")
  public void healthCheck() {
    given()
        .when()
        .get("/ping")
        .then()
        .statusCode(201);
  }
}
