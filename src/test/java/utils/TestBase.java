package utils;
import com.beust.jcommander.ParameterException;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import jdk.nashorn.internal.ir.FunctionCall;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestBase {
    public static WebDriver driver;
    public static WebDriverWait wait;
    public static String buildNumber;
    public static String testName;
    protected static ExtentTest test;
    private final String description = "The Extent report of Test";
    public static String envFlag ;
    public static String browserFlag;
    public static CustomizeAssertion customizeAssertion;

    ChromeOptions chromeOptions = new ChromeOptions();

    @BeforeSuite
    public void generateBuildNumber(){
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(date);
        buildNumber = time;
    }

    @BeforeSuite(alwaysRun = true)
    public void clearFolderFile(){
//        Functions.deleteOldScreenshot();
    }

    /**
     * browser: Chrome, FireFox
     * encironment: DEV, DEVX, UAT
     * @param browser
     * @param environment
     */

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser","environment"})

    public void setupBrowser (@Optional("Chrome") String browser, @Optional("UAT") String environment ){
       envFlag = environment;
       browserFlag = browser;
       switch (browser){
           case "Chrome":{
               setupChrome();
               break;
           }
           case "Firefox":{
               setupFirefox();
               break;
           }
           default:
               throw new ParameterException(browser+"is not a valid browser option");
       }

       //Get the test name
        String testCaseFullName = this.getClass().getName();
       testName = (testCaseFullName.split("\\.")[testCaseFullName.split("\\.").length-1]);

       System.out.println("Run in Browser: " +browserFlag);
       System.out.println("Current test case name: " + testName);
       System.out.println("Run for environment: "+envFlag);

       test = ReportFactory.getTest(testName,envFlag,description);

       //Create a wait and maximize window, suggest all test classes to use this
        wait = new WebDriverWait(driver,60);
        driver.manage().window().maximize();

    }


    public void setupChrome(){
        chromeOptions.setExperimentalOption("useAutomationExtension",false);

        chromeOptions.addArguments("--disable-features=VizDisplayCompositor");
//        chromeOptions.setBinary("C:\\Users\\herby\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");//指定本地的chrome浏览器，否则jenkins会报错：cannot find chrome binary
//        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\lib\\chromedriver.exe");

        System.setProperty("webdriver.chrome.driver","C:\\Users\\herby\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver246666.exe");

        driver = new ChromeDriver(chromeOptions);
    }

    public void setupFirefox(){
        System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"XXX");
        driver = new FirefoxDriver();
    }



    @AfterMethod(alwaysRun = true)
    public void teardown () {
        try {
            //close the process of browser
            driver.close();
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method caller, ITestResult iTestResult){
        ReportFactory.closeTest(testName);
        if(iTestResult.getThrowable()!=null && !iTestResult.getThrowable().toString().contains("java.lang.AssertionError")){
            test.log(LogStatus.FAIL,"Exception Type: " + iTestResult.getThrowable().getMessage());
            String path = customizeAssertion.errorScreenshot((TakesScreenshot)driver);
            customizeAssertion.printErrorMsg(path, "screenshot");
        }
    }


    @AfterSuite(alwaysRun = true)
    public void afterSuite() throws IOException {
        ReportFactory.closeReport();
    }

    public void eventualAssert(){
        test.log(LogStatus.INFO,"");
        Assert.assertEquals(ReportFactory.getTest(testName).getRunStatus().toString(),LogStatus.PASS.toString(),"The test case status is: " + ReportFactory.getTest(testName).getRunStatus().toString());
    }
}