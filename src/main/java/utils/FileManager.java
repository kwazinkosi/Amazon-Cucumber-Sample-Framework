/*package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import org.apache.logging.log4j.core.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;

public class FileManager {

	public static Properties loadProperties(String fileName) throws IOException {
	    Properties props = new Properties();
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    InputStream inputStream = classLoader.getResourceAsStream(fileName);
	    if (inputStream == null) {
	        throw new IllegalArgumentException("Properties file not found: " + fileName);
	    }
	    props.load(inputStream);
	    inputStream.close();
	    return props;
	}
	
	// Capture Screenshot for failed tests
	private void captureScreenshot(String fileName) throws IOException {
	    TakesScreenshot screenshot = (TakesScreenshot) driver;
	    File scrFile = screenshot.getScreenshotAs(OutputType.FILE);
	    String path = screenshotDirectory + File.separator + fileName + ".png";
	    FileUtils.copyFile(scrFile, new File(path));
	    log.info("Screenshot captured and saved to: " + path);
	  }
}
*/

package utils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class FileManager {

	public static Properties loadProperties(String fileName) throws IOException {
	    Properties props = new Properties();
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    InputStream inputStream = classLoader.getResourceAsStream(fileName);
	    if (inputStream == null) {
	        throw new IllegalArgumentException("Properties file not found: " + fileName);
	    }
	    props.load(inputStream);
	    inputStream.close();
	    return props;
	}
}
