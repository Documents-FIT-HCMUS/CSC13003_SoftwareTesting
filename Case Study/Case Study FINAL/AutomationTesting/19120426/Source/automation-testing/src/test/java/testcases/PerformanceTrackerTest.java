package testcases;

import base.BaseSetup;
import base.ListenerTest;
import helpers.ExcelHelpers;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.AddPerformanceTrackerPage;
import pages.LogInPage;

import java.time.Duration;

@Listeners(ListenerTest.class)
public class PerformanceTrackerTest {
    private EdgeDriver driver;
    private ExcelHelpers excelHelpers;
    private WebDriverWait wait;
    private LogInPage logInPage;
    private AddPerformanceTrackerPage performanceTrackerPage;
    private final String logInUrl = "http://localhost/orangehrm-5.1";
    private final String addPerformanceTrackerUrl = "http://localhost/orangehrm-5.1/web/index.php/performance/addPerformanceTracker";

    @BeforeTest
    public void setUpBrowser() {
        driver = new EdgeDriver();
        BaseSetup.driver = this.driver;
        driver.manage().window().maximize();
        excelHelpers = new ExcelHelpers();
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(0));
    }

    @BeforeClass
    public void logInPage() throws Exception {
        logInPage = new LogInPage(driver);
        driver.get(logInUrl);
        logInPage.logIn("19120426-DiemUyen", "Kcpm_cq193_k1");
        Thread.sleep(1000);
    }

    @Test
    public void addPerformanceTrackerTest() throws Exception {
        excelHelpers.setExcelFilePath("src/test/resources/data_source.xlsx", "Performance Tracker");
        int rowCount = excelHelpers.getRowCount();
        System.out.println(rowCount);
        for (int i = 1; i <= rowCount; i++) {
            driver.navigate().to(addPerformanceTrackerUrl);
            performanceTrackerPage = new AddPerformanceTrackerPage(this.driver);

            String trackerName = excelHelpers.getCellData("Tracker Name", i);
            String employeeName = excelHelpers.getCellData("Employee Name", i);
            String reviewersName = excelHelpers.getCellData("Reviewers Name", i);
            String expectedResult = excelHelpers.getCellData("Expected Result", i);
            performanceTrackerPage.addPerformanceTracker(trackerName, employeeName, reviewersName);
            Thread.sleep(4000);
            assertOutput(expectedResult, i);
        }
    }

    private void assertOutput(String expectedResult, int rowNumber) throws Exception {
        String actualResult = "";
        if (expectedResult.contains("http")) {
            for (String windows : driver.getWindowHandles()) {
                driver.switchTo().window(windows);
            }
            actualResult = driver.getCurrentUrl();
        }
        else {
            int errorNumber = performanceTrackerPage.getErrorNumber();
            actualResult = errorNumber + " error field";
        }
        excelHelpers.setCellData(actualResult, rowNumber, "Actual Result");
        Assert.assertEquals(actualResult, expectedResult);
    }

    @AfterClass
    public void closeBrowser() {
        driver.close();
    }
}
