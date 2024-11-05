Feature: Checkout
  As a user
  I want to be able to view items on cart and complete purchase
  So that I can buy my desired items

  Background:
    Given the user is running on "Chrome" browser
    And the user is on "https://www.saucedemo.com"
    And the user is logged in as "standard_user"
    And the user is on the inventory page

  Scenario: Add to cart and verify
    When the user clicks on add to cart button
    And the user clicks on cart button
    Then the user should see the added item

  Scenario: Remove item from cart
    Given the user already add an item to cart
    And the user clicks on cart button
    When the user clicks on remove item
    Then the item will be removed

  Scenario: Continue shopping
    Given the user already add an item to cart
    When the user clicks on cart button
    And the user clicks on Continue Shopping
    Then the user should be redirected to "https://www.saucedemo.com/inventory.html"

  Scenario: Checkout without item
    Given the cart is empty
    When the user clicks on cart button
    And the user clicks on Checkout
    Then the user should get an error message

  Scenario: Checkout
    Given the user already add an item to cart
    And the user clicks on cart button
    When the user clicks on Checkout
    Then the user should be redirected to "https://www.saucedemo.com/checkout-step-one.html"

  Scenario Outline: Checkout Step One
    Given the user already add an item to cart
    And the user clicks on cart button
    And the user clicks on Checkout
    Then the user should be redirected to "https://www.saucedemo.com/checkout-step-one.html"
    When the user provides the following checkout information:
      | First Name   | Last Name   | Zip Code   |
      | <first_name> | <last_name> | <zip_code> |
    And the user clicks on Continue
    Then <outcome>

    Examples:
      | first_name | last_name | zip_code | outcome                                                                             |
      | Jim        | Brown     | 12345    | the user should be redirected to "https://www.saucedemo.com/checkout-step-two.html" |
      |            | Brown     | 12345    | the user should get an error message                                                |
      | Jim        |           | 12345    | the user should get an error message                                                |
      | Jim        | Brown     |          | the user should get an error message                                                |
      | Jim        | Brown     | asdfg    | the user should get an error message                                                |
      |            |           |          | the user should get an error message                                                |

  Scenario: Checkout Step Two
    Given the user already add an item to cart
    And the user clicks on cart button
    And the user clicks on Checkout
    And the user already provides checkout information
