package com.szczygelski;

import java.io.File;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.VncRecordingContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class FirstSeleniumTest {

  @Container
  protected static BrowserWebDriverContainer<?> chrome =
      new BrowserWebDriverContainer<>()
          .withCapabilities(new ChromeOptions())
          .withRecordingMode(
              BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL,
              new File("target"),
              VncRecordingContainer.VncRecordingFormat.MP4);

  protected static WebDriver driver;

  @BeforeAll
  public static void setUpSelenium() {
    driver = new RemoteWebDriver(chrome.getSeleniumAddress(), new ChromeOptions());
  }

  @Test
  public void mojtestnr1() {
    driver.get("https://rrogacz.pl/html-formularze");
    driver.manage().window().setSize(new Dimension(1418, 1002));
    driver.findElement(By.cssSelector(".card > .card > .mb-2")).click();
    driver.findElement(By.cssSelector("li:nth-child(1) > .card > input:nth-child(5)")).click();
    driver.findElement(By.cssSelector("li:nth-child(1) > .card > input:nth-child(5)")).sendKeys("Jan");
    driver.findElement(By.cssSelector("li:nth-child(1) > .card > input:nth-child(8)")).sendKeys("Kowalski");
    driver.findElement(By.cssSelector(".mt-4 > .card > input:nth-child(5)")).click();
    driver.findElement(By.cssSelector(".mt-4 > .card > input:nth-child(5)")).sendKeys("Paweł");
    driver.findElement(By.cssSelector(".mt-4 > .card > input:nth-child(8)")).sendKeys("Nowak");
    driver.findElement(By.cssSelector(".mt-4:nth-child(3) input")).click();
  }

}


