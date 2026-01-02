package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.RegistrationPage;

public class TC_01_AccountRegistration extends BaseClass {

    @Test(groups = {"regression","Master"})
    public void verifyaccountregistration() {

        logger.info("--- Starting Test ---");

        try {
            HomePage hp = new HomePage(driver);
            hp.clickmyaccount();
            hp.clickRegister();
            logger.info("Clicked on register");

            RegistrationPage rp = new RegistrationPage(driver);
            logger.info("Providing user details");

            rp.firstname(randomString().toUpperCase());
            rp.lastname(randomString().toUpperCase());
            rp.email(randomString() + "@gmail.com");
            rp.telephone(randomNumber());

            String pwd = randompwd();
            rp.password(pwd);
            rp.confirmpassword(pwd);

            rp.register();

            logger.info("Validating expected message");
            String actualMsg = rp.message();
            Assert.assertEquals(actualMsg, "Your Account Has Been Created!");

        } catch (Exception e) {
            logger.error("Test failed", e);  
            Assert.fail();
        }

        logger.info("--- Finished Test ---");
    }
}
