package tests;

import org.testng.annotations.Test;
import pages.BasicPage;
import utilities.Log;
import utils.CustomizeAssertion;
import utils.CustomizeFunctions;
import utils.TestBase;

public class TC01 extends TestBase {
    BasicPage basicPage;
    CustomizeFunctions customizeFunctions;
    CustomizeAssertion customizeAssertion;

    @Test
    public void verify_TC01() throws Exception {

        basicPage = new BasicPage();
        customizeFunctions = new CustomizeFunctions();
        customizeAssertion = new CustomizeAssertion();

        driver.get("https://www.baidu.com/");

        Log.info("Step 1: Enter keywords into search field");
        customizeFunctions.input(basicPage.inputOfSearch,"Automation test","Enter a keywords");
//        customizeAssertion.assertTrue(false,"verify it is true");

        Log.info("Step 2: Click on Submit button");
        customizeFunctions.click(basicPage.btnOfSubmit,"Click on submit button");


        eventualAssert();




    }

}
