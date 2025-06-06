package com.szczygelski;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import java.io.UnsupportedEncodingException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.VncRecordingContainer;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

public class FormSteps {
    private static BrowserWebDriverContainer<?> chrome =
            new BrowserWebDriverContainer<>()
                    .withCapabilities(new ChromeOptions())
                    .withRecordingMode(
                            BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL,
                            new File("target"),
                            VncRecordingContainer.VncRecordingFormat.MP4);

    private static WebDriver driver;

    @Before
    public void setUp() {
        if (driver == null) {
            chrome.start();
            driver = new RemoteWebDriver(chrome.getSeleniumAddress(), new ChromeOptions());
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        if (chrome != null && chrome.isRunning()) {
            chrome.stop();
        }
    }
    @After
    public void afterScenario(Scenario scenario) throws UnsupportedEncodingException {
        // Dodanie tekstowej informacji
        // Szukanie najnowszego pliku mp4 w katalogu target
        File dir = new File("target");
        File[] mp4s = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".mp4"));
        if (mp4s != null && mp4s.length > 0) {
            File newest = Arrays.stream(mp4s)
                .max(Comparator.comparingLong(File::lastModified))
                .orElse(null);
            if (newest != null) {
                String fileUrl = "file:///" + newest.getAbsolutePath();
                scenario.attach(fileUrl.getBytes("UTF-8"), "text/uri-list", "Link do nagrania testu");
            }
        }
    }

    @Given("Otwieram stronę formularza")
    public void otwieramStroneFormularza() {
        driver.get("https://rrogacz.pl/html-formularze");
        driver.manage().window().setSize(new Dimension(1418, 1002));
    }


    @When("Wpisuję Imię osoby uprawiającej sport {string} i Nazwisko {string}")
    public void wpisujeImieINazwiskoSportowca(String imie, String nazwisko) {
        driver.findElement(By.cssSelector("li:nth-child(1) > .card > input:nth-child(5)")).sendKeys(imie);
        driver.findElement(By.cssSelector("li:nth-child(1) > .card > input:nth-child(8)")).sendKeys(nazwisko);
    }

    @When("Wpisuję imię dodatkowa grającej w piłkę nożną {string} i nazwisko {string}")
    public void wpisujeImieINazwiskoDodatkowaPilkaNozna(String imie, String nazwisko) {
        driver.findElement(By.cssSelector(".mt-4 > .card > input:nth-child(5)")).sendKeys(imie);
        driver.findElement(By.cssSelector(".mt-4 > .card > input:nth-child(8)")).sendKeys(nazwisko);
    }

    @Then("Formularz jest wypełniony poprawnie")
    public void formularzJestWypelnionyPoprawnie() {
        try {
            // Sprawdzenie wartości w polu Imię osoby uprawiającej sport
            String imieSportowca = driver.findElement(By.cssSelector("li:nth-child(1) > .card > input:nth-child(5)")).getAttribute("value");
            // Sprawdzenie wartości w polu Nazwisko osoby uprawiającej sport
            String nazwiskoSportowca = driver.findElement(By.cssSelector("li:nth-child(1) > .card > input:nth-child(8)")).getAttribute("value");
            // Sprawdzenie wartości w polu Imię dodatkowej osoby grającej w piłkę nożną
            String imieDodatkowe = driver.findElement(By.cssSelector(".mt-4 > .card > input:nth-child(5)")).getAttribute("value");
            // Sprawdzenie wartości w polu Nazwisko dodatkowej osoby grającej w piłkę nożną
            String nazwiskoDodatkowe = driver.findElement(By.cssSelector(".mt-4 > .card > input:nth-child(8)")).getAttribute("value");

            if ("Jan".equals(imieSportowca) && "Kowalski".equals(nazwiskoSportowca)
                    && "Paweł".equals(imieDodatkowe) && "Nowak".equals(nazwiskoDodatkowe)) {
                System.out.println("udało się");
            } else {
                System.out.println("nie udało się");
                throw new AssertionError("Wartości pól nie są zgodne z oczekiwaniami");
            }
        } catch (Exception e) {
            System.out.println("nie udało się");
            throw e;
        }
    }

    @Then("Widzę komunikat sukcesu")
    public void widzeKomunikatSukcesu() {
        // Ten krok pojawi się jako "pass" w raporcie Cucumbera, jeśli test dotrze do tego miejsca
        // Możesz tu dodać dodatkowe logowanie lub asercje, jeśli chcesz
    }
}
