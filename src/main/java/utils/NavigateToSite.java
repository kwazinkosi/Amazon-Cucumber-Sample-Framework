package utils;

import org.openqa.selenium.WebDriver;

import pages.AmazonHomePage;

public class NavigateToSite {

	public static void navigateToAmazon(String url, WebDriver driver) {
		
        try {
            
        	AmazonHomePage amazonHomePage = new AmazonHomePage(driver);
        	
            amazonHomePage.visitPage(url);
        } 
        catch (Exception e) {
        	System.out.println(" --- Failed---- ");
        }
    }
}