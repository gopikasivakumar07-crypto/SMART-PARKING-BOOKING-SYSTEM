package com.smartparking.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.time.Duration;

public class ParkingSlotTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );

        // Open Smart Parking homepage
        String homePage = Path.of(
                "parking-app",
                "index.html"
        ).toAbsolutePath().toUri().toString();

        System.out.println("Opening: " + homePage);

        driver.get(homePage);
    }

    @Test
    public void parkingSlotSelectionTest() {

        // 1. Click Login
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='login.html']")
                )
        ).click();

        // 2. Enter email
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("email")
                )
        ).sendKeys("user@gmail.com");

        // 3. Enter password
        driver.findElement(
                By.id("password")
        ).sendKeys("password123");

        // 4. Click Login
        driver.findElement(
                By.id("loginButton")
        ).click();

        // 5. Wait for Dashboard
        wait.until(
                ExpectedConditions.urlContains("dashboard.html")
        );

        // 6. Click View Parking Slots
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='parking.html']")
                )
        ).click();

        // 7. Wait for Parking page
        wait.until(
                ExpectedConditions.urlContains("parking.html")
        );

        // 8. Select parking slot P01
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("[data-slot='P01']")
                )
        ).click();

        // 9. Verify P01 is selected
        String selectedSlot =
                driver.findElement(
                        By.cssSelector("[data-slot='P01']")
                ).getAttribute("class");

        Assert.assertTrue(
                selectedSlot.contains("selected"),
                "P01 was not selected"
        );

        System.out.println(
                "======================================"
        );

        System.out.println(
                "PARKING SLOT SELECTION TEST: PASSED"
        );

        System.out.println(
                "Selected Slot: P01"
        );

        System.out.println(
                "======================================"
        );
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}
