package com.smartparking.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.time.Duration;

public class BookingTest {

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
    public void bookingFormTest() {

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

        // 6. Open Parking Slots
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='parking.html']")
                )
        ).click();

        // 7. Wait for Parking page
        wait.until(
                ExpectedConditions.urlContains("parking.html")
        );

        // 8. Select P01
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("[data-slot='P01']")
                )
        ).click();

        // 9. Click Book Selected Slot
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("bookButton")
                )
        ).click();

        // 10. Wait for Booking page
        wait.until(
                ExpectedConditions.urlContains("booking.html")
        );

        // 11. Verify selected slot
        String slotNumber =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("slotNumber")
                        )
                ).getText();

        Assert.assertEquals(
                slotNumber,
                "P01",
                "Incorrect parking slot selected"
        );

        // 12. Enter vehicle number
        driver.findElement(
                By.id("vehicleNumber")
        ).sendKeys("TN01AB1234");

        // 13. Select vehicle type
        Select vehicleType =
                new Select(
                        driver.findElement(
                                By.id("vehicleType")
                        )
                );

        vehicleType.selectByVisibleText("Car");

        // 14. Enter parking date
        driver.findElement(
                By.id("parkingDate")
        ).sendKeys("30/09/2026");

        // 15. Enter parking time
        driver.findElement(
                By.id("parkingTime")
        ).sendKeys("10:00AM");

        // 16. Select parking duration = 2 hours
        Select duration =
                new Select(
                        driver.findElement(
                                By.id("duration")
                        )
                );

        duration.selectByValue("2");

        // 17. Verify calculated fee
        String fee =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("fee")
                        )
                ).getText();

        Assert.assertEquals(
                fee,
                "₹100",
                "Incorrect parking fee calculated"
        );

        System.out.println(
                "======================================"
        );

        System.out.println(
                "BOOKING FORM TEST: PASSED"
        );

        System.out.println(
                "Selected Slot: " + slotNumber
        );

        System.out.println(
                "Vehicle Number: TN01AB1234"
        );

        System.out.println(
                "Vehicle Type: Car"
        );

        System.out.println(
                "Duration: 2 Hours"
        );

        System.out.println(
                "Calculated Fee: " + fee
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
