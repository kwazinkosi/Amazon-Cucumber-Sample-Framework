

package tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import BaseClass.Base;
import pages.AmazonHomePage;
import pages.TodayDealsPage;

public class TodayDealsTests extends Base {

    
	private TodayDealsPage todayDeals;
    private WebDriver driver;

    @FindBy(xpath = ".//span[contains(text(), '% off')]")
    private WebElement discountElement;
    
    @FindBy(className = "ProductCard-module__card_uyr_Jh7WpSkPx4iEpn4w")
    private List<WebElement> productCards;
    
    @FindBy(xpath = "//a[contains(text(),'Apple-2022-10-9-inch-iPad-Wi-Fi') and @href[contains(@href, '/dp/B0BJMGXLYZ')]]")
    private WebElement ipad;
    
    private AmazonHomePage amazonHomePage = new AmazonHomePage();
    
    public TodayDealsTests(String screenshotDir) {
		super(screenshotDir);
	}

    @BeforeClass // Setup before running any tests
    public void setUp() {
    	// navigate to amazon first
    	amazonHomePage.goToAmazon();
        todayDeals = new TodayDealsPage(); // Creating an instance of TodayDeals page
        log.info("### ============================== TodayDealsTests ============================== ###");
        System.out.println("### ============================== TodayDealsTests ============================== ###");
    }

    @BeforeMethod // Setup before each test method
    public void navigateToTodaysDeals() {
    	try {
    		 System.out.println("		### navigateToTodaysDeals() -- Trying to navigate to amazon ###");
    		amazonHomePage.goToAmazon();
	        Assert.assertTrue(amazonHomePage.verifyLogoIsDisplayed(), "Amazon logo is not displayed on homepage");
	        System.out.println("		### navigateToTodaysDeals() -- navigation to amazon succesfully! ###");
            log.info("		### navigateToTodaysDeals() -- navigation to amazon succesfully! ###");
	  	} catch (Exception e) {
	  		System.out.println("		### Failure::navigateToTodaysDeals() -- navigation to amazon failed! ###");
            log.error("		### navigateToTodaysDeals() -- navigation to amazon failed! ###");
	  		takeScreenshotOnFailure("testFailure_"); //capture screen
		}
    	
        todayDeals.goToTodaysDeals(); // Navigate to Today's Deals page using the TodayDeals object
    }

    @Test(priority = 1) // Test case to verify navigation to Electronics category
    public void verifyNavigationToElectronicsCategory() {
    	
        todayDeals.clickOnElectronicsCategory(); // Click on the Electronics category button/link
        Assert.assertTrue(todayDeals.isCurrentCategoryElectronics(), "Failed to navigate to Electronics category");
    }

    @Test // Test case to check for a product with discount 50% or more
    public void verifyProductDiscounted50OrMore() {
        
    	boolean isDiscounted = false;
        isDiscounted = todayDeals.isDiscounted50OrMore(ipad); // Pass the product element to the method
        Assert.assertTrue(isDiscounted, "This product does not have a discount 50% or more");
    }
    
    @Test // Test case to check for a product with discount 50% or more
    public void verifyProductsDiscounted50OrMore() {
        boolean isDiscounted = false;
        WebElement productElement = driver.findElement(By.xpath(".//span[contains(text(), '% off')]"));
        isDiscounted = todayDeals.isDiscounted50OrMore(productElement); // Pass the product element to the method

        Assert.assertTrue(isDiscounted, "No product found with discount 50% or more");
    }
    
    

    @AfterTest // Cleanup after all tests
    public void tearDown() {
        driver.quit(); // Close the browser (assuming you want to quit after all tests)
    }
}
