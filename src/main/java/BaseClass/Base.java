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
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import screenshots.ScreenCapturer;
import utils.FileManager;
import utils.WebDriverWaitManager;
//import utils.TestListener;

public class Base {

	public final static Logger log = LogManager.getLogger(Base.class);
	protected ScreenCapturer screenCapturer;
	protected static WebDriverWait wait;
	protected static int timeoutSec = 50; // wait timeout = 50seconds by default
	private static Properties props;
//    private TestListener listener; // Or private TestListener listener; (depending on approach)

	private static WebDriver driver;

	public Base(String screenshotDir, WebDriver driver) {
		this.screenCapturer = new ScreenCapturer(screenshotDir);
		this.driver = driver;
		// Removed props and driver from constructor as they are accessed through
		// TestListener
	}

	public void initializePageElements() {
		PageFactory.initElements(driver, this);
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

	@BeforeSuite
	public static void setupLogging() {
		// Logic for configuring logger (can be kept or moved to a separate utility
		// class)
		String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
		System.out.println("Base::setupLogging() -- log4jConfig file is " + log4jConfigFile);
		try {
			ConfigurationSource source = new ConfigurationSource(new FileInputStream(log4jConfigFile));
			Configurator.initialize(null, source);
			System.out.println("Base::setupLogging() -- Done configuring logger.");
		} catch (FileNotFoundException e) {
//            log.error("Failed to find log4j.properties file: " + log4jConfigFile, e);
		} catch (IOException e) {
//            log.error("Error while configuring logger: " + log4jConfigFile, e);
		}
	}

//	@BeforeTest
	public static  WebDriver setupDriver() {


		loadProperties();
		String browserName = getBrowserName();
		driver = createWebDriver(browserName);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		wait = WebDriverWaitManager.createWebDriverWait(driver, timeoutSec);
		System.out.println("Done setting up WebDriver.");
		return driver;
	}

	private static WebDriver createWebDriver(String browserName) {
		switch (browserName.toLowerCase()) {
		case "chrome":
			ChromeOptions cOptions = new ChromeOptions();
			// Uncomment for headless mode
			// cOptions.addArguments("--headless", "--disable-gpu",
			// "--ignore-certificate-errors");
			return new ChromeDriver();
		case "firefox":
			FirefoxOptions fOptions = new FirefoxOptions();
			// Uncomment for headless mode
			// fOptions.addArguments("--headless", "--disable-gpu",
			// "--ignore-certificate-errors");
			return new FirefoxDriver(fOptions);
		case "edge":
			EdgeOptions eOptions = new EdgeOptions();
			// Uncomment for headless mode
			// eOptions.addArguments("--headless", "--disable-gpu",
			// "--ignore-certificate-errors");
			return new EdgeDriver(eOptions);
		default:
			throw new RuntimeException("Unsupported browser: " + browserName);
		}
	}

	public void click(WebElement element) {
		find(element).click();
	}

	public void takeScreenshotOnFailure(String imgType) throws IOException {

		try {

			screenCapturer.captureScreenshot(this.driver, imgType);
			System.out.println("		### Base::takeScreenshotOnFailure() -- navigation to amazon succesfully! ###");
			log.info("		###  Base::takeScreenshotOnFailure() -- navigation to amazon succesfully! ###");
		} catch (IOException e) {

			log.error("		###  Base::takeScreenshotOnFailure() -- Failed to capture screenshot on failure ###", e);
			System.out.println("		###  Base::takeScreenshotOnFailure() -- navigation to amazon succesfully! ###");
		}
	}

	public void visitPage(String url) {

		WebDriver driver = getDriver();
		String logMessage = "### Base::visitPage() -- Visiting " + url;
		log.info(logMessage);
		System.out.println(logMessage);
		driver.manage().deleteAllCookies();
		driver.get(url);
	}

	public WebElement find(WebElement element) {

		try {
			return wait.until(ExpectedConditions.visibilityOf(element));
		} catch (TimeoutException | NoSuchElementException e) {
			System.out.println("		### Base::find() -- Element not found: " + element);
			log.error("		### Base::find() -- Element not found: " + element, e);
			throw e;
		}
	}

	public void typeText(WebElement element, String text) {
		WebElement webElement = find(element);
		webElement.clear();
		webElement.sendKeys(text);
	}

	public boolean isDisplayed(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			System.out.println("		### Base::isDisplayed() -- Element is displayed ");
			return true;
		} catch (TimeoutException e) {

			System.out.println("		### Base::isDisplayed() -- Element NOT displayed ");
			log.warn("Timeout waiting for element: " + element);
			return false;
		}
	}

	public void waitTillClickable(WebElement element) {

		try {

			wait.until(ExpectedConditions.elementToBeClickable(element));
			System.out.println("		### Base::isClickable() -- Element is displayed ");
		} catch (TimeoutException e) {

			log.warn("Timeout waiting for element: " + element);
			System.out.println("		### Base::isClickable() -- Element is NOT displayed ");
		}
	}

//    public void setTestListener(TestListener listener) {
//        this.listener = listener;
//    }

	public void setWaitTimeout(int timeout) {
		timeoutSec = timeout;
	}

	public WebDriverWait getWait() {
		return wait;
	}

	public WebDriver getDriver() {
		return this.driver;
	}

	private static String getBrowserName() {
		return props.getProperty("browser");
	}

	public String getUrl() {
		return props.getProperty("appUrl");
	}
}
