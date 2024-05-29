package utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class TestListener  implements ITestListener {

    private Map<String, WebDriver> driverMap = new HashMap<>();

    private static Properties props;
    @Override
    public void onTestStart(ITestResult result) {
    	
        if (props == null) {
            String fileName = "props.properties";
            try {
                props = FileManager.loadProperties(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String browserName = getBrowserName();
        WebDriver driver = createWebDriver(browserName);
        driverMap.put(result.getName(), driver);
    }
    
    private String getBrowserName() {
		return props.getProperty("browser");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        
    	WebDriver driver = driverMap.get(result.getName());
        if (driver != null) {
            driver.quit();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        WebDriver driver = driverMap.get(result.getName());
        if (driver != null) {
            driver.quit();
        }
    }
    
    private WebDriver createWebDriver(String browserName) {
  
    	switch (browserName.toLowerCase()) {
	        case "chrome":
	            ChromeOptions cOptions = new ChromeOptions();
	            cOptions.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors");
	            return new ChromeDriver(cOptions);
	        case "firefox":
	            FirefoxOptions fOptions = new FirefoxOptions();
	            fOptions.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors");
	            return new FirefoxDriver(fOptions);
	        case "edge":
	            EdgeOptions eOptions = new EdgeOptions();
	            eOptions.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors");
	            return new EdgeDriver(eOptions);
	        default:
	            throw new RuntimeException("Unsupported browser: " + browserName);
	    }
    }
}