package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestBase;


public class BasicPage {

    public BasicPage() {
        PageFactory.initElements(TestBase.driver, this);
    }

    //Input: Search input
    @FindBy(xpath="//input[@id='kw']")
    public WebElement inputOfSearch;

    //Button: Submit button
    @FindBy(xpath="//input[@type='submit']")
    public WebElement btnOfSubmit;

}
