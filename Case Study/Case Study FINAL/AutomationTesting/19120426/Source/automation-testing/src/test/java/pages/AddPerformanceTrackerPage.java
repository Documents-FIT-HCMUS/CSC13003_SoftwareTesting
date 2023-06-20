package pages;

import helpers.UIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.List;

public class AddPerformanceTrackerPage {
    EdgeDriver driver;
    UIHelpers helpers;

    public AddPerformanceTrackerPage(EdgeDriver driver) {
        this.driver = driver;
        helpers = new UIHelpers(this.driver);
        setElements();
    }

    // Elements at Add Performance Tracker Page
    private WebElement performanceTrackerInput;
    private WebElement employeeNameInput;
    private WebElement reviewersNameInput;
    private By saveBtn = By.xpath("//button[@type='submit']");
    private final By errors = By.cssSelector(".oxd-text.oxd-text--span.oxd-input-field-error-message.oxd-input-group__message");

    private void setElements() {
        List<WebElement> inputFields = this.driver.findElements(By.cssSelector("input"));
        performanceTrackerInput = inputFields.get(1);
        employeeNameInput = inputFields.get(2);
        reviewersNameInput = inputFields.get(3);
    }

    public void addPerformanceTracker(String performanceTracker, String employee, String reviewers) throws InterruptedException {
        helpers.setWebElementText(performanceTrackerInput, performanceTracker);
        helpers.setDynamicDropdownElement(employeeNameInput, employee);
        helpers.setDynamicDropdownElement(reviewersNameInput, reviewers);
        helpers.clickElement(saveBtn);
    }

    public int getErrorNumber() {
        return helpers.getErrors(errors);
    }
}
