package com.smartparking.tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Field;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        System.out.println(
                "TEST FAILED - CAPTURING SCREENSHOT..."
        );

        try {

            Object testObject =
                    result.getInstance();

            Field driverField =
                    testObject
                            .getClass()
                            .getDeclaredField("driver");

            driverField.setAccessible(true);

            WebDriver driver =
                    (WebDriver) driverField.get(testObject);

            ScreenshotUtil.takeScreenshot(
                    driver,
                    result.getName()
            );

        } catch (Exception e) {

            System.out.println(
                    "LISTENER ERROR:"
            );

            e.printStackTrace();
        }
    }
}