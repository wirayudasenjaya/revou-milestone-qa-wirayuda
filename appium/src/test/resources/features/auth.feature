Feature: Auth

  Background:
    Given the user is logged out

  Scenario Outline: Login
    Given the user is on the login screen
    When the user enters email "<email>" and password "<password>"
    Then <outcome>

    Examples:
      | email             | password   | outcome                                                                                     |
      | bob@example.com   | 10203040   | the user should be redirected to home screen                                                |
      | alice@example.com | 10203040   | the user should get an error: "Sorry, this user has been locked out."                       |
      | bob@example.com   | 1020304050 | the user should get an error: "Provided credentials do not match any user in this service." |
      | alice@example.com | 1020304050 | the user should get an error: "Provided credentials do not match any user in this service." |