package com.smartparking.tests;

import org.openqa.selenium.Alert;
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

public class CancelBookingTest {

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
    public void cancelBookingTest() {

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

        // 7. Enter vehicle number
        driver.findElement(
                By.id("vehicleNumber")
        ).sendKeys("TN01AB1234");

        // 8. Select vehicle type
        Select vehicleType =
                new Select(
                        driver.findElement(
                                By.id("vehicleType")
                        )
                );

        vehicleType.selectByVisibleText("Car");

        // 9. Enter date
        driver.findElement(
                By.id("parkingDate")
        ).sendKeys("30/09/2026");

        // 10. Enter time
        driver.findElement(
                By.id("parkingTime")
        ).sendKeys("10:00AM");

        // 11. Select duration
        Select duration =
                new Select(
                        driver.findElement(
                                By.id("duration")
                        )
                );

        duration.selectByValue("2");

        // 12. Confirm booking
        driver.findElement(
                By.id("confirmBookingButton")
        ).click();

        // 13. Wait for successful booking message
        wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        By.id("bookingMessage"),
                        "Booking successful!"
                )
        );

        // 14. Wait for My Bookings page
        wait.until(
                ExpectedConditions.urlContains("history.html")
        );

        // 15. Verify booking exists
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("cancelBookingButton")
                )
        );

        // 16. Click Cancel Booking
        driver.findElement(
                By.id("cancelBookingButton")
        ).click();

        // 17. Handle confirmation popup
        Alert alert =
                wait.until(
                        ExpectedConditions.alertIsPresent()
                );

        String alertMessage = alert.getText();

        Assert.assertEquals(
                alertMessage,
                "Are you sure you want to cancel this booking?",
                "Incorrect cancellation confirmation message"
        );

        // Accept the confirmation
        alert.accept();

// 18. Handle the second alert
        Alert successAlert =
                wait.until(
                        ExpectedConditions.alertIsPresent()
                );

        String successMessage =
                successAlert.getText();

        Assert.assertEquals(
                successMessage,
                "Booking cancelled successfully.",
                "Incorrect cancellation success message"
        );

// Close success alert
        successAlert.accept();

// 19. Verify booking was removed
        wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        By.id("bookingContainer"),
                        "No Bookings Found"
                )
        );

        String pageContent =
                driver.findElement(
                        By.id("bookingContainer")
                ).getText();

        Assert.assertTrue(
                pageContent.contains("No Bookings Found"),
                "Booking was not cancelled"
        );

        System.out.println(
                "======================================"
        );

        System.out.println(
                "CANCEL BOOKING TEST: PASSED"
        );

        System.out.println(
                "Cancellation Confirmation: VERIFIED"
        );

        System.out.println(
                "Booking Removed: VERIFIED"
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