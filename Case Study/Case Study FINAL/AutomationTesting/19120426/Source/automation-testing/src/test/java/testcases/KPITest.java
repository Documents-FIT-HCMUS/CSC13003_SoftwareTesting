package testcases;

import base.BaseSetup;
import base.ListenerTest;
import helpers.ExcelHelpers;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.AddKPIPage;
import pages.LogInPage;

import java.time.Duration;

@Listeners(ListenerTest.class)
public class KPITest {
    private EdgeDriver driver;
    private ExcelHelpers excelHelpers;
    private WebDriverWait wait;
    private LogInPage logInPage;
    private AddKPIPage kpiPage;
    private final String logInUrl = "http://localhost/orangehrm-5.1";
    private final String addKPIUrl = "http://localhost/orangehrm-5.1/web/index.php/performance/saveKpi";

    @BeforeTest
    public void setUpBrowser() throws Exception {
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

    @Test(alwaysRun = true)
    public void addKPITest() throws Exception {
        excelHelpers.setExcelFilePath("src/test/resources/data_source.xlsx", "KPI");
        int rowCount = excelHelpers.getRowCount();
        for (int i = 1; i <= rowCount; i++) {
            driver.navigate().to(addKPIUrl);
            kpiPage = new AddKPIPage(this.driver);

            String kpi = excelHelpers.getCellData("Key Performance Indicator", i);
            String jobTitle = excelHelpers.getCellData("Job Title", i);
            String minimumRating = excelHelpers.getCellData("Minimum Rating", i);
            String maximumRating = excelHelpers.getCellData("Maximum Rating", i);
            String expectedResult = excelHelpers.getCellData("Expected Result", i);
            kpiPage.addKPI(kpi, jobTitle, minimumRating, maximumRating);
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
            int errorNumber = kpiPage.getErrorNumber();
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
