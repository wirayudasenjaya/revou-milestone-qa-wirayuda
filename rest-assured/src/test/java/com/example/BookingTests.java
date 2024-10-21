package com.example;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookingTests extends ApiTests {
  private String bookingId;

  @Test(priority = 1, description = "Get Booking ID")
  public void getBookingIds() {
    given()
        .when()
        .get("/booking")
        .then()
        .statusCode(200)
        .body("bookingid", notNullValue());
  }

  @Test(priority = 1, description = "Get Booking ID By First Name/Last Name")
  public void getBookingIdsByName() {
    given()
        .queryParam("firstname", "Mary")
        .when()
        .get("/booking")
        .then()
        .statusCode(200)
        .body("bookingid", notNullValue());
  }

  @Test(priority = 1, description = "Get Booking ID By Check-in/Check-out Date")
  public void getBookingIdsByDate() {
    given()
        .queryParam("checkin", "2018-01-01")
        .when()
        .get("/booking")
        .then()
        .statusCode(200)
        .body("bookingid", notNullValue());
  }

  @Test(priority = 2, description = "Get Booking Detail By ID")
  public void getBooking() {
    given()
        .pathParam("bookingId", 1)
        .when()
        .get("/booking/{bookingId}")
        .then()
        .statusCode(200)
        .body("firstname", notNullValue())
        .body("lastname", notNullValue())
        .body("totalprice", notNullValue())
        .body("depositpaid", notNullValue())
        .body("bookingdates", notNullValue());
  }

  @Test(priority = 3, description = "Create Booking")
  public void createBooking() {
    String bookingPayload = "{ " +
        "\"firstname\": \"John\", " +
        "\"lastname\": \"Smith\", " +
        "\"totalprice\": 111, " +
        "\"depositpaid\": true, " +
        "\"bookingdates\": { " +
        "\"checkin\": \"2018-01-01\", " +
        "\"checkout\": \"2019-01-01\" }, " +
        "\"additionalneeds\": \"Breakfast\" }";

    Response response = given()
        .header("Content-Type", "application/json")
        .body(bookingPayload)
        .when()
        .post("/booking");

    bookingId = response.jsonPath().getString("bookingid");

    response.then()
            .statusCode(200)
            .body("bookingid", notNullValue());
  }

  @Test(priority = 3, description = "Create Booking Without Optional Data")
  public void createBookingWithoutNeeds() {
    String bookingPayload = "{ " +
        "\"firstname\": \"John\", " +
        "\"lastname\": \"Smith\", " +
        "\"totalprice\": 111, " +
        "\"depositpaid\": true, " +
        "\"bookingdates\": { " +
        "\"checkin\": \"2018-01-01\", " +
        "\"checkout\": \"2019-01-01\" } " + "}";

    given()
        .header("Content-Type", "application/json")
        .body(bookingPayload)
        .when()
        .post("/booking")
        .then()
        .statusCode(200)
        .body("bookingid", notNullValue());
  }

  @Test(priority = 3, description = "Create Booking With Missing Parameters")
  public void createBookingMissingFields() {
    String bookingPayload = "{ " +
        "\"firstname\": \"John\", " +
        "\"lastname\": \"Smith\", " +
        "\"totalprice\": 111, " +
        "\"depositpaid\": true " + "}";

    given()
        .header("Content-Type", "application/json")
        .body(bookingPayload)
        .when()
        .post("/booking")
        .then()
        .statusCode(not(200));
  }

  @Test(priority = 3, description = "Create Booking With Invalid Data")
  public void createBookingWithInvalidData() {
    String bookingPayload = "{ " +
        "\"firstname\": \"Andy\", " +
        "\"lastname\": \"Smith\", " +
        "\"totalprice\": -1000, " +
        "\"depositpaid\": true, " +
        "\"bookingdates\": { " +
        "\"checkin\": \"2018-02-31\", " +
        "\"checkout\": \"2019-03-01\" }, " +
        "\"additionalneeds\": \"Breakfast\" }";

    given()
        .header("Content-Type", "application/json")
        .body(bookingPayload)
        .when()
        .post("/booking")
        .then()
        .statusCode(not(200));
  }

  @Test(priority = 4, description = "Update Booking")
  public void updateBooking() {
    String updatePayload = "{ " +
        "\"firstname\": \"James\", " +
        "\"lastname\": \"Brown\", " +
        "\"totalprice\": 112, " +
        "\"depositpaid\": true, " +
        "\"bookingdates\": { " +
        "\"checkin\": \"2018-01-02\", " +
        "\"checkout\": \"2019-01-02\" }, " +
        "\"additionalneeds\": \"Breakfast\" }";

    given()
        .pathParam("bookingId", bookingId)
        .header("Content-Type", "application/json")
        .header("Cookie", "token=" + token)
        .body(updatePayload)
        .when()
        .put("/booking/{bookingId}")
        .then()
        .statusCode(200)
        .body("firstname", equalTo("James"))
        .body("lastname", equalTo("Brown"))
        .body("totalprice", equalTo(112))
        .body("depositpaid", equalTo(true))
        .body("additionalneeds", equalTo("Breakfast"));
  }

  @Test(priority = 5, description = "Partial Update Booking")
  public void partialUpdateBooking() {
    String partialUpdatePayload = "{ " +
        "\"firstname\": \"Jack\", " +
        "\"lastname\": \"Jones\" }";

    given()
        .pathParam("bookingId", bookingId)
        .header("Content-Type", "application/json")
        .header("Cookie", "token=" + token)
        .body(partialUpdatePayload)
        .when()
        .patch("/booking/{bookingId}")
        .then()
        .statusCode(200)
        .body("firstname", equalTo("Jack"))
        .body("lastname", equalTo("Jones"));
  }

  @Test(priority = 6, description = "Delete Booking")
  public void deleteBooking() {

    given()
        .pathParam("bookingId", bookingId)
        .header("Content-Type", "application/json")
        .header("Cookie", "token=" + token)
        .when()
        .delete("/booking/{bookingId}")
        .then()
        .statusCode(201);
  }
}
