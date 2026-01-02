package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

public class TC_03_LoginDDT extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class,groups="DataDriven")
    public void verify_loginddt(String email, String pwd, String exp) {

        logger.info("DDT Login Test Started");

        HomePage hp = new HomePage(driver);
        hp.clickmyaccount();
        hp.clickLogin();

        LoginPage lp = new LoginPage(driver);
        lp.email(email);
        lp.password(pwd);
        lp.clickLogin();

        MyAccountPage myacc = new MyAccountPage(driver);
        boolean targetPage = myacc.isMyAccountPageExists();

        if (exp.equalsIgnoreCase("valid")) {

            if (targetPage) {
                Assert.assertTrue(true);
                myacc.clickLogout();   // logout after valid login
            } else {
                Assert.fail("Valid login failed");
            }

        } else if (exp.equalsIgnoreCase("invalid")) {

            if (targetPage) {
                myacc.clickLogout();
                Assert.fail("Invalid login passed");
            } else {
                Assert.assertTrue(true);
            }
        }

        logger.info("DDT Login Test Finished");
    }
}
