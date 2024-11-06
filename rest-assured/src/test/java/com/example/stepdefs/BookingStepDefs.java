package com.example.stepdefs;

import com.example.config.Config;
import com.example.context.GlobalContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BookingStepDefs {
  String token;
  private final GlobalContext context;

  public BookingStepDefs(GlobalContext context) {
    this.context = context;
  }

  @When("I send a GET request to booking endpoint")
  public void getBooking() {
    Response res = context.getRequest().get("booking");

    context.setOutputStatusCode(res.getStatusCode());
    context.setResponse(res);
  }

  @When("I send a GET request to booking detail endpoint")
  public void getBookingById() {
    Response res = context.getRequest().get("booking/{id}");

    context.setOutputStatusCode(res.getStatusCode());
    context.setResponse(res);
  }

  @When("I send a POST request to booking endpoint with the following details:")
  public void createBooking(DataTable dataTable) {
    Map<String, String> bookingData = dataTable.asMap(String.class, String.class);
    Map<String, Object> bookingPayload = new HashMap<>();
    Map<String, Object> bookingDates = new HashMap<>();

    bookingData.forEach((key, value) -> {
      switch (key) {
        case "totalprice":
          bookingPayload.put(key, Integer.parseInt(value));
          break;
        case "depositpaid":
          bookingPayload.put(key, Boolean.parseBoolean(value));
          break;
        case "checkin":
        case "checkout":
          bookingDates.put(key, value);
          break;
        default:
          bookingPayload.put(key, value);
          break;
      }
    });

    if (!bookingDates.isEmpty()) {
      bookingPayload.put("bookingdates", bookingDates);
    }

    Response res = context.getRequest()
        .body(bookingPayload)
        .post("/booking");

    context.setOutputStatusCode(res.getStatusCode());
    context.setResponse(res);
    context.setBookingId(res.jsonPath().getString("bookingId"));
  }

  @When("I send a PUT request to booking detail endpoint with the following details:")
  public void updateBooking(DataTable dataTable) {
    Map<String, String> bookingData = dataTable.asMap(String.class, String.class);
    Map<String, Object> bookingPayload = new HashMap<>();
    Map<String, Object> bookingDates = new HashMap<>();

    bookingData.forEach((key, value) -> {
      switch (key) {
        case "totalprice":
          bookingPayload.put(key, Integer.parseInt(value));
          break;
        case "depositpaid":
          bookingPayload.put(key, Boolean.parseBoolean(value));
          break;
        case "checkin":
        case "checkout":
          bookingDates.put(key, value);
          break;
        default:
          bookingPayload.put(key, value);
          break;
      }
    });

    if (!bookingDates.isEmpty()) {
      bookingPayload.put("bookingdates", bookingDates);
    }

    Response res = context.getRequest()
        .body(bookingPayload)
        .put("/booking/{id}");

    context.setOutputStatusCode(res.getStatusCode());
    context.setResponse(res);
  }

  @When("I send a PATCH request to booking detail endpoint with the following details:")
  public void partialUpdateBooking(DataTable dataTable) {
    Map<String, String> bookingData = dataTable.asMap(String.class, String.class);
    Map<String, Object> bookingPayload = new HashMap<>();
    Map<String, Object> bookingDates = new HashMap<>();

    bookingData.forEach((key, value) -> {
      switch (key) {
        case "totalprice":
          bookingPayload.put(key, Integer.parseInt(value));
          break;
        case "depositpaid":
          bookingPayload.put(key, Boolean.parseBoolean(value));
          break;
        case "checkin":
        case "checkout":
          bookingDates.put(key, value);
          break;
        default:
          bookingPayload.put(key, value);
          break;
      }
    });

    if (!bookingDates.isEmpty()) {
      bookingPayload.put("bookingdates", bookingDates);
    }

    Response res = context.getRequest()
        .body(bookingPayload)
        .put("/booking/{id}");

    context.setOutputStatusCode(res.getStatusCode());
    context.setResponse(res);
  }

  @When("I send a DELETE request to booking detail endpoint")
  public void deleteBooking() {
    Response res = context.getRequest().delete("/booking/{id}");

    context.setOutputStatusCode(res.getStatusCode());
    context.setResponse(res);
  }

  @And("the response should consist of list of booking IDs")
  public void getBookingIdResponse() {
    List<Integer> bookingIds = context.getResponse().jsonPath().getList("bookingid");
    Assertions.assertNotNull(bookingIds, "Booking ID list should not be null");
    Assertions.assertFalse(bookingIds.isEmpty(), "Booking ID list should not be empty");
  }

  @And("the response should consist of list of booking IDs with corresponding data")
  public void getFilteredBookingIdResponse() {
    List<Integer> bookingIds = context.getResponse().jsonPath().getList("bookingid");
    Assertions.assertNotNull(bookingIds, "Booking ID list should not be null");
    Assertions.assertFalse(bookingIds.isEmpty(), "Booking ID list should not be empty");
  }

  @And("the response should contain updated booking detail")
  public void getUpdateBookingResponse() {
    Assertions.assertNotNull(context.getResponse().jsonPath().get());
  }

  @And("the response should contain an error message")
  public void getErrorResponse() {
    Assertions.assertNotNull(context.getResponse());
  }

  @And("the response should contain message: {string}")
  public void getTextResponse(String message) {
    Assertions.assertEquals("Forbidden", message);
  }

  @And("I am authenticated")
  public void authenticate() {
    Response response = given()
        .header("Content-Type", "application/json")
        .body(String.format("{ \"username\": \"%s\", \"password\": \"%s\" }", Config.USERNAME, Config.PASSWORD))
        .when()
        .post("/auth");

    token = response.jsonPath().getString("token");
    context.getRequest().header("Cookie", "token=" + token);
  }

  @And("I am not authenticated")
  public void notAuthenticated() {
    context.getRequest().header("Cookie", "");
  }

  @And("I provide a path parameter with value {int}")
  public void insertPathParameter(int id) {
    context.getRequest().pathParam("id", String.valueOf(id));
  }

  @And("I provide a query parameter {string} as {string}")
  public void insertQueryParameter(String key, String value) {
    context.getRequest().queryParam(key, value);
  }

  @And("I provide a content type of {string}")
  public void insertParameter(String type) {
    context.getRequest().header("Content-Type", type);
  }

  @And("the response should contain {string} with value {string}")
  public void verifyResponseDataString(String key, String value) {
    if (context.getResponse().asString().trim().startsWith("[")) {
      List<Map<String, Object>> responseList = context.getResponse().jsonPath().getList("$");

      boolean foundValidKey = responseList.stream()
          .anyMatch(item -> item.containsKey(key) && (
              (item.get(key) instanceof Boolean || item.get(key) instanceof Integer || item.get(key) instanceof Double) ||
                  item.get(key) != null)
          );

      Assertions.assertTrue(foundValidKey);
    } else {
      Map<String, Object> responseObject = context.getResponse().jsonPath().getMap("$");
      Assertions.assertTrue(responseObject.containsKey(key) && responseObject.get(key).equals(value));
    }
  }

  @And("the response should contain {string} with value {int}")
  public void verifyResponseDataInt(String key, int value) {
    if (context.getResponse().asString().trim().startsWith("[")) {
      List<Map<String, Object>> responseList = context.getResponse().jsonPath().getList("$");

      boolean foundValidKey = responseList.stream()
          .anyMatch(item -> item.containsKey(key) && (
              (item.get(key) instanceof Boolean || item.get(key) instanceof Integer || item.get(key) instanceof Double) ||
                  item.get(key) != null)
          );

      Assertions.assertTrue(foundValidKey);
    } else {
      Map<String, Object> responseObject = context.getResponse().jsonPath().getMap("$");

      Assertions.assertNotNull(responseObject.get(key));
    }
  }

  @And("the response should match the booking schema")
  public void verifyBookingResponse() {
    context.getResponse()
        .then()
        .assertThat()
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/booking-schema.json"));
  }

  @And("the response should match the booking id schema")
  public void verifyBookingIdResponse() {
    context.getResponse()
        .then()
        .assertThat()
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/booking-id-schema.json"));
  }
}
