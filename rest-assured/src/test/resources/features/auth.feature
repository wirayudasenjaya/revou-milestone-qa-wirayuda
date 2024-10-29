@Auth
Feature: Authentication
  As a user
  I want to be able to authenticate
  So that i can update and delete bookings

  Scenario: Successful Login
    Given the base URL is available
    When I send a POST request to auth endpoint with the following details:
      | username       | admin        |
      | password       | password123  |
    Then the response status code should be 200

  Scenario: Failed Login (Wrong Password)
    Given the base URL is available
    When I send a POST request to auth endpoint with the following details:
      | username       | admin        |
      | password       | 12345678     |
    Then the response status code should be 200

  Scenario: Failed Login (Empty Username)
    Given the base URL is available
    When I send a POST request to auth endpoint with the following details:
      | username       |              |
      | password       | 12345678     |
    Then the response status code should be 200

  Scenario: Failed Login (Empty Password)
    Given the base URL is available
    When I send a POST request to auth endpoint with the following details:
      | username       | admin        |
      | password       |              |
    Then the response status code should be 200