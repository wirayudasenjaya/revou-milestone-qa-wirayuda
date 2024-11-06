Feature: Cart

  Background:
    Given the user is on product list screen

  Scenario: Empty Cart
    When the user opens the cart
    Then the user should see No items

  Scenario: Go Shopping Button
    When the user opens the cart
    Then the user should see No items
    When the user clicks Go Shopping
    Then the user should be redirected to home screen

  Scenario: Cart Items
    Given the user adds an item to cart
    When the user opens the cart
    Then the user should see the added item

  Scenario: Remove Item from Cart
    Given the user adds an item to cart
    When the user opens the cart
    And the user clicks Remove item
    Then the item is no longer in the cart