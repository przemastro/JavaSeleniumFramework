package com.testing.Pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.ITestContext;

public class ObservationsPage extends MainPage {

    private String methodName;

    public ObservationsPage(WebDriver driver) {
        super(driver);
    }

    public ObservationsPage navigateToObservationsFormPage(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='#table-list']"))).click();
        return this;
    }

    public ObservationsPage clickNewObservationButton(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='New Observation']"))).click();
        return this;
    }

    public ObservationsPage clickCloseButtonInModalHeader(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='modal-content']/div/button"))).click();
        return this;
    }

    public ObservationsPage clickCloseButtonInModalFooter(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='modal-footer']/button"))).click();
        return this;
    }

    public ObservationsPage clickYesButtonInModalFooter(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='modal-footer']/button"))).click();
        return this;
    }

    public ObservationsPage clickCloseButtonInRemoveModalFooter(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='modal-footer']/button[2]"))).click();
        return this;
    }

    public ObservationsPage clickSubmitNewObservation(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='modal-footer']/input"))).click();
        return this;
    }

    public ObservationsPage fillInMandatoryFieldsInNewObservation(ITestContext context, String name, String startDate, String endDate) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='name']"))).clear();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='name']"))).sendKeys(name);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='startDate']"))).clear();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='startDate']"))).sendKeys(startDate);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='endDate']"))).clear();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='endDate']"))).sendKeys(endDate);
        return this;
    }

    public ObservationsPage uploadUPhotometryFile(ITestContext context, String uFileName) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(1000);
        String path = System.getProperty("user.dir");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='uFileName']"))).sendKeys(path+"/src/test/resources/"+uFileName);
        return this;
    }

    public ObservationsPage uploadVPhotometryFile(ITestContext context, String vFileName) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(1000);
        String path = System.getProperty("user.dir");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='vFileName']"))).sendKeys(path+"/src/test/resources/"+vFileName);
        return this;
    }

    public ObservationsPage uploadBPhotometryFile(ITestContext context, String bFileName) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(1000);
        String path = System.getProperty("user.dir");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='bFileName']"))).sendKeys(path+"/src/test/resources/"+bFileName);
        return this;
    }

    public ObservationsPage typeSearchedStar(ITestContext context, String starName) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='tableList']/tbody/tr/td[2]/input"))).sendKeys(starName);
        sleep(1000);
        return this;
    }

    public ObservationsPage clickUPhotometry(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@ng-click='editUPhotometry(observation.id)']"))).click();
        return this;
    }

    public ObservationsPage clickVPhotometry(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@ng-click='editVPhotometry(observation.id)']"))).click();
        return this;
    }

    public ObservationsPage clickBPhotometry(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@ng-click='editBPhotometry(observation.id)']"))).click();
        return this;
    }

    public ObservationsPage clickEditButton(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@ng-click='editObservation(observation.id)']"))).click();
        return this;
    }

    public ObservationsPage clickRemoveButton(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@ng-click='removeObservation(observation.id)']"))).click();
        return this;
    }

    public ObservationsPage clickCloseButtonInModalBody(ITestContext context) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@ng-click='cancel()']"))).click();
        return this;
    }

    public ObservationsPage verifyStarIsDisplayed(ITestContext context, String starName) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@ng-click='editObservation(observation.id)']")));
        Assert.assertTrue(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tr[@ng-repeat='observation in displayedObservations']/td"))).getText().contains(starName));
        return this;
    }

    public ObservationsPage verifyStarIsNotDisplayed(ITestContext context, String starName) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@ng-click='editObservation(observation.id)']")));
        Assert.assertFalse(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tr[@ng-repeat='observation in displayedObservations']/td"))).getText().contains(starName));
        return this;
    }

    public ObservationsPage verifyFluxIsDisplayed(ITestContext context, String value) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@ng-click='cancel()']")));
        Assert.assertTrue(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tr[@ng-repeat='observation in ob']/td"))).getText().contains(value));
        return this;
    }

    public ObservationsPage verifyTimeIsDisplayed(ITestContext context, String value) {
        methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        context.setAttribute("method", methodName);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@ng-click='cancel()']")));
        Assert.assertTrue(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//tr[@ng-repeat='observation in ob']/td[2]"))).getText().contains(value));
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
