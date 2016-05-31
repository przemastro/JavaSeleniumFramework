# javaSeleniumFramework
Java + TestNG + Selenium WebDriver + MySql + Testlink

This is a demo version of framework used for UI testing. Created in the Page Object pattern style. It is prepared for 
https://github.com/przemastro/testApp.git application but obviously can be addapted to any web application.
Please see gettingStarted.txt for more info about testlink setup.
It consists of following:

Java classes

    MainTest - initial class for initializing properties and connecting with DB
    RestFormPage - page object methods
    MainPage - page object methods used in more than one page
    RestForm - tests for rest form page
    Reporter - listeners


Properties file

    functional-automated-tests.properties - file with properties like (url, Chrome binaries and .exe files etc.)


XML file 

    sanity-functional-run - to organize tests execution


DB 

    DB.sql - testlink schema

ChromeDriver

    chromeDriver.exe - be careful with using versions of chromedriver, Chrome binary and selenium

