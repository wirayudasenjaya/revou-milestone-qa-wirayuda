Feature: Checkout

  Background:
    Given the user is logged out
    And the app state is reset
    Given the user is on product list screen
    And the user adds an item to cart
    And the user is on cart screen

  Scenario: Checkout Without Login
    When the user clicks proceed to checkout button
    Then the user should be redirected to login screen

  Scenario: Checkout
    When the user clicks proceed to checkout button
    And the user logs in to the app
    Then the user should be redirected to checkout screen

  Scenario Outline: Checkout Shipping Form
    When the user clicks proceed to checkout button
    And the user logs in to the app
    Then the user should be redirected to checkout screen
    When the user enters the following shipping details:
      | Full Name      | <Full Name>      |
      | Address Line 1 | <Address Line 1> |
      | Address Line 2 | <Address Line 2> |
      | City           | <City>           |
      | State          | <State>          |
      | Zip Code       | <Zip Code>       |
      | Country        | <Country>        |
    And the user clicks on To Payment button
    Then <Outcome>

    Examples:
      | Full Name | Address Line 1 | Address Line 2 | City     | State | Zip Code | Country       | Outcome                                            |
      | John Doe  | 123 Main St    | Apt 4B         | New York | NY    | 10001    | United States | user should be redirected to payment method        |
      | John Doe  | 123 Main St    |                | New York |       | 10001    | United States | user should be redirected to payment method        |
      |           | 123 Main St    | Apt 4B         | New York | NY    | 10001    | United States | user should get an error on "full name" field      |
      | John Doe  |                | Apt 4B         | New York | NY    | 10001    | United States | user should get an error on "address line 1" field |
      | John Doe  | 123 Main St    | Apt 4B         |          | NY    | 10001    | United States | user should get an error on "city" field           |
      | John Doe  | 123 Main St    | Apt 4B         | New York | NY    |          | United States | user should get an error on "zip code" field       |
      | John Doe  | 123 Main St    | Apt 4B         | New York | NY    | 10001    |               | user should get an error on "country" field        |
      | John Doe  | 123 Main St    | Apt 4B         | New York | NY    | asdfg    | United States | user should get an error on "zip code" field       |

  Scenario Outline: Checkout Payment Form
    When the user clicks proceed to checkout button
    And the user logs in to the app
    Then the user should be redirected to checkout screen
    When the user enters the following shipping details:
      | Full Name      | John Doe      |
      | Address Line 1 | 123 Main St   |
      | Address Line 2 | Apt 4B        |
      | City           | New York      |
      | State          | NY            |
      | Zip Code       | 10001         |
      | Country        | United States |
    And the user clicks on To Payment button
    Then user should be redirected to payment method
    When the user enters the following payment details:
      | Full Name       | <Full Name>       |
      | Card Number     | <Card Number>     |
      | Expiration Date | <Expiration Date> |
      | Security Code   | <Security Code>   |
    And the user clicks on Review Order button
    Then <Outcome>

    Examples:
      | Full Name | Card Number      | Expiration Date | Security Code | Outcome                                             |
      | John Doe  | 1234567812345678 | 11/25           | 123           | user should be redirected to order review           |
      |           | 1234567812345678 | 11/25           | 123           | user should get an error on "full name" field       |
      | John Doe  |                  | 11/25           | 123           | user should get an error on "card number" field     |
      | John Doe  | 1234567812345678 |                 | 123           | user should get an error on "expiration date" field |
      | John Doe  | 1234567812345678 | 11/25           |               | user should get an error on "security code" field   |
      | John Doe  | qwertyasdfghzxcv | 11/25           | 123           | user should get an error on "card number" field     |
      | John Doe  | 123              | 11/25           | 123           | user should get an error on "card number" field     |
      | John Doe  | 1234567812345678 | qw/er           | 123           | user should get an error on "expiration date" field |
      | John Doe  | 1234567812345678 | 1               | 123           | user should get an error on "expiration date" field |
      | John Doe  | 1234567812345678 | 11/25           | asd           | user should get an error on "security code" field   |
      | John Doe  | 1234567812345678 | 11/25           | 1             | user should get an error on "security code" field   |

  Scenario: Checkout Review
    When the user clicks proceed to checkout button
    And the user logs in to the app
    Then the user should be redirected to checkout screen
    When the user enters the following shipping details:
      | Full Name      | John Doe      |
      | Address Line 1 | 123 Main St   |
      | Address Line 2 | Apt 4B        |
      | City           | New York      |
      | State          | NY            |
      | Zip Code       | 10001         |
      | Country        | United States |
    And the user clicks on To Payment button
    Then user should be redirected to payment method
    When the user enters the following payment details:
      | Full Name       | John Doe         |
      | Card Number     | 1234567812345678 |
      | Expiration Date | 11/25            |
      | Security Code   | 123              |
    And the user clicks on Review Order button
    Then user should be redirected to order review
    And the user should see cart items
    And the user should see delivery address
    And the user should see payment method
    When the user clicks Place Order button
    Then user should be redirected to checkout complete
    And cart should be empty
