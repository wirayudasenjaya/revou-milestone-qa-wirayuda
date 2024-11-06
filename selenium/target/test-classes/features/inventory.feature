Feature: Inventory
  As a user
  I want to view inventory items
  So that I can view detail or order item

  Background:
    Given the user is running on "Chrome" browser
    And the user is on "https://www.saucedemo.com"
    And the user is logged in as "standard_user"
    And the user is on the inventory page

  Scenario: View Inventory Items
    Then the user should see inventory item list
    And each item should display:
      | product image       |
      | product name        |
      | product price       |
      | product description |
      | add to cart button  |

  Scenario: Sort Name (A to Z)
    When the user clicks on filter dropdown and selects "Name (A to Z)"
    Then the items should be ordered alphabetically "descending"

  Scenario: Sort Name (Z to A)
    When the user clicks on filter dropdown and selects "Name (Z to A)"
    Then the items should be ordered alphabetically "ascending"

  Scenario: Sort Price (low to high)
    When the user clicks on filter dropdown and selects "Price (low to high)"
    Then the items should be ordered by price "ascending"

  Scenario: Sort Price (high to low)
    When the user clicks on filter dropdown and selects "Price (high to low)"
    Then the items should be ordered by price "descending"

  Scenario: View Detail
    When the user clicks on product name
    Then the user should be redirected to detail page
    And the page should display:
      | product image            |
      | product name             |
      | product price            |
      | product description      |
      | add to cart button       |
      | back to products button  |
#
  Scenario: Add to Cart
    When the user clicks on add to cart button
    Then the shopping cart badge should show "1"
    And the button should change to remove from cart

  Scenario: Remove from Cart
    When the user clicks on add to cart button
    Then the shopping cart badge should show "1"
    And the button should change to remove from cart
    When the user clicks on remove from cart button
    Then the shopping cart badge should not appear
    And the button should change to add to cart

  Scenario: Add to Cart from Detail Page
    When the user enters detail page
    And the user clicks on add to cart button inside detail page
    Then the shopping cart badge should show "1"
    And the button should change to remove from cart inside detail page

  Scenario: Remove from Cart from Detail Page
    When the user enters detail page
    And the user clicks on add to cart button inside detail page
    Then the shopping cart badge should show "1"
    And the button should change to remove from cart inside detail page
    When the user clicks on remove from cart inside detail page
    Then the button should change to add to cart inside detail page

  Scenario: Open Cart Page
    When the user clicks on cart button
    Then the user should be redirected to "https://www.saucedemo.com/cart.html"
    And the page title should be "Your Cart"

  Scenario: Open/Close Drawer
    When the user clicks on burger menu
    Then the drawer should be opened
    And the user can see following menu:
      | All Items       |
      | About           |
      | Logout          |
      | Reset App State |
    When the user clicks on close button
    Then the drawer should be closed

  Scenario: Open All Items Page
    When the user clicks on burger menu
    And the user clicks on "All Items" menu
    Then the user should be redirected to "https://saucelabs.com/inventory.html"

  Scenario: Open About Page
    When the user clicks on burger menu
    And the user clicks on "About" menu
    Then the user should be redirected to "https://saucelabs.com/"

  Scenario: Logout
    When the user clicks on burger menu
    And the user clicks on "Logout" menu
    Then the user should be redirected to "https://www.saucedemo.com"

  Scenario: Reset App State
    When the user clicks on add to cart button
    Then the shopping cart badge should show "1"
    When the user clicks on burger menu
    And the user clicks on "Reset App State" menu
    Then the shopping cart badge should not appear
    And the button should change to add to cart