package utils;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import utilities.Log;

public class CustomizeAssertion extends TestBase {
    String errorImagePath = ".\\test-output\\errorScreenshot";
    String returnPath = ".\\errorScreenshot";

    public boolean assertTrue(boolean condition, String message){
        try{
            Assert.assertTrue(condition,message);
            return true;

        }catch (AssertionError e){
            String path = errorScreenshot((TakesScreenshot)driver);
            printErrorMsg(path,message);
            return false;
        }

    }

    public boolean assertFalse(boolean condition, String message){
        try{
            Assert.assertFalse(condition, message);
            return false;
        }catch (AssertionError e){
            String path = errorScreenshot((TakesScreenshot)driver);
            printErrorMsg(path,message);
            return true;
        }
    }

    public boolean assertEquals(String actual, String expected, String message){
        try{
            Assert.assertEquals(actual,expected,message);
            return true;
        }catch (AssertionError e){
            String path = errorScreenshot((TakesScreenshot)driver);
            printErrorMsg(path,message,actual,expected);
            return false;
        }

    }


    public boolean assertNotEquals(String actual, String expected, String message){
        try{
            Assert.assertNotEquals(actual, expected, message);
            return true;
        }catch (AssertionError e){
            String path = errorScreenshot((TakesScreenshot)driver);
            printErrorMsg(path, message,actual,expected);
            return false;
        }
    }



    public String errorScreenshot(TakesScreenshot name){

        File file = name.getScreenshotAs(OutputType.FILE);
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = format.format(date);

        File path = new File(errorImagePath);
        String absolutePath = path.getAbsolutePath() + "\\" +timestamp +".png";

        try {
            System.out.println("Save error screenshot path is: " +errorImagePath);
            FileUtils.copyFile(file, new File(absolutePath));
        }catch (IOException e){
            System.out.println("Cannot save screenshot");
            return "";
        }finally {
            return returnPath+"\\"+timestamp +".png";
        }

    }


    public void printErrorMsg(String path, String message, String actual, String expected){
        Log.error(message+path);
        test.log(LogStatus.FAIL, "\n Actual = " +actual+"  Expected = "+ expected + test.addScreenCapture(path));

    }

    public void printErrorMsg(String path, String message){
        test.log(LogStatus.FAIL, message + path);
        Log.error(message);
        test.log(LogStatus.FAIL, test.addScreenCapture(path));
    }
}
