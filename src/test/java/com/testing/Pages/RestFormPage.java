package com.testing.Pages;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.ITestContext;

public class RestFormPage extends MainPage {

    private String methodName;

    public RestFormPage(WebDriver driver) {
        super(driver);
    }

    public RestFormPage navigateToRestFormPage(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//body[@ng-controller='mainCtrl']/ul/li[3]"))).click();
        //driver.findElement(By.xpath("//body[@ng-controller='mainCtrl']/ul/li[3]")).click();
        return this;
    }

    public RestFormPage fillInMandatoryFields(ITestContext context, String name, String lastName, String dateOfBirth,
                                              String address, String grossIncome, String netIncome) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='name']"))).sendKeys(name);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='lastName']"))).sendKeys(lastName);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='dateOfBirth']"))).sendKeys(dateOfBirth);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='address']"))).sendKeys(address);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='grossIncome']"))).sendKeys(grossIncome);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='netIncome']"))).sendKeys(netIncome);
        return this;
    }

    public RestFormPage clickSubmitButton(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='Submit']"))).click();
        return this;
    }

    public RestFormPage verifyEmployeeIsDisplayed(ITestContext context, String name, String lastName) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        int i = 1;
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@class='insideTab']/tbody/tr[2]/td[3]/input")));
        while (true) {
            try {
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                Assert.assertTrue(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@class='insideTab']/tbody/tr[" + i + "]/td[1]"))).getText().contains(name));
                Assert.assertTrue(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@class='insideTab']/tbody/tr[" + i + "]/td[2]"))).getText().contains(lastName));
                i++;
            } catch (Exception e) {
                break;
            }
        }
        return this;
    }

    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("got interrupted!");
        }
    }

    public void scrollTop(int length) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        for (int second = 0;; second++) {
            if (second >= 1) {
                break;
            }
            jse.executeScript("scroll(" + length + ", 0)");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("got interrupted!");
            }
        }
    }

    public void scrollBottom(int length) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        for (int second = 0;; second++) {
            if (second >= 1) {
                break;
            }
            jse.executeScript("window.scrollBy(0," + length + ")", "");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("got interrupted!");
            }
        }
    }
}
