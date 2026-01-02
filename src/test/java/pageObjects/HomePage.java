package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[@title='My Account']")
    WebElement myaccount;

    @FindBy(xpath = "//a[normalize-space()='Register']")
    WebElement lnkregister;
//    @FindBy(xpath = "//input[@value='Login']")
//    WebElement login;
    @FindBy(linkText = "Login")
    WebElement login;
    

    public void clickmyaccount() {
        myaccount.click();
    }

    public void clickRegister() {
        lnkregister.click();
    }
    public void clickLogin() {
        login.click();
    }
}
