package pages;

import helpers.UIHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.edge.EdgeDriver;

public class LogInPage {
    EdgeDriver driver;
    UIHelpers helpers;

    public LogInPage(EdgeDriver _driver) {
        this.driver = _driver;
        helpers = new UIHelpers(this.driver);
    }

    // Elements at Sign In Page
    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By logInBtn = By.xpath("//button[@type='submit']");

    public void logIn(String username, String password) {
        helpers.setByElementText(usernameInput, username);
        helpers.setByElementText(passwordInput, password);
        helpers.clickElement(logInBtn);
    }
}
