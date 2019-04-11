package utils;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utilities.Log;

import javax.jws.WebResult;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class CustomizeFunctions extends TestBase {

    /**
     * Click on the element
     * @param element
     * @param description
     * @author He, Ying
     */
    public void click(WebElement element, String description){
        Log.info(description);
        test.log(LogStatus.INFO,description);

        try{
            waitForElementAppear(element);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        element.click();
    }


    /**
     * Double click on the element
     * @param element
     * @param description
     * @author He, ying
     */
    public void doubleClick(WebElement element, String description){
        Log.info(description);
        test.log(LogStatus.INFO,description);
        org.openqa.selenium.interactions.Actions action = new org.openqa.selenium.interactions.Actions(driver);
        action.doubleClick(element).build().perform();
    }


    /**
     * Use javascripts to click on the element
     * @param element
     * @param description
     */
    public void clickByJavascript(WebElement element, String description){
        Log.info(description);
        test.log(LogStatus.INFO,description);

        try{
            if(element.isEnabled() && element.isDisplayed()){
                ((JavascriptExecutor)driver).executeScript("arguments[0].click();",element);
            }

        }catch (StaleElementReferenceException e){
            System.out.println("Element was not found in DOM "+ e.getStackTrace());
        }catch (NoSuchElementException e){
            System.out.println("Element is not exist");
        }catch (Exception e){
            System.out.println("Unable to click on element " + e.getStackTrace());
        }
    }


    /**
     * Input function
     * @param element
     * @param sendData
     * @param description
     * @author He, ying
     */
    public void input(WebElement element, String sendData, String description){
        Log.info(description);
        test.log(LogStatus.INFO,description);
        try{
            waitForElementAppear(element);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        element.sendKeys(sendData);
    }

    public void scrollToElement(WebElement element, String description){
        Log.info(description);
        test.log(LogStatus.INFO,description);
        try{
            if(element.isEnabled() && element.isDisplayed()){
                ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",element);
            }

        }catch (NoSuchElementException e){
            System.out.println("Element not exist " + e.getStackTrace());
        }
    }

    public void scrollToBottom(){
        Log.info("Scroll to the bottom of page");
        test.log(LogStatus.INFO,"Scroll to the bottom of page");
        JavascriptExecutor  js = ((JavascriptExecutor)driver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void mouseHoverOver(WebElement element, String description) throws InterruptedException {
        Log.info(description);
        test.log(LogStatus.INFO,description);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
        Thread.sleep(1000);
    }

    public void scrollToTop(){
        Log.info("Scroll to the top of page");
        test.log(LogStatus.INFO,"Scroll to the top of page");
        JavascriptExecutor  js = ((JavascriptExecutor)driver);
        js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
    }

    /**
     * wait for element exist, the max waiting time is 20s
     * @param element
     * @throws InterruptedException
     */
    public static void waitForElementAppear(WebElement element) throws InterruptedException {
        int i = 0;
        TestBase.driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        try{
            while (i<10){
                if(element.isDisplayed()){
                    break;
                }
                Thread.sleep(2000);
                System.out.println("The element is not found yet");
                i++;
            }
        }catch (NoSuchElementException e){
            System.out.println("No such element exist!");
        }
    }
}
