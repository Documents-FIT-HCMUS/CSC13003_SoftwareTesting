package base;

import helpers.CaptureHelpers;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerTest implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        CaptureHelpers.captureScreenshot(BaseSetup.getDriver(), result.getName());
    }
}
