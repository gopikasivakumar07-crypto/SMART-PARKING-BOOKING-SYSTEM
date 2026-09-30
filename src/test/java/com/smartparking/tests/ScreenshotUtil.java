package com.smartparking.tests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtil {

    public static void takeScreenshot(
            WebDriver driver,
            String testName
    ) {

        try {

            if (driver == null) {
                System.out.println("Driver is NULL. Screenshot not taken.");
                return;
            }

            File source =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            Path screenshotFolder =
                    Path.of(
                            System.getProperty("user.dir"),
                            "screenshots"
                    );

            Files.createDirectories(screenshotFolder);

            Path destination =
                    screenshotFolder.resolve(
                            testName + ".png"
                    );

            Files.copy(
                    source.toPath(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "SCREENSHOT SAVED SUCCESSFULLY"
            );

            System.out.println(
                    "Location: "
                            + destination.toAbsolutePath()
            );

            System.out.println(
                    "======================================"
            );

        } catch (Exception e) {

            System.out.println(
                    "======================================"
            );

            System.out.println(
                    "SCREENSHOT FAILED"
            );

            e.printStackTrace();

            System.out.println(
                    "======================================"
            );
        }
    }
}