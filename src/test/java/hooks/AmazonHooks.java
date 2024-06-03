package hooks;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import BaseClass.Base;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import screenshots.ScreenCapturer;
import utils.FileManager;

public class AmazonHooks {
	
	private WebDriver driver;
	protected ScreenCapturer screenCapturer;
	protected static WebDriverWait wait;
	protected static int timeoutSec = 50; // wait timeout = 50seconds by default
	private static Properties props;
	
	@Before(order = 0)
	public void setUp(Scenario scenario, WebDriver driver) {
		
		if (props == null) {
			String fileName = "props.properties";
			try {
				System.out.println("Loading props.properties file...");
				props = FileManager.loadProperties(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		this.driver =driver;
//		System.out.println("Hooks::setUp() -- setting up");
//		this.driver = Base.setupDriver();
		
	}
	
	@After(order = 0)
	public void tearDown() {
		System.out.println("Hooks::TearDown() -- quiting up");
		driver.quit();
	}
	
	@After(order = 1)
	public void takeScreenShot(Scenario sc) throws IOException {
		if(sc.isFailed()) {
			System.out.println("Hooks::takeScreenShot() -- scenario failed - "+sc.getName());
//			takeScreenshotOnFailure("Scenario_Failure");
		}
	}
}
