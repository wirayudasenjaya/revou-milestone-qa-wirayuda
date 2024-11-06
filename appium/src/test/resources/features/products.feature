Feature: Product

  Background:
    Given the user is on product list screen

  Scenario: View Product Detail
    When the user clicks on one of the product
    Then the user should be redirected to product detail screen
    And the screen should display:
      | product image       |
      | product name        |
      | product price       |
      | product description |
      | add to cart button  |
      | ratings             |
      | quantity            |

  Scenario: Adjust Quantity
    Given the user clicks on one of the product
    When the user clicks on plus button
    Then the quantity should display "2"
    When the user clicks on minus button
    Then the quantity should display "1"

  Scenario: Add to Cart
    Given the user clicks on one of the product
    When the user clicks on add to cart button
    Then the cart icon should display a badge

  Scenario: Disable Add to Cart Button
    Given the user clicks on one of the product
    When the user clicks on minus button
    Then the quantity should display "0"
    And the add to cart button should be disabled

  Scenario: Sort Name Ascending
    When the user clicks on filter button
    And selects "Name - Ascending"
    Then products should be sorted by name "ascending"

  Scenario: Sort Name Descending
    When the user clicks on filter button
    And selects "Name - Descending"
    Then products should be sorted by name "descending"

  Scenario: Sort Price Ascending
    When the user clicks on filter button
    And selects "Price - Ascending"
    Then products should be sorted by name "ascending"

  Scenario: Sort Price Descending
    When the user clicks on filter button
    And selects "Price - Descending"
    Then products should be sorted by name "descending"

