package com.example.stepdefs;

import com.example.config.Config;
import com.example.context.GlobalContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class GlobalStepDefs {

  private final GlobalContext context;

  public GlobalStepDefs(GlobalContext context) {
    this.context = context;
  }

  @Given("the base URL is available")
  public void checkUrlAvailability() {
    RestAssured.baseURI = Config.BASE_URI;
    context.setRequest(given());
  }

  @Then("the response status code should be {int}")
  public void checkStatusCode(int status) {
    Assertions.assertEquals(status, context.getOutputStatusCode());
  }
}
