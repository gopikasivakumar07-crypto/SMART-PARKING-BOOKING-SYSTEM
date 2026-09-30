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

public class BookingConfirmationTest {

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

        String homePage = Path.of(
                "parking-app",
                "index.html"
        ).toAbsolutePath().toUri().toString();

        driver.get(homePage);
    }

    @Test
    public void bookingConfirmationTest() {

        // 1. Login
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='login.html']")
                )
        ).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("email")
                )
        ).sendKeys("user@gmail.com");

        driver.findElement(
                By.id("password")
        ).sendKeys("password123");

        driver.findElement(
                By.id("loginButton")
        ).click();

        // 2. Wait for Dashboard
        wait.until(
                ExpectedConditions.urlContains("dashboard.html")
        );

        // 3. Open Parking Slots
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='parking.html']")
                )
        ).click();

        wait.until(
                ExpectedConditions.urlContains("parking.html")
        );

        // 4. Select P01
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("[data-slot='P01']")
                )
        ).click();

        // 5. Click Book Selected Slot
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("bookButton")
                )
        ).click();

        // 6. Wait for Booking page
        wait.until(
                ExpectedConditions.urlContains("booking.html")
        );

        // 7. Verify selected slot
        String slotNumber =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("slotNumber")
                        )
                ).getText();

        Assert.assertEquals(
                slotNumber,
                "P01",
                "Incorrect parking slot"
        );

        // 8. Enter vehicle number
        driver.findElement(
                By.id("vehicleNumber")
        ).sendKeys("TN01AB1234");

        // 9. Select vehicle type
        Select vehicleType =
                new Select(
                        driver.findElement(
                                By.id("vehicleType")
                        )
                );

        vehicleType.selectByVisibleText("Car");

        // 10. Enter parking date
        driver.findElement(
                By.id("parkingDate")
        ).sendKeys("30/09/2026");

        // 11. Enter parking time
        driver.findElement(
                By.id("parkingTime")
        ).sendKeys("10:00AM");

        // 12. Select 2-hour duration
        Select duration =
                new Select(
                        driver.findElement(
                                By.id("duration")
                        )
                );

        duration.selectByValue("2");

        // 13. Verify fee
        String fee =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("fee")
                        )
                ).getText();

        Assert.assertEquals(
                fee,
                "₹100",
                "Incorrect fee"
        );

        // 14. Confirm booking
        driver.findElement(
                By.id("confirmBookingButton")
        ).click();

        // 15. Verify success message
        String bookingMessage =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("bookingMessage")
                        )
                ).getText();

        Assert.assertEquals(
                bookingMessage,
                "Booking successful!",
                "Booking was not successful"
        );

        // 16. Wait for History page
        wait.until(
                ExpectedConditions.urlContains("history.html")
        );

        // 17. Verify booking status
        String status =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("bookingStatus")
                        )
                ).getText();

        Assert.assertEquals(
                status,
                "CONFIRMED",
                "Booking is not confirmed"
        );

        // 18. Verify parking slot in history
        String historySlot =
                driver.findElement(
                        By.id("historySlot")
                ).getText();

        Assert.assertEquals(
                historySlot,
                "P01",
                "Incorrect slot displayed in booking history"
        );

        // 19. Verify vehicle number
        String historyVehicle =
                driver.findElement(
                        By.id("historyVehicle")
                ).getText();

        Assert.assertEquals(
                historyVehicle,
                "TN01AB1234",
                "Incorrect vehicle number displayed"
        );

        System.out.println(
                "======================================"
        );

        System.out.println(
                "BOOKING CONFIRMATION TEST: PASSED"
        );

        System.out.println(
                "Booking Status: " + status
        );

        System.out.println(
                "Parking Slot: " + historySlot
        );

        System.out.println(
                "Vehicle Number: " + historyVehicle
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