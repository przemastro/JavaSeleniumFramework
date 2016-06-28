package com.testing.Tests;

import com.testing.Pages.AdminPanelPage;
import com.testing.Pages.MainTest;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import com.testing.Pages.ObservationsPage;

public class Observations extends MainTest {

    private ObservationsPage observationsPage;
    private AdminPanelPage adminPanelPage;

    @Test
    public void addNewObservation(ITestContext context) throws InterruptedException {
        observationsPage = new ObservationsPage(driver)
                .navigateToObservationsFormPage(context)
                .clickNewObservationButton(context)
                .clickCloseButtonInModalHeader(context)
                .clickNewObservationButton(context)
                .clickCloseButtonInModalFooter(context)
                .clickNewObservationButton(context)
                .clickSubmitNewObservation(context)
                .fillInMandatoryFieldsInNewObservation(context, "SYEqu", "20160505", "20160506")
                .uploadUPhotometryFile(context, "SYEqu-uPhotometry-extended.csv")
                .uploadVPhotometryFile(context, "SYEqu-vPhotometry-extended.csv")
                .uploadBPhotometryFile(context, "SYEqu-bPhotometry-extended.csv")
                .clickSubmitNewObservation(context);

        adminPanelPage = new AdminPanelPage(driver)
                .navigateToAdminPanelPage(context)
                .clickProcessButton(context);

        observationsPage = new ObservationsPage(driver)
                .navigateToObservationsFormPage(context)
                .typeSearchedStar(context, "SYEqu")
                .verifyStarIsDisplayed(context, "SYEqu");
    }

    @Test
    public void editUVBData(ITestContext context) throws InterruptedException {
        observationsPage = new ObservationsPage(driver)
                .navigateToObservationsFormPage(context)
                .typeSearchedStar(context,"Vega")
                .clickUPhotometry(context)
                .verifyFluxIsDisplayed(context,"0.58200")
                .verifyTimeIsDisplayed(context,"2720.81478")
                .clickCloseButtonInModalBody(context)
                .clickVPhotometry(context)
                .verifyFluxIsDisplayed(context,"0.58200")
                .verifyTimeIsDisplayed(context,"2720.81478")
                .clickCloseButtonInModalBody(context)
                .clickBPhotometry(context)
                .verifyFluxIsDisplayed(context,"0.58200")
                .verifyTimeIsDisplayed(context,"2720.81478")
                .clickCloseButtonInModalBody(context);
    }

    @Test
    public void editObservation(ITestContext context) throws InterruptedException {
        observationsPage = new ObservationsPage(driver)
                .navigateToObservationsFormPage(context)
                .typeSearchedStar(context,"Beta Cephei")
                .clickEditButton(context)
                .fillInMandatoryFieldsInNewObservation(context, "Alpha Cephei", "20160505", "20160506")
                .uploadUPhotometryFile(context, "SYEqu-uPhotometry-extended.csv")
                .uploadVPhotometryFile(context, "SYEqu-vPhotometry-extended.csv")
                .uploadBPhotometryFile(context, "SYEqu-bPhotometry-extended.csv")
                .clickSubmitNewObservation(context);

        adminPanelPage = new AdminPanelPage(driver)
                .navigateToAdminPanelPage(context)
                .clickProcessButton(context);

        observationsPage = new ObservationsPage(driver)
                .navigateToObservationsFormPage(context)
                .typeSearchedStar(context, "Alpha Cephei")
                .verifyStarIsDisplayed(context, "Alpha Cephei")
                .clickEditButton(context)
                .fillInMandatoryFieldsInNewObservation(context, "Beta Cephei", "20160505", "20160506")
                .uploadUPhotometryFile(context, "SYEqu-uPhotometry-extended.csv")
                .uploadVPhotometryFile(context, "SYEqu-vPhotometry-extended.csv")
                .uploadBPhotometryFile(context, "SYEqu-bPhotometry-extended.csv")
                .clickSubmitNewObservation(context);

        adminPanelPage = new AdminPanelPage(driver)
                .navigateToAdminPanelPage(context)
                .clickProcessButton(context);

        observationsPage = new ObservationsPage(driver)
                .navigateToObservationsFormPage(context)
                .typeSearchedStar(context, "Beta Cephei")
                .verifyStarIsDisplayed(context, "Beta Cephei");
    }

    @Test
    public void removeObservation(ITestContext context) throws InterruptedException {
        observationsPage = new ObservationsPage(driver)
                .navigateToObservationsFormPage(context)
                .typeSearchedStar(context,"SYEqu")
                .clickRemoveButton(context)
                .clickCloseButtonInRemoveModalFooter(context)
                .clickRemoveButton(context)
                .clickYesButtonInModalFooter(context);

        adminPanelPage = new AdminPanelPage(driver)
                .navigateToAdminPanelPage(context)
                .clickProcessButton(context);

        observationsPage = new ObservationsPage(driver)
                .navigateToObservationsFormPage(context)
                .typeSearchedStar(context, "SYEqu");
    }

}