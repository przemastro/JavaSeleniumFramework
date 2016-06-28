package com.testing.Pages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.*;

public class MainTest {

    protected WebDriver driver;
    protected String url;
    private String chromeDriver;
    private String chromeBinary;

    protected FirefoxProfile firefoxProfile;
    private String path;
    public String pathResources;

    public String dateValue;
    public String nameBuild;
    public static final Date date = new Date();
    public String methodName;
    public Connection connection;
    public PreparedStatement preparedStatement;
    public Statement stmt;
    //public static final boolean enableTestLink = false;

    public MainTest() {
        initializeProperties();
    }

    private void initializeProperties() {
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("functional-automated-tests.properties");
        try {
            prop.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (System.getenv("URL") == null) {
            url = prop.getProperty("functional-automated-tests.url");
        } else {
            url = System.getenv("URL");
        }
        path = prop.getProperty("mainTest.downloadPath");

        chromeDriver = prop.getProperty("functional-automated-tests.ChromeDriverPath");
        chromeBinary = prop.getProperty("functional-automated-tests.ChromeBinaryPath", "C:\\\\Program Files (x86)\\\\Google\\\\Chrome\\\\Application\\chrome.exe");
        pathResources = prop.getProperty("mainTest.pathResources");
        System.out.println(pathResources);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeClass() {


        //beforeFirefox();
        beforeChrome();
        System.out.println("url=" + url);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(MainPage.DEFAULT_WAIT_FOR_PAGE_LOAD, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(MainPage.DEFAULT_WAIT_FOR_PAGE_LOAD, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(MainPage.DEFAULT_WAIT_FOR_PAGE_LOAD, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();

        driver.get(url);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        driver.get(url);
        System.out.println("Test method being run: " + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void afterClass() {
        driver.close();
        driver.quit();
    }

    //@BeforeSuite(enabled = true)
    public void startFile() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        dateValue = dateFormat.format(date);

        nameBuild = dateValue;
        String url = "jdbc:mysql://localhost:3306/testlink";
        try {
            System.out.println("Connecting database...");
            connection = DriverManager.getConnection(url, "admin", "admin");
            System.out.println("Database connected!");

            insertNewBuildNameToBuildsSQL();
            dropAndCreateTestPlanTCVersionsSQL();

        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect the database!", e);
        } finally {
            System.out.println("Closing the connection.");
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }

    public void insertNewBuildNameToBuildsSQL() throws SQLException {
        String insertNewBuildToBuilds = "insert into builds (testplan_id,name,notes,active,is_open,author_id,creation_ts,release_date,closed_on_date)"
                + "values ((select id from testplans),?,null,1,1,null,sysdate(),sysdate(),null);";

        preparedStatement = connection.prepareStatement(insertNewBuildToBuilds);
        preparedStatement.setString(1, "Build " + nameBuild);

        preparedStatement.execute();
        System.out.println("Build " + nameBuild + " has been created");
    }

    public void dropAndCreateTestPlanTCVersionsSQL() throws SQLException {
        String dropTesplanTcversions = "DROP TABLE IF EXISTS `testplan_tcversions`;";

        stmt = connection.createStatement();
        stmt.executeUpdate(dropTesplanTcversions);

        String createTesplanTcversions = "CREATE TABLE `testplan_tcversions` ("
                + "`id` int(10) unsigned NOT NULL AUTO_INCREMENT,"
                + "`testplan_id` int(10) unsigned NOT NULL DEFAULT '0',"
                + "`tcversion_id` int(10) unsigned NOT NULL DEFAULT '0',"
                + "`node_order` int(10) unsigned NOT NULL DEFAULT '1',"
                + "`urgency` smallint(5) NOT NULL DEFAULT '2',"
                + "`platform_id` int(10) unsigned NOT NULL DEFAULT '0',"
                + "`author_id` int(10) unsigned DEFAULT NULL,"
                + "`creation_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "PRIMARY KEY (`id`),"
                + "UNIQUE KEY `testplan_tcversions_tplan_tcversion` (`testplan_id`,`tcversion_id`,`platform_id`)"
                + ") ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;";

        stmt = connection.createStatement();
        stmt.executeUpdate(createTesplanTcversions);
        System.out.println("testplan_tcversions table has been created");
    }

    public static String executeCommand(String command) {

        StringBuffer output = new StringBuffer();
        Process process;

        try {
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public void beforeFirefox() {
        FirefoxProfile profile = new FirefoxProfile();
        driver = new FirefoxDriver();
    }

    public void beforeChrome() {
        System.setProperty("webdriver.chrome.driver", chromeDriver);

        ChromeOptions options = new ChromeOptions();
        options.setBinary(chromeBinary);

        options.addArguments("--disable-extensions");

        driver = new ChromeDriver(options);
    }


    //@AfterSuite
    public static String recreateDatabase() {
        String fileLocation = "src\\test\\resources\\create_schema.bat";
        String status = MainTest.executeCommand(fileLocation);
        System.out.println(status);
        return status;
    }

}
