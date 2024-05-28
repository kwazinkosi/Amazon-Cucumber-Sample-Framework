package screenshots;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class ScreenCapturer {

    private final WebDriver driver;
    private final String screenshotDirectory;
    public ScreenCapturer(WebDriver driver, String screenshotDirectory) {
        this.driver = driver;
        this.screenshotDirectory = screenshotDirectory;
    }

    public String captureScreenshot(String imgType) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File scrFile = screenshot.getScreenshotAs(OutputType.FILE);

        Instant now = Instant.now(); // Get current instant
        String fileName = now.toString().replace(":", "") +"-"+ imgType + ".png"; // Replace colons with empty string
        String path = screenshotDirectory + File.separator + fileName;
        FileUtils.copyFile(scrFile, new File(path));
        
        return fileName; // Return the captured filename
    }
}

