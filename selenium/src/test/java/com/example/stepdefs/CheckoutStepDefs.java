package com.example.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

public class CheckoutStepDefs {
  WebDriver driver = GlobalStepDefs.getDriver();

  @Given("the cart is empty")
  public void cartIsEmpty() {
    List<WebElement> cartBadge = GlobalStepDefs.getDriver().findElements(By.className("shopping_cart_badge"));
    Assertions.assertTrue(cartBadge.isEmpty());
  }

  @Given("the user already add an item to cart")
  public void addItemToCart() {
    WebElement addToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
    addToCartButton.click();
  }

  @When("the user clicks on remove item")
  public void clickRemoveItem() {
    WebElement removeButton = driver.findElement(By.id("remove-sauce-labs-backpack"));
    removeButton.click();
  }

  @When("the user provides the following checkout information:")
  public void inputDetails(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    Map<String, String> checkoutData = rows.get(0);

    try {
      WebElement firstNameElement = driver.findElement(By.name("firstName"));
      WebElement lastNameElement = driver.findElement(By.name("lastName"));
      WebElement zipCodeElement = driver.findElement(By.name("postalCode"));

      String firstName = checkoutData.get("First Name");
      firstNameElement.clear();
      if (firstName != null && !firstName.trim().isEmpty()) {
        firstNameElement.sendKeys(firstName);
      }

      String lastName = checkoutData.get("Last Name");
      lastNameElement.clear();
      if (lastName != null && !lastName.trim().isEmpty()) {
        lastNameElement.sendKeys(lastName);
      }

      String zipCode = checkoutData.get("Zip Code");
      zipCodeElement.clear();
      if (zipCode != null && !zipCode.trim().isEmpty()) {
        zipCodeElement.sendKeys(zipCode);
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to input checkout information: " + e.getMessage(), e);
    }
  }

  @When("the user clicks on Finish")
  public void clickFinish() {
    WebElement button = driver.findElement(By.id("finish"));
    button.click();
  }

  @Then("the user should see the added item")
  public void isItemAdded() {
    WebElement cartItem = driver.findElement(By.id("item_4_title_link"));
    String cartItemText = cartItem.getText();
    Assertions.assertEquals("Sauce Labs Backpack", cartItemText);
  }

  @Then("the item will be removed")
  public void isItemRemoved() {
    List<WebElement> cartItems = driver.findElements(By.id("item_4_title_link"));
    Assertions.assertTrue(cartItems.isEmpty());
  }

  @Then("the user should see cart item list")
  public void isCartListDisplayed() {
    WebElement cartItems = driver.findElement(By.className("cart_list"));
    Assertions.assertTrue(cartItems.isDisplayed());
  }

  @Then("the user should be redirected to complete purchase")
  public void completePurchaseDisplayed() {
    WebElement container = driver.findElement(By.id("checkout_complete_container"));
    Assertions.assertTrue(container.isDisplayed());
  }

  @And("the user clicks on Continue Shopping")
  public void clickContinueShopping() {
    WebElement button = driver.findElement(By.id("continue-shopping"));
    button.click();
  }

  @And("the user clicks on Checkout")
  public void clickCheckout() {
    WebElement button = driver.findElement(By.id("checkout"));
    button.click();
  }

  @And("the user clicks on Continue")
  public void clickContinue() {
    WebElement button = driver.findElement(By.id("continue"));
    button.click();
  }

  @And("the user already provides checkout information")
  public void provideCheckoutInformation() {
    WebElement firstNameElement = driver.findElement(By.name("firstName"));
    WebElement lastNameElement = driver.findElement(By.name("lastName"));
    WebElement zipCodeElement = driver.findElement(By.name("postalCode"));
    WebElement button = driver.findElement(By.id("continue"));

    firstNameElement.sendKeys("Jim");
    lastNameElement.sendKeys("Brown");
    zipCodeElement.sendKeys("12345");

    button.click();
  }
}
