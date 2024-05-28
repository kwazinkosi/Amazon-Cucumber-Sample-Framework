package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UrlUtil {
	
	public static String getUrlFromHref(WebDriver driver, WebElement element) {
        return element.getAttribute("href");
    }

    public static boolean isUrlPresent(WebDriver driver, String expectedUrl) {
        return driver.getCurrentUrl().equals(expectedUrl);
    }
}
