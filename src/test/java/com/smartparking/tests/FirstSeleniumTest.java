package com.smartparking.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FirstSeleniumTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void openGoogleTest() {

        driver.get("https://www.google.com");

        String title = driver.getTitle();

        System.out.println("Page Title: " + title);

        Assert.assertTrue(title.contains("Google"));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}