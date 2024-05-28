package screenshots;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class ScreenCapturer {

//    private final WebDriver driver;
    private final String screenshotDirectory;
    public ScreenCapturer(String screenshotDirectory) {
//        this.driver = driver;
        this.screenshotDirectory = screenshotDirectory;
    }

    public String captureScreenshot(WebDriver driver, String imageType) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);

        Instant now = Instant.now();
        String randomId = String.valueOf(new Random().nextInt(100));
        String fileName = now.toString().replace(":", "") + "-" + randomId + "-" + imageType + ".png";
        Path screenshotPath = Paths.get(screenshotDirectory, fileName);

        try {
            FileUtils.copyFile(screenshotFile, screenshotPath.toFile());
            System.out.println("		### ScreenCapturer::captureScreenshot() -- screenshot capture succesful! " );  
        } catch (IOException e) {
            System.out.println("		### ScreenCapturer::captureScreenshot() -- Failed to capture screenshot: " + e.getMessage());   
        }

        return fileName;
    }

}

