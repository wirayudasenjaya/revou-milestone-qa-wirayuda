package com.example;

import com.example.config.Config;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public class ApiTests extends PingTests {
  protected static String token;

  @BeforeClass
  public void setup() {
    RestAssured.baseURI = Config.BASE_URI;
    if (token == null) {
      token = createToken();
    }
  }

  private String createToken() {
    Response response = given()
        .header("Content-Type", "application/json")
        .body(String.format("{ \"username\": \"%s\", \"password\": \"%s\" }", Config.USERNAME, Config.PASSWORD))
        .when()
        .post("/auth");

    return response.jsonPath().getString("token");
  }
}
