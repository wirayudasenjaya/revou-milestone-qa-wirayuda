package com.example;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

public class AuthTests {
  private String token;

  @Test(description = "Success Authentication")
  public void authSuccess() {
    Response response = given()
        .header("Content-Type", "application/json")
        .body("{ \"username\": \"admin\", \"password\": \"password123\" }")
        .when()
        .post("/auth");

    token = response.jsonPath().getString("token");

    response.then()
        .statusCode(200)
        .body("token", notNullValue());
  }

  @Test(description = "Failed Authentication (Wrong username/password)")
  public void authFailed() {
    Response response = given()
        .header("Content-Type", "application/json")
        .body("{ \"username\": \"admin\", \"password\": \"12345678\" }")
        .when()
        .post("/auth");

    response.then()
        .statusCode(not(200))
        .body("token", notNullValue());
  }

  @Test(description = "Failed Authentication (Empty username)")
  public void authEmptyUsername() {
    Response response = given()
        .header("Content-Type", "application/json")
        .body("{ \"username\": \"\", \"password\": \"password123\" }")
        .when()
        .post("/auth");

    token = response.jsonPath().getString("token");

    response.then()
        .statusCode(200)
        .body("token", notNullValue());
  }

  @Test(description = "Failed Authentication (Empty password)")
  public void authEmptyPassword() {
    Response response = given()
        .header("Content-Type", "application/json")
        .body("{ \"username\": \"admin\", \"password\": \"\" }")
        .when()
        .post("/auth");

    token = response.jsonPath().getString("token");

    response.then()
        .statusCode(200)
        .body("token", notNullValue());
  }
}
