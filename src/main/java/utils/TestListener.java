/*package utils;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.BeforeTest;


public class TestListener implements ITestListener {

	protected static WebDriverWait wait;
	protected static int timeoutSec = 50; // wait timeout = 50seconds by default
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static Properties props;

    public TestListener() {
        loadProperties();
        if(driver == null) {
        	setUpWebDriver();
        }
    }

    private static void loadProperties() {
        if (props == null) {
            String fileName = "props.properties";
            try {
                System.out.println("Loading props.properties file...");
                props = FileManager.loadProperties(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @BeforeTest
    public void setUpWebDriver() {
    	
    	
        String browserName = getBrowserName();
        driver.set(createWebDriver(browserName));
        driver.get().manage().window().maximize();
        driver.get().manage().deleteAllCookies();
        wait = WebDriverWaitManager.createWebDriverWait(driver.get(), timeoutSec);
        System.out.println("Done setting up WebDriver.");
    }

    private String getBrowserName() {
        return props.getProperty("browser");
    }

    @Override
    public void onTestStart(ITestResult result) {
        // No need for driverMap, use ThreadLocal instead
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (driver.get() != null) {
            driver.get().quit();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (driver.get() != null) {
            driver.get().quit();
        }
    }

    
    private WebDriver createWebDriver(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                ChromeOptions cOptions = new ChromeOptions();
                // Uncomment for headless mode
                // cOptions.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors");
                return new ChromeDriver();
            case "firefox":
                FirefoxOptions fOptions = new FirefoxOptions();
                // Uncomment for headless mode
                // fOptions.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors");
                return new FirefoxDriver(fOptions);
            case "edge":
                EdgeOptions eOptions = new EdgeOptions();
                // Uncomment for headless mode
                // eOptions.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors");
                return new EdgeDriver(eOptions);
            default:
                throw new RuntimeException("Unsupported browser: " + browserName);
        }
    }


    public void setWaitTimeout(int timeout) {
        timeoutSec = timeout;
    }

    public  WebDriverWait getWait() {
        return wait;
    }

    public WebDriver getDriver() {
        WebDriver driverInstance = driver.get();
        if (driverInstance == null) {
        	setUpWebDriver();
//            throw new RuntimeException("WebDriver instance not initialized! Call setUpWebDriver() before accessing driver.");
        }
        return driverInstance;
    }
    public String getUrl() {
        return props.getProperty("appUrl");
    }
}*/
