package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC_02_Login extends BaseClass {

    @Test(groups = {"sanity","Master"})
    public void verify_login() {

        logger.info("===== Starting Login Test =====");

        try {
            HomePage hp = new HomePage(driver);
            hp.clickmyaccount();
            hp.clickLogin();

            logger.info("Navigated to Login page");

            LoginPage lp = new LoginPage(driver);
            lp.email(p.getProperty("email"));
            lp.password(p.getProperty("password"));
            lp.clickLogin();

            logger.info("Login submitted");

            MyAccountPage myacc = new MyAccountPage(driver);
            Assert.assertTrue(
                myacc.isMyAccountPageExists(),
                "Login failed - My Account page not displayed"
            );

            logger.info("===== Login Test Passed =====");

        } catch (Exception e) {
            logger.error("Login Test Failed", e);
            Assert.fail();
        }
    }
}
