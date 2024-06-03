package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
	
//	public WebDriver driver;
	public static Threadlocal<WebDriver> driver = new Threadlocal<>();
	
	public void init_driver(String browserName) {
		
		if (browserName.equals("chrome")) {
			
			driver.set(new ChromeDriver());
		}
	}
}
