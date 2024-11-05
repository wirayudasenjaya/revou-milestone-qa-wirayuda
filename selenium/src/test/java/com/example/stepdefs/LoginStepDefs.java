package com.example.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginStepDefs {
  @When("the user enter username as {string} and password as {string}")
  public void enterCredentials(String username, String password) {
    WebElement usernameField = GlobalStepDefs.getDriver().findElement(By.name("user-name"));
    WebElement passwordField = GlobalStepDefs.getDriver().findElement(By.name("password"));
    WebElement submitButton = GlobalStepDefs.getDriver().findElement(By.name("login-button"));

    usernameField.sendKeys(username);
    passwordField.sendKeys(password);
    submitButton.click();
  }

  @Then("the user should be redirected to {string}")
  public void checkRedirectPage(String url) {
    String currentPage = GlobalStepDefs.getDriver().getCurrentUrl();
    Assertions.assertEquals(currentPage, url);
  }

  @Then("the user should get an error message")
  public void checkErrorVisibility() {
    WebElement errorTextElement = GlobalStepDefs.getDriver().findElement(By.cssSelector(".error-message-container h3"));
    Assertions.assertEquals("error", errorTextElement.getDomAttribute("data-test"));
  }

  @And("the user is on {string}")
  public void checkPage (String url){
    GlobalStepDefs.getDriver().get(url);
  }

  @After
  public void tearDown() {
    if (GlobalStepDefs.getDriver() != null) {
      GlobalStepDefs.getDriver().quit();
    }
  }
}
