@Booking
Feature: Booking
  As a user
  I want to manage my bookings
  So that i can create, edit, or delete booking information

  Scenario Outline: Get Booking ID By Search Parameters
    Given the base URL is available
    And I provide a query parameter "<parameter>" as "<value>"
    When I send a GET request to booking endpoint
    Then the response status code should be 200
    And the response should consist of list of booking IDs with corresponding data
    Examples:
      | parameter | value      |
      | firstname | John       |
      | lastname  | Smith      |
      | checkin   | 2024-01-11 |
      | checkout  | 2024-01-15 |

  Scenario: Get Booking ID
    Given the base URL is available
    When I send a GET request to booking endpoint
    Then the response status code should be 200
    And the response should match the booking id schema

  Scenario: Get Booking Detail By ID
    Given the base URL is available
    And I provide a path parameter with value 1
    When I send a GET request to booking detail endpoint
    Then the response status code should be 200
    And the response should match the booking schema

  Scenario Outline: Create Booking with Different Requirements
    Given the base URL is available
    And I provide a content type of "application/json"
    When I send a POST request to booking endpoint with the following details:
      | firstname       | <firstname>       |
      | lastname        | <lastname>        |
      | totalprice      | <totalprice>      |
      | depositpaid     | <depositpaid>     |
      | checkin         | <checkin>         |
      | checkout        | <checkout>        |
      | additionalneeds | <additionalneeds> |
    Then the response status code should be <status_code>
    And the response should <response_action>
    Examples:
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds | status_code | response_action                 |
      | Jim       | Brown    | 111        | true        | 2024-10-01 | 2024-10-05 | Breakfast       | 200         | match the create booking schema |
      | Jim       | Brown    | 111        | true        | 2024-10-01 | 2024-10-05 |                 | 200         | match the create booking schema |
      | Jim       | Brown    | 111        |             |            |            |                 | 500         | contain an error message        |

  Scenario Outline: Update Booking
    Given the base URL is available
    And <auth_status>
    And I provide a path parameter with value <booking_id>
    When I send a PUT request to booking detail endpoint with the following details:
      | firstname       | <firstname>       |
      | lastname        | <lastname>        |
      | totalprice      | <totalprice>      |
      | depositpaid     | <depositpaid>     |
      | checkin         | <checkin>         |
      | checkout        | <checkout>        |
      | additionalneeds | <additionalneeds> |
    Then the response status code should be <status_code>
    And the response should <response_action>
    Examples:
      | auth_status            | booking_id | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds | status_code | response_action              |
      | I am authenticated     | 1          | Jim       | Brown    | 111        | true        | 2024-10-01 | 2024-10-05 | Breakfast       | 200         | match the booking schema     |
      | I am authenticated     | 1          | Jim       | Brown    | 111        | true        | 2024-10-01 | 2024-10-05 |                 | 200         | match the booking schema     |
      | I am authenticated     | 1          | Jim       | Brown    | 111        |             |            |            |                 | 500         | contain an error message     |
      | I am not authenticated | 1          | Jim       | Brown    | 111        | true        | 2024-10-01 | 2024-10-05 | Breakfast       | 403         | contain message: "Forbidden" |

  Scenario Outline: Partial Update Booking with Different Authentication
    Given the base URL is available
    And <auth_status>
    And I provide a path parameter with value <booking_id>
    When I send a PATCH request to booking detail endpoint with the following details:
      | firstname  | <firstname>  |
      | lastname   | <lastname>   |
      | totalprice | <totalprice> |
    Then the response status code should be <status_code>
    And the response should <response_action>
    Examples:
      | auth_status            | booking_id | firstname | lastname | totalprice | status_code | response_action              |
      | I am authenticated     | 1          | Jim       | Brown    | 222        | 200         | match the booking schema     |
      | I am not authenticated | 1          | Jim       | Brown    | 222        | 403         | contain message: "Forbidden" |

  Scenario Outline: Delete Booking with Different Authentication
    Given the base URL is available
    And <auth_status>
    And I provide a path parameter with value <booking_id>
    When I send a DELETE request to booking detail endpoint
    Then the response status code should be <status_code>
    And the response should contain message: "<message>"
    Examples:
      | auth_status            | booking_id | status_code | message   |
      | I am not authenticated | 2          | 403         | Forbidden |
      | I am authenticated     | 2          | 201         | Created   |