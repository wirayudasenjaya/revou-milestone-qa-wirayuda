package com.example.stepdefs;

import com.example.context.GlobalContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class AuthStepDefs {
  private final GlobalContext context;

  public AuthStepDefs(GlobalContext context) {
    this.context = context;
  }

  @When("I send a POST request to auth endpoint with the following details:")
  public void sendPostRequest(DataTable dataTable) {
    Map<String, String> authData = dataTable.asMap(String.class, String.class);

    Map<String, Object> authPayload = new HashMap<>(authData);

    Response res = context.getRequest()
        .body(authPayload)
        .post("/auth");

    context.setOutputStatusCode(res.getStatusCode());
    context.setResponse(res);
  }
}
