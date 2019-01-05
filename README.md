# JavaSeleniumFramework
Java + TestNG + Selenium WebDriver + MySql + Testlink

This is a demo version of a framework I used for UI testing of GFT internal application developed around 2014. 
Created in the Page Object pattern style. It is prepared for https://github.com/przemastro/astroApp.git application but obviously can be addapted to any web application. Please see gettingStarted.txt for more info about testlink setup.
It consists of following:

Java classes

    MainTest 
    AdminPanelPage 
    ObservationsPage
    MainPage
    Observations
    Reporter - listeners


Properties file

    functional-automated-tests.properties


XML file 

    sanity-functional-run


DB 

    DB.sql - testlink schema

ChromeDriver

    chromeDriver.exe - be careful with using versions of chromedriver, Chrome binary and selenium

