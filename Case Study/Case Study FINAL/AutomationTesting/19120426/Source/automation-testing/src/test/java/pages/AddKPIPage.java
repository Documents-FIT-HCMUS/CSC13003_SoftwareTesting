package pages;

import helpers.UIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.List;

public class AddKPIPage {
    EdgeDriver driver;
    UIHelpers helpers;

    public AddKPIPage(EdgeDriver _driver) {
        this.driver = _driver;
        helpers = new UIHelpers(this.driver);
        setElements();
    }

    // Elements at Add KPI Page
    private WebElement kpiNameInput;
    private WebElement minimumRatingInput;
    private WebElement maximumRatingInput;
    private final By jobTitleDropdown = By.cssSelector(".oxd-select-text.oxd-select-text--active");
    private final By saveBtn = By.xpath("//button[@type='submit']");
    private final By errors = By.cssSelector(".oxd-text.oxd-text--span.oxd-input-field-error-message.oxd-input-group__message");

    private void setElements() {
        List<WebElement> inputFields = this.driver.findElements(By.cssSelector(".oxd-input.oxd-input--active"));
        kpiNameInput = inputFields.get(1);
        minimumRatingInput = inputFields.get(2);
        maximumRatingInput = inputFields.get(3);
    }

    public void addKPI(String kpiValue, String jobTitleValue, String minimumRatingValue, String maximumRatingValue) throws InterruptedException {
        helpers.setWebElementText(kpiNameInput, kpiValue);
        helpers.setDropdownElement(jobTitleDropdown, jobTitleValue);
        helpers.setWebElementText(minimumRatingInput, minimumRatingValue);
        helpers.setWebElementText(maximumRatingInput, maximumRatingValue);
        helpers.clickElement(saveBtn);
    }

    public int getErrorNumber() {
        return helpers.getErrors(errors);
    }
}
