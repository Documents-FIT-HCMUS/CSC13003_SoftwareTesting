package base;

import org.openqa.selenium.edge.EdgeDriver;

public class BaseSetup {
    public static EdgeDriver getDriver() {
        return driver;
    }

    public static EdgeDriver driver;
}
