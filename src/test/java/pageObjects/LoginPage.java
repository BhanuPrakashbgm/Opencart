package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(id = "input-email")
    WebElement email;

    @FindBy(id = "input-password")
    WebElement pwd;

    @FindBy(xpath = "//input[@value='Login']")
    WebElement loginbtn;

    public void email(String temail) {
        email.clear();
        email.sendKeys(temail);
    }

    public void password(String pwdd) {
        pwd.clear();
        pwd.sendKeys(pwdd);
    }

    public void clickLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(loginbtn)).click();
    }
}
