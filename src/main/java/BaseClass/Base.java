package BaseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeTest;

import screenshots.ScreenCapturer;
import utils.FileManager;
import utils.WebDriverWaitManager;

public class Base {

	protected final Logger log = LogManager.getLogger(this.getClass());
	protected static WebDriver driver;
	private WebDriverWait wait;
	protected int timeoutSec = 5; // wait timeout = 5seconds by default
	protected static Properties props;
	protected final ScreenCapturer screenCapturer; 

	public Base(String screenshotDir) {
		
		setupLogging();
		try {

			String fileName = "props.properties";
			Base.props = loadProperties(fileName);
			setupDriver();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.screenCapturer = new ScreenCapturer(driver, screenshotDir);
	}

	
	public void setupLogging() {

	    String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j2.properties";
	    ConfigurationSource source;
		try {
			
			source = new ConfigurationSource(new FileInputStream(log4jConfigFile));
		    Configurator.initialize(null, source);
		    System.out.println("Base::setupLogging() -- Done configuring logger.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@BeforeClass
	public void setupDriver() {
		
		System.out.println("Base::setupDriver()");
		String browserName = getBrowserName(); // get browser name from properties file
		
		switch (browserName.toLowerCase()) {
		
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			default:
				throw new RuntimeException("Unsupported browser: " + browserName);
		}

		driver.manage().window().maximize();
		wait = WebDriverWaitManager.createWebDriverWait(driver, timeoutSec);
	}

	public void click(WebElement element) {
		find(element).click();
	}

	public static Properties loadProperties(String fileName) throws IOException {
		return FileManager.loadProperties(fileName); // Using the static method from FileReader
	}

	// take screenshot upon failure
	public void takeScreenshotOnFailure(String imgType) {
	    try {
	        screenCapturer.captureScreenshot(imgType);
	    } catch (IOException e) {
	        log.error("Failed to capture screenshot on failure", e); // Log the error with exception details
	    }
	}
	
	// get the Url of page
	public String getApplicationUrl() {
		return props.getProperty("appUrl");
	}

	// get browser name from properties file or configuration
	public String getBrowserName() {
		return props.getProperty("browser");
	}

	public int getGlobalWaitTime() {
		return Integer.parseInt(props.getProperty("globalWaitTime", "10")); // Set default if not present
	}

	// =============== Getters and Setters ================\\
	public void setTimeOutSec(int timeoutSec) {
		this.timeoutSec = timeoutSec;
	}

	public void visitPage(String url) {
		driver.manage().deleteAllCookies();
		driver.get(url);
	}

	public WebElement find(WebElement element) {
	    try {
	        // Since the element is already located by @FindBy, we just wait for it to be present
	        return wait.until(ExpectedConditions.visibilityOf(element));
	    } catch (TimeoutException | NoSuchElementException e) {
	        log.error("Element not found: " + element, e);
	        throw e; // Re-throw the exception for handling in test cases
	    }
	}

	public void typeText(WebElement element, String text) {
	    WebElement webElement = find(element); // Wait for the element to be visible
	    webElement.clear(); // Clear the field before typing
	    webElement.sendKeys(text); // Send the text to the element
	}

	public boolean isDisplayed(WebElement searchBox) {
		
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated((By) searchBox));
			return true;
		} catch (TimeoutException e) {
			log.warn("Timeout of {} wait for {}", timeoutSec, searchBox);
			return false;
		}
	}
	
	public static WebDriver getDriver() {
      
		return driver;
    }
}
