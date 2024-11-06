@Ping
Feature: Ping
  As a user
  I want to check on API status
  So that I know whether the API is usable or not
  
  Scenario: Ping
    Given the base URL is available
    When the user send a GET request to ping endpoint
    Then the response status code should be 200