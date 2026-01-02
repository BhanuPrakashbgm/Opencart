package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.BaseClass;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;
    String repName;

    @Override
    public void onStart(ITestContext testContext) {

        // Timestamp for unique report name
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        // Initialize ExtentSparkReporter
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\reports\\" + repName);
        sparkReporter.config().setDocumentTitle("Opencart Automation Report");
        sparkReporter.config().setReportName("Opencart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        // Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        // Set OS and Browser info from TestNG parameters
        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, result.getMethod().getMethodName() + " executed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, result.getMethod().getMethodName() + " failed");
        test.log(Status.INFO, result.getThrowable());

        try {
            String imgPath = captureScreen(result.getMethod().getMethodName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getMethod().getMethodName() + " skipped");
        if (result.getThrowable() != null) {
            test.log(Status.INFO, result.getThrowable());
        }
    }

    @Override
    public void onFinish(ITestContext testContext) {
        extent.flush();

        String reportPath = System.getProperty("user.dir") + "\\reports\\" + repName;
        try {
            Desktop.getDesktop().browse(new File(reportPath).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Send the report via email
//        sendReportByEmail(reportPath);
    }

    // Utility Methods
    public String randomeNumber() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomAlphaNumeric() {
        String str = RandomStringUtils.randomAlphabetic(3);
        String num = RandomStringUtils.randomNumeric(3);
        return str + "@" + num;
    }

    public String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // Take screenshot using static driver from BaseClass
        TakesScreenshot ts = (TakesScreenshot) BaseClass.driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        // Create screenshots folder if it doesn't exist
        String dirPath = System.getProperty("user.dir") + "\\screenshots\\";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Destination file
        String destPath = dirPath + tname + "_" + timeStamp + ".png";
        File target = new File(destPath);

        // Copy screenshot
        org.apache.commons.io.FileUtils.copyFile(source, target);

        return destPath;
    }

    // Sending the report via email
//    public void sendReportByEmail(String reportPath) {
//        try {
//            URL url = new File(reportPath).toURI().toURL();
//            ImageHtmlEmail email = new ImageHtmlEmail();
//            email.setDataSourceResolver(new DataSourceUrlResolver(url));
//            email.setHostName("smtp.gmail.com");
//            email.setSmtpPort(465);
//            email.setSSLOnConnect(true);
//
//            // Use environment variables or App Password
//            String username = System.getenv("EMAIL_USER"); // your email
//            String password = System.getenv("EMAIL_PASS"); // Gmail App Password
//            email.setAuthenticator(new DefaultAuthenticator(username, password));
//
//            email.setFrom(username);
//            email.setSubject("Automation Test Report");
//            email.setMsg("Please find attached the latest automation test report.");
//            email.addTo(username); // recipient email
//            email.attach(url, "Extent Report", "Automation Test Report");
//            email.send();
//
//            System.out.println("Email sent successfully!");
//        } catch (EmailException | IOException e) {
//            e.printStackTrace();
//        }
    }

