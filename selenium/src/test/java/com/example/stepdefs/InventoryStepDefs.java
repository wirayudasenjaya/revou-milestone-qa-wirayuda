package com.example.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryStepDefs {
  WebDriver driver = GlobalStepDefs.getDriver();
  
  @When("the user clicks on filter dropdown and selects {string}")
  public void selectFilter(String filter) {
    Select fliterDropdown = new Select(driver.findElement(By.className("product_sort_container")));
    fliterDropdown.selectByVisibleText(filter);
  }

  @When("the user clicks on add to cart button")
  public void clickAddToCart() {
    WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
    addToCartButton.click();
  }

  @When("the user clicks on remove from cart button")
  public void clickRemoveFromCart() {
    WebElement removeFromCartButton = driver.findElement(By.id("remove-sauce-labs-backpack"));
    removeFromCartButton.click();
  }

  @When("the user enters detail page")
  public void goToDetailPage() {
    WebElement productLink = driver.findElement(By.id("item_4_title_link"));
    productLink.click();
    String currentPage = driver.getCurrentUrl();
    Assertions.assertEquals("https://www.saucedemo.com/inventory-item.html?id=4", currentPage);
  }

  @When("the user clicks on burger menu")
  public void clickBurgerMenu() {
    WebElement menu = driver.findElement(By.id("react-burger-menu-btn"));
    menu.click();
  }

  @When("the user clicks on close button")
  public void clickCloseDrawerButton() {
    WebElement button = driver.findElement(By.id("react-burger-cross-btn"));
    button.click();
  }

  @When("the user clicks on cart button")
  public void clickCartButton() {
    WebElement cart = driver.findElement(By.id("shopping_cart_container"));
    cart.click();
  }

  @When("the user clicks on product name")
  public void clickProductName() {
    WebElement name = driver.findElement(By.className("inventory_item_name"));
    name.click();
  }

  @Then("the user should see inventory item list")
  public void checkInventoryItemVisibility() {
    WebElement inventoryItem = driver.findElement(By.className("inventory-list"));
    Assertions.assertTrue(inventoryItem.isDisplayed());
  }

  @Then("the items should be ordered alphabetically {string}")
  public void checkItemsOrderAlphabetically(String order) {
    List<WebElement> inventoryItems = driver.findElements(By.className("inventory_item"));
    List<String> productNames = inventoryItems.stream()
        .map(item -> item.findElement(By.className("inventory_item_name")).getText())
        .collect(Collectors.toList());

    List<String> productNameArray = new ArrayList<>(productNames);

    if ("ascending".equalsIgnoreCase(order)) {
      productNameArray.sort(Collections.reverseOrder());
    } else if ("descending".equalsIgnoreCase(order)) {
      Collections.sort(productNameArray);
    } else {
      throw new IllegalArgumentException("Order must be either 'ascending' or 'descending'");
    }

    Assertions.assertEquals(productNames, productNameArray);
  }

  @Then("the items should be ordered by price {string}")
  public void checkItemsOrderByPrice(String order) {
    List<WebElement> inventoryItems = driver.findElements(By.className("inventory_item"));
    List<Double> productPrices = inventoryItems.stream()
        .map(item -> item.findElement(By.className("inventory_item_price")).getText())
        .map(price -> Double.parseDouble(price.replace("$", "").trim()))
        .collect(Collectors.toList());

    List<Double> productPriceArray = new ArrayList<>(productPrices);

    if ("ascending".equalsIgnoreCase(order)) {
      Collections.sort(productPriceArray);
    } else if ("descending".equalsIgnoreCase(order)) {
      productPriceArray.sort(Collections.reverseOrder());
    } else {
      throw new IllegalArgumentException("Order must be either 'ascending' or 'descending'");
    }

    Assertions.assertEquals(productPrices, productPriceArray);
  }

  @Then("the shopping cart badge should show {string}")
  public void checkBadgeVisibility(String count) {
    WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
    String cartBadgeText = cartBadge.getText();
    Assertions.assertEquals(cartBadgeText, count);
  }

  @Then("the shopping cart badge should not appear")
  public void checkBadgeVisibility() {
    WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
    Assertions.assertFalse(cartBadge.isDisplayed());
  }

  @Then("the drawer should be opened")
  public void isDrawerOpen() {
    WebElement drawer = driver.findElement(By.className("bm-menu-wrap"));
    Assertions.assertEquals("false", drawer.getDomAttribute("aria-hidden"));
  }

  @Then("the drawer should be closed")
  public void isDrawerClosed() {
    WebElement drawer = driver.findElement(By.className("bm-menu-wrap"));
    Assertions.assertEquals("true", drawer.getDomAttribute("aria-hidden"));
  }

  @Then("the user should be redirected to detail page")
  public void isOnDetailPage() {
    String currentPage = driver.getCurrentUrl();
    Assertions.assertEquals("https://www.saucedemo.com/inventory-item.html?id=4", currentPage);
  }

  @And("each item should display:")
  public void isCardItemDisplayed(DataTable dataTable) {
    List<String> elements = dataTable.asList();

    for (String element : elements) {
      switch (element.toLowerCase().trim()) {
        case "product image":
          WebElement image = driver.findElement(By.className("inventory_item_img"));
          Assertions.assertTrue(image.isDisplayed());
          break;
        case "product name":
          WebElement name = driver.findElement(By.className("inventory_item_name"));
          Assertions.assertTrue(name.isDisplayed());
          break;
        case "product price":
          WebElement price = driver.findElement(By.className("inventory_item_price"));
          Assertions.assertTrue(price.isDisplayed());
          break;
        case "product description":
          WebElement description = driver.findElement(By.className("inventory_item_desc"));
          Assertions.assertTrue(description.isDisplayed());
          break;
        case "add to cart button":
          WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
          Assertions.assertTrue(addToCartButton.isDisplayed());
          break;
      }
    }
  }

  @And("the page should display:")
  public void isItemDisplayed(DataTable dataTable) {
    List<String> elements = dataTable.asList();

    for (String element : elements) {
      switch (element.toLowerCase().trim()) {
        case "product image":
          WebElement image = driver.findElement(By.className("inventory_details_img"));
          Assertions.assertTrue(image.isDisplayed());
          break;
        case "product name":
          WebElement name = driver.findElement(By.className("inventory_details_name"));
          Assertions.assertTrue(name.isDisplayed());
          break;
        case "product price":
          WebElement price = driver.findElement(By.className("inventory_details_price"));
          Assertions.assertTrue(price.isDisplayed());
          break;
        case "product description":
          WebElement description = driver.findElement(By.className("inventory_details_desc"));
          Assertions.assertTrue(description.isDisplayed());
          break;
        case "add to cart button":
          WebElement addToCartButton = driver.findElement(By.id("add-to-cart"));
          Assertions.assertTrue(addToCartButton.isDisplayed());
          break;
        case "back to products button":
          WebElement backToProductsButton = driver.findElement(By.id("back-to-products"));
          Assertions.assertTrue(backToProductsButton.isDisplayed());
          break;
      }
    }
  }

  @And("the button should change to remove from cart")
  public void checkRemoveFromCartButtonVisibility() {
    WebElement removeFromCartButton = driver.findElement(By.id("remove-sauce-labs-backpack"));
    Assertions.assertTrue(removeFromCartButton.isDisplayed());
  }

  @And("the button should change to add to cart")
  public void checkAddToCartButtonVisibility() {
    WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
    Assertions.assertTrue(addToCartButton.isDisplayed());
  }

  @And("the user clicks on {string} menu")
  public void clickDrawerMenu(String menu) {
    switch (menu) {
      case "All Items":
        WebElement all = driver.findElement(By.id("inventory_sidebar_link"));
        all.click();
        break;
      case "About":
        WebElement about = driver.findElement(By.id("about_sidebar_link"));
        about.click();
        break;
      case "Logout":
        WebElement logout = driver.findElement(By.id("logout_sidebar_link"));
        logout.click();
        break;
      case "Reset App State":
        WebElement reset = driver.findElement(By.id("reset_sidebar_link"));
        reset.click();
        break;
    }
  }

  @And("the page title should be {string}")
  public void getPageTitle(String title) {
    WebElement pageTitle = driver.findElement(By.className("title"));
    String titleText = pageTitle.getText();
    Assertions.assertEquals(titleText, title);
  }

  @And("the user can see following menu:")
  public void verifyMenuItems(DataTable dataTable) {
    List<String> elements = dataTable.asList();
    WebElement menu = driver.findElement(By.cssSelector("nav.bm-item-list"));
    List<WebElement> menuItems = menu.findElements(By.tagName("a"));

    for (String item : elements) {
      boolean found = false;
      for (WebElement menuItem : menuItems) {
        if (menuItem.getText().trim().equals(item.trim())) {
          found = true;
          break;
        }
      }
      if (!found) {
        throw new AssertionError("Menu item not found: " + item);
      }
    }
  }
}
