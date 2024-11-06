package com.example.stepdefs;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

public class AuthStepDefs {
  AndroidDriver driver = GlobalStepDefs.getDriver();

  @Given("the user is logged out")
  public void isLoggedOut() {
    driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]")).click();
    driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"menu item log out\"]")).click();
    driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]")).click();
    driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]")).click();
  }

  @Given("the user is on the login screen")
  public void isOnLoginScreen() {
    driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"open menu\"]")).click();
    driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"menu item log in\"]")).click();
  }

  @When("the user enters email {string} and password {string}")
  public void login(String email, String password) {
    driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Username input field\"]")).clear();
    driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Password input field\"]")).clear();
    driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Username input field\"]")).sendKeys(email);
    driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"Password input field\"]")).sendKeys(password);
    driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"Login button\"]")).click();
  }

  @Then("the user should be redirected to home screen")
  public void isRedirectedToHome() {
    Assertions.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text=\"Products\"]")).isDisplayed());
  }

  @Then("the user should get an error: {string}")
  public void isErrorDisplayed(String errorMsg) {
    Assertions.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text=\"" + errorMsg + "\"]")).isDisplayed());
  }
}
