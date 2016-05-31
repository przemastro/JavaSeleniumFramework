package com.testing.Pages;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.server.browserlaunchers.Sleeper;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;

public abstract class MainPage {

    public static final int DEFAULT_WAIT_FOR_PAGE_LOAD = 5;
    public static final int DEFAULT_WAIT_FOR_AJAX_LOAD = 5;
    public static final int DEFAULT_WAIT_FOR_SCRIPT_MS = 8000;
    protected final WebDriver driver;
    private String methodName;


    public MainPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE_LOAD, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(DEFAULT_WAIT_FOR_PAGE_LOAD, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(DEFAULT_WAIT_FOR_PAGE_LOAD, TimeUnit.SECONDS);
    }

    public void waitForAjaxComplete() {
        final JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        do {
            Sleeper.sleepTight(DEFAULT_WAIT_FOR_SCRIPT_MS);
        } while (!(Boolean) javascriptExecutor.executeScript("return jQuery.active == 0"));
    }

    public void waitForPageLoadComplete() {
        final JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        do {
            Sleeper.sleepTight(DEFAULT_WAIT_FOR_SCRIPT_MS);
        } while (!javascriptExecutor.executeScript("return document.readyState").equals("complete"));
    }

    public static boolean areElementsPresent(WebDriver driver, By by) {
        try {
            driver.findElements(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean isTextPresent(WebDriver driver, By by, String text) {
        try {
            return driver.findElement(by).getText().contains(text);
        } catch (NullPointerException e) {
            return false;
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

    public static WebElement waitForElementVisible(WebDriver driver, final By by, int timeOutInSeconds) {
        WebElement element;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));

            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE_LOAD, TimeUnit.SECONDS);

            return element;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public static WebElement waitForElementPresent(WebDriver driver, final By by, int timeOutInSeconds) {
        WebElement element;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));

            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE_LOAD, TimeUnit.SECONDS);
            return element;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public static void waitForElementNotPresent(WebDriver driver, final By by, int timeOutInSeconds) {
        WebElement element;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));

            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE_LOAD, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static List<WebElement> waitForListElementsPresent(WebDriver driver, final By by, int timeOutInSeconds) {
        List<WebElement> elements;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.until((new ExpectedCondition<Boolean>() {
                //@Override
                public Boolean apply(WebDriver driverObject) {
                    return areElementsPresent(driverObject, by);
                }
            }));

            elements = driver.findElements(by);
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE_LOAD, TimeUnit.SECONDS);
            return elements;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return Collections.emptyList();
    }

    public static boolean waitForTextPresent(WebDriver driver, final By by, final String text, int timeOutInSeconds) {
        boolean isPresent = false;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            isPresent = new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {

                //@Override
                public Boolean apply(WebDriver driverObject) {
                    return isTextPresent(driverObject, by, text);
                }
            });
            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_FOR_PAGE_LOAD, TimeUnit.SECONDS);
            return isPresent;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

}
