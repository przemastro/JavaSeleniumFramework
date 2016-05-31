package com.testing.Tests;

import com.testing.Pages.MainTest;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import com.testing.Pages.RestFormPage;

public class RestForm extends MainTest {

    private RestFormPage restFormPage;

    @Test
    public void addNewEmployee(ITestContext context) throws InterruptedException {
        restFormPage = new RestFormPage(driver)
                .navigateToRestFormPage(context)
                .fillInMandatoryFields(context, "Crazy", "Horse", "18591010", "Dakota", "123434", "2333")
                .clickSubmitButton(context)
                .verifyEmployeeIsDisplayed(context, "Crazy", "Horse");
    }
}