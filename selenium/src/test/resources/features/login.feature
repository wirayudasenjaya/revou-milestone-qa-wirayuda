Feature: Login
  As a user
  I want to be able to login
  So that I can shop on the site

  Scenario Outline: Login
    Given the user is running on "Chrome" browser
    And the user is on "https://www.saucedemo.com"
    When the user enter username as "<username>" and password as "<password>"
    Then <outcome>

    Examples:
      | username                | password     | outcome                                                                     |
      | standard_user           | secret_sauce | the user should be redirected to "https://www.saucedemo.com/inventory.html" |
      | standard_user           | wrong_sauce  | the user should get an error message                                        |
      | locked_out_user         | secret_sauce | the user should get an error message                                        |
      | locked_out_user         | wrong_sauce  | the user should get an error message                                        |
      | problem_user            | secret_sauce | the user should be redirected to "https://www.saucedemo.com/inventory.html" |
      | problem_user            | wrong_sauce  | the user should get an error message                                        |
      | performance_glitch_user | secret_sauce | the user should be redirected to "https://www.saucedemo.com/inventory.html" |
      | performance_glitch_user | wrong_sauce  | the user should get an error message                                        |
      | error_user              | secret_sauce | the user should be redirected to "https://www.saucedemo.com/inventory.html" |
      | error_user              | wrong_sauce  | the user should get an error message                                        |
      | visual_user             | secret_sauce | the user should be redirected to "https://www.saucedemo.com/inventory.html" |
      | visual_user             | wrong_sauce  | the user should get an error message                                        |
      |                         | secret_sauce | the user should get an error message                                        |
      | standard_user           |              | the user should get an error message                                        |