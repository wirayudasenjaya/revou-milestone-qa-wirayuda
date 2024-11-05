package com.example.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class GlobalStepDefs {
  private static WebDriver driver;

  public static WebDriver getDriver() {
    return driver;
  }

  public static void setDriver(String browser) {
    if (driver != null) {
      driver.quit();
    }

    switch (browser.toLowerCase()) {
      case "chrome":
        driver = new ChromeDriver();
        break;
      case "firefox":
        driver = new FirefoxDriver();
        break;
      case "edge":
        driver = new EdgeDriver();
        break;
      case "safari":
        driver = new SafariDriver();
        break;
      default:
        throw new IllegalArgumentException("Browser not supported: " + browser);
    }

    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
  }

  public static void quitDriver() {
    if (driver != null) {
      driver.quit();
      driver = null;
    }
  }

  @Given("the user is running on {string} browser")
  public void runOnBrowser(String browser) {
    setDriver(browser);
  }

  @And("the user is logged in as {string}")
  public void loginToSite(String username) {
    WebElement usernameField = GlobalStepDefs.getDriver().findElement(By.name("user-name"));
    WebElement passwordField = GlobalStepDefs.getDriver().findElement(By.name("password"));
    WebElement submitButton = GlobalStepDefs.getDriver().findElement(By.name("login-button"));

    usernameField.sendKeys(username);
    passwordField.sendKeys("secret_sauce");
    submitButton.click();
  }

  @And("the user is on the inventory page")
  public void checkUserOnInventoryPage() {
    String currentPage = getDriver().getCurrentUrl();
    Assertions.assertEquals(currentPage, "https://www.saucedemo.com/inventory.html");
  }

  @After
  public void tearDown() {
    if (getDriver() != null) {
      getDriver().quit();
    }
  }
}
