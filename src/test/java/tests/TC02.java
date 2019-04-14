package tests;

import org.testng.annotations.Test;
import pages.BasicPage;
import utilities.Log;
import utils.CustomizeAssertion;
import utils.CustomizeFunctions;
import utils.TestBase;

public class TC02 extends TestBase {

    BasicPage basicPage;
    CustomizeFunctions customizeFunctions;
    CustomizeAssertion customizeAssertion;

    @Test
    public void verify_TC02() throws Exception {
        basicPage = new BasicPage();
        customizeFunctions = new CustomizeFunctions();
        customizeAssertion = new CustomizeAssertion();

        driver.get("https://www.baidu.com/");

        Log.info("Step 1: Enter keywords into search field");
        customizeFunctions.input(basicPage.inputOfSearch,"Automation test","Enter a keywords");
    }
}
