package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
	
//	public WebDriver driver;
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	
	public WebDriver initDriver(String browserName) {
		
		if (browserName.equals("chrome")) {
			
			driver.set(new ChromeDriver());
		}
		else if(browserName.equals("firefox")) {
			driver.set(new FirefoxDriver());
		}
		else if(browserName.equals("edge")) {
			driver.set(new EdgeDriver());
		}
		else {
			System.out.println("Please pass correct browser value");
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		return getDriver();
	}
	
	public static synchronized WebDriver getDriver() {
		return driver.get();
	}
	
}
