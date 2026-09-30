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

public class LoginTest {

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

        // Open the Smart Parking homepage directly
        String homePage = Path.of(
                "parking-app",
                "index.html"
        ).toAbsolutePath().toUri().toString();

        System.out.println("Opening: " + homePage);

        driver.get(homePage);
    }


    @Test
    public void validLoginTest() {

        // Verify homepage loaded
        Assert.assertTrue(
                driver.getTitle().contains("Smart Parking"),
                "Smart Parking homepage did not load"
        );


        // Click Login
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='login.html']")
                )
        ).click();


        // Enter email
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("email")
                )
        ).sendKeys("user@gmail.com");


        // Enter password
        driver.findElement(
                By.id("password")
        ).sendKeys("password123");


        // Click Login
        driver.findElement(
                By.id("loginButton")
        ).click();


        // Wait for dashboard
        wait.until(
                ExpectedConditions.urlContains("dashboard.html")
        );


        // Verify dashboard
        Assert.assertTrue(
                driver.getCurrentUrl().contains("dashboard.html"),
                "Dashboard was not opened after login"
        );


        System.out.println(
                "======================================"
        );

        System.out.println(
                "VALID LOGIN TEST: PASSED"
        );

        System.out.println(
                "Dashboard URL: " + driver.getCurrentUrl()
        );

        System.out.println(
                "======================================"
        );
    }
    @Test
    public void invalidLoginTest() {

        // Click Login on homepage
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='login.html']")
                )
        ).click();


        // Enter valid email
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("email")
                )
        ).sendKeys("user@gmail.com");


        // Enter WRONG password
        driver.findElement(
                By.id("password")
        ).sendKeys("wrongpassword");


        // Click Login
        driver.findElement(
                By.id("loginButton")
        ).click();


        // Wait for error message
        String errorMessage =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("loginMessage")
                        )
                ).getText();


        // Verify error message
        Assert.assertEquals(
                errorMessage,
                "Invalid email or password.",
                "Incorrect error message displayed"
        );


        System.out.println(
                "INVALID LOGIN TEST: PASSED"
        );
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}