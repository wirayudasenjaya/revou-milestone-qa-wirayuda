package com.example.context;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GlobalContext {
  private int outputStatusCode;
  private Response response;
  private RequestSpecification request;
  private String bookingId;

  public int getOutputStatusCode() {
    return outputStatusCode;
  }

  public void setOutputStatusCode(int outputStatusCode) {
    this.outputStatusCode = outputStatusCode;
  }

  public RequestSpecification getRequest() {
    return request;
  }

  public void setRequest(RequestSpecification request) {
    this.request = request;
  }

  public Response getResponse() {
    return response;
  }

  public void setResponse(Response response) {
    this.response = response;
  }

  public String getBookingId() {
    return bookingId;
  }

  public void setBookingId(String bookingId) {
    this.bookingId = bookingId;
  }
}
