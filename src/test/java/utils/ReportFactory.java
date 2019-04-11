package utils;


import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ReportFactory extends TestBase {

    public static ExtentReports reporter;
    public static Map<Long, String> threadToExtentTestMap = new HashMap<Long, String>();
    public static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();

    private synchronized static ExtentReports getExtentReport() {
        if (reporter == null) {
            init();
            reporter = new ExtentReports("./test-output/ExtentReport_" +buildNumber+ ".html", true);

        }
        return reporter;
    }

    public synchronized static ExtentTest getTest(String testName) {
        return getTest(testName, "", "");
    }

    public synchronized static ExtentTest getTest(String testName, String categoryName, String testDescription) {

        // if this test has already been created return
        if (!nameToTestMap.containsKey(testName)) {
            Long threadID = Thread.currentThread().getId();
            ExtentTest test = getExtentReport().startTest(testName, testDescription);
            test.assignCategory(categoryName);
            nameToTestMap.put(testName, test);
            threadToExtentTestMap.put(threadID, testName);
        }
        return nameToTestMap.get(testName);
    }

    public synchronized static ExtentTest getTest() {
        Long threadID = Thread.currentThread().getId();

        if (threadToExtentTestMap.containsKey(threadID)) {
            String testName = threadToExtentTestMap.get(threadID);
            return nameToTestMap.get(testName);
        }
        //system log, this shouldn't happen but in this crazy times if it did happen log it.
        return null;
    }

    public synchronized static void closeTest(String testName) {

        if (!testName.isEmpty()) {
            ExtentTest test = getTest(testName);
            getExtentReport().endTest(test);
        }
    }

    public synchronized static void closeTest(ExtentTest test) {
        if (test != null) {
            getExtentReport().endTest(test);
        }
    }

    public synchronized static void closeTest() {
        ExtentTest test = getTest();
        closeTest(test);
    }

    public synchronized static void closeReport() throws IOException {
        if (reporter != null) {
            reporter.endTest(test);
            reporter.flush();
            reporter.close();
//            File currentReport = new File("/test-output/ExtentReport" + ".html");
//            FileUtils.copyFile(currentReport, new File("/test-output/ExtentReport.html"));
        }
    }

    private static void init() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("./test-output/ExtentReport_"+buildNumber + ".html");
        htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS); //解决报告没有css样式的问题
        htmlReporter.config().setDocumentTitle("ExtentReports - Created by TestNG Listener");
        htmlReporter.config().setReportName("ExtentReports - Created by TestNG Listener");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setTheme(Theme.STANDARD);

    }



}
