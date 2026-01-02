package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-firstname']")
    WebElement firstname;

    @FindBy(xpath = "//input[@id='input-lastname']")
    WebElement lastname;

    @FindBy(xpath = "//input[@id='input-email']")
    WebElement email;

    @FindBy(xpath = "//input[@id='input-telephone']")
    WebElement telephone;

    @FindBy(xpath = "//input[@id='input-password']")
    WebElement pwd;

    @FindBy(xpath = "//input[@id='input-confirm']")
    WebElement cpwd;

    @FindBy(xpath = "//input[@name='agree']")
    WebElement agreebtn;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement registerbtn;


    @FindBy(xpath = "//h1[text()='Your Account Has Been Created!']")
    WebElement msg;

    // Actions
    public void firstname(String fname) {
        firstname.sendKeys(fname);
    }

    public void lastname(String lname) {
        lastname.sendKeys(lname);
    }

    public void email(String temail) {
        email.sendKeys(temail);
    }
    public void telephone(String tel) {
    	telephone.sendKeys(tel);
    }
    public void password(String pwdd) {
        pwd.sendKeys(pwdd);
    }

    public void confirmpassword(String pwdd) {
        cpwd.sendKeys(pwdd);
    }
    

    public void register() {
        agreebtn.click();
        registerbtn.click();
    }

    public String message() {
        return msg.getText();
    }
}
