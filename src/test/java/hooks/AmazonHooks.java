package hooks;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import BaseClass.Base;
import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import screenshots.ScreenCapturer;
import utils.FileManager;

public class AmazonHooks{
	
	private DriverFactory driverFactory;
	private WebDriver driver;
//	private FileManager fileManager;
	private ScreenCapturer screenCapturer;
	Properties props;

	@Before(order = 0)
	public void getProps() {
		System.out.println("Hooks::setUp() -- setting up");
		
//		fileManager =new FileManager();
		try {
			props = FileManager.loadProperties("props.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		this.driver = Base.setupDriver();
	}
	
	@Before(order = 1)
	public void launchBrowser() {
		String browserName =props.getProperty("browser");
		driverFactory = new DriverFactory();
		driver = driverFactory.initDriver(browserName);
	}
	
	
	@After(order = 0)
	public void quitBrowser() {
		System.out.println("Hooks::quitBrowser() -- setting up");
		driver.quit();
	}
	
	@After(order = 0)
	public void tearDown(Scenario scenario) {
		if(scenario.isFailed()) {
//			take screenshot
			try {

				screenCapturer.captureScreenshot(this.driver, scenario.getName());
				System.out.println("		### Hook::takeScreenshotOnFailure() -- navigation to amazon succesfully! ###");
//				log.info("		###  Base::takeScreenshotOnFailure() -- navigation to amazon succesfully! ###");
			} catch (IOException e) {

//				log.error("		###  Base::takeScreenshotOnFailure() -- Failed to capture screenshot on failure ###", e);
				System.out.println("		###  Hook::takeScreenshotOnFailure() -- navigation to amazon succesfully! ###");
			}
		}
	}
	
}
