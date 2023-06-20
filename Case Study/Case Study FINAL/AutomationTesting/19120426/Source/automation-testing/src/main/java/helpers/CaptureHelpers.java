package helpers;

import org.apache.commons.io.FileUtils;
import org.monte.media.Format;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.edge.EdgeDriver;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureHelpers extends ScreenRecorder {
    // Get the path of the current project
    static String projectPath = System.getProperty("user.dir") + "/";
    static String screenshotFolderPath = projectPath + "src/results/screenshot";
    static String recordFolderPath = projectPath + "src/results/record";
    // Create format datetime to save image or video
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    public CaptureHelpers(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
    }

    // Screenshot
    public static void captureScreenshot(EdgeDriver driver, String screenName) {
        try {
            TakesScreenshot screenshot = driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            File dir = new File(screenshotFolderPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            FileUtils.copyFile(
                source,
                new File(screenshotFolderPath + "/" + screenName + "_" + dateFormat.format(new Date()) + ".png")
            );
        }
        catch (Exception exception) {
            System.out.println("Exception while taking screenshot: " + exception.getMessage());
        }
    }
}
