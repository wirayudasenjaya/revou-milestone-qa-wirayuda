package com.example.stepdefs;

import com.example.context.GlobalContext;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class PingStepDefs {
  private final GlobalContext context;

  public PingStepDefs(GlobalContext context) {
    this.context = context;
  }

  @When("the user send a GET request to ping endpoint")
  public void sendGetRequest() {
    Response res = context.getRequest()
        .get("/ping");

    context.setOutputStatusCode(res.getStatusCode());
    context.setResponse(res);
  }
}
