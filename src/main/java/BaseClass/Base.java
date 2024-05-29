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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeSuite;

import screenshots.ScreenCapturer;
import utils.FileManager;
import utils.WebDriverWaitManager;

public class Base {

	protected final static Logger log = LogManager.getLogger(Base.class);
	protected static WebDriver driver;

	protected static WebDriverWait wait;
	protected int timeoutSec = 50; // wait timeout = 50seconds by default
	protected static Properties props;
	protected ScreenCapturer screenCapturer;

	public Base(WebDriver driver, String screenshotDir) {
		this.driver = driver;
		this.screenCapturer = new ScreenCapturer(screenshotDir);
		System.out.println("Base() -- done constructing ");
	}

	@BeforeSuite
	public static void setupLogging() {

		System.out.println("Base::setupLogging() -- Attempting to configure logger.");
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
		System.out.println("Base::setupLogging() -- log4jConfig file is " + log4jConfigFile);
		ConfigurationSource source;

		try {

			source = new ConfigurationSource(new FileInputStream(log4jConfigFile));
			Configurator.initialize(null, source);
			System.out.println("Base::setupLogging() -- Done configuring logger.");
		} catch (FileNotFoundException e) {
			log.error("Base::setupLogging() -- Failed to find log4j.properties file: " + log4jConfigFile, e); 
		} catch (IOException e) {
			log.error("Base::setupLogging() -- Error while configuring logger: " + log4jConfigFile, e); 
		}
	}
	@AfterTest
    public void tearDown() {
        if (driver != null) {
        	System.out.println("		### tearDown() -- Quiting ###");
            driver.quit();
        }
        System.out.println("\n\n### ============================== Tests End ============================== ###");
        log.info("//============== AmazonHomePage Tests End =============\\");
    }
	
	@BeforeClass
	public void setupDriver() {

		try {
//			setupLogging();
			String fileName = "props.properties";
			Base.props = loadProperties(fileName);
//			setupDriver();
		} catch (IOException e) {
			log.error("		### Base() -- logging configuration failed");
			e.printStackTrace();
		}

		System.out.println("Base::setupDriver() -- setting up the browser");
		String browserName = getBrowserName(); // get browser name from properties file

		switch (browserName.toLowerCase()) {

		case "chrome":

			ChromeOptions cOptions = new ChromeOptions();
			cOptions.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors");
//			driver = new ChromeDriver(cOptions);
			driver = new ChromeDriver();
			break;

		case "firefox":

			FirefoxOptions fOptions = new FirefoxOptions();
			fOptions.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors");
//			driver = new FirefoxDriver(fOptions);
			driver = new FirefoxDriver();
			break;

		case "edge":
			EdgeOptions eOptions = new EdgeOptions();
			eOptions.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors");
//			driver = new EdgeDriver(eOptions);
			driver = new EdgeDriver();
			break;

		default:
			throw new RuntimeException("Unsupported browser: " + browserName);
		}

		driver.manage().window().maximize();
		wait = WebDriverWaitManager.createWebDriverWait(driver, timeoutSec);
	}

	public void click(WebElement element) {
		pause(1000); // TODO delete this later
		find(element).click();
	}

	public static Properties loadProperties(String fileName) throws IOException {
		return FileManager.loadProperties(fileName); // Using the static method from FileReader
	}

	// take screenshot upon failure
	public void takeScreenshotOnFailure(String imgType) {
		try {

			screenCapturer.captureScreenshot(driver, imgType);
			System.out.println("		### Base::takeScreenshotOnFailure() -- navigation to amazon succesfully! ###");
			log.info("		###  Base::takeScreenshotOnFailure() -- navigation to amazon succesfully! ###");
		} catch (IOException e) {

			log.error("		###  Base::takeScreenshotOnFailure() -- Failed to capture screenshot on failure ###", e); 
			System.out.println("		###  Base::takeScreenshotOnFailure() -- navigation to amazon succesfully! ###");
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
		return Integer.parseInt(props.getProperty("globalWaitTime", "50")); // Set default if not present
	}

	// =============== Getters and Setters ================\\
	public void setTimeOutSec(int timeoutSec) {
		this.timeoutSec = timeoutSec;
	}

	public void visitPage(String url) {

		log.info("		### visitPage() -- visiting " + url + " ###");
		System.out.println("		### visitPage() -- visiting " + url + " ###");
		driver.manage().deleteAllCookies();
		driver.get(url);
	}

	public WebElement find(WebElement element) {
		try {
			// Since the element is already located by @FindBy, we just wait for it to be
			// present
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

	public boolean isDisplayed(WebElement element) {

		try {

			System.out.println("		### Base --waiting for element to display. ###");
			log.info("		### Base --waiting for element to display. ###");
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (TimeoutException e) {
			log.warn("Timeout of {} wait for {}", timeoutSec, element);
			return false;
		}
	}

	public void pause(int timesec) {
		try {
			Thread.sleep(timesec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static WebDriver getDriver() {

		return driver;
	}
}
