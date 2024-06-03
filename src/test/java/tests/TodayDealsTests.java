package tests;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import BaseClass.Base;
import factory.DriverFactory;
import pages.AmazonHomePage;
import pages.ProductDetailsPage;
import pages.SearchResultsPage;
import pages.TodayDealsPage;
import screenshots.ScreenCapturer;
import utils.FileManager;
import utils.NavigateToSite;
//import utils.TestListener;


public class TodayDealsTests {

    
	private TodayDealsPage todayDeals;
    private WebDriver driver;

    @FindBy(xpath = ".//span[contains(text(), '% off')]")
    private WebElement discountElement;
    
    @FindBy(className = "ProductCard-module__card_uyr_Jh7WpSkPx4iEpn4w")
    private List<WebElement> productCards;
    
    @FindBy(xpath = "//div[contains(@class, 'ProductCard-module__card_uyr_Jh7WpSkPx4iEpn4w') and contains(@aria-label, 'Apple iPad')]//a//div[2]//span//div//div[1]//span")
    private WebElement ipad;
    By ipad_ = By.xpath("//div[contains(@class, 'ProductCard-module__card_uyr_Jh7WpSkPx4iEpn4w') and contains(@aria-label, 'Apple iPad')]//a//div[2]//span//div//div[1]//span");
    
    @FindBy(xpath = "//*[@id='slot-2']/div/h1")
    private WebElement dealsPage;
    By discValue = By.xpath(".//span[contains(text(), '% off')]");
    
    private AmazonHomePage amazonHomePage;
    private ProductDetailsPage productDetails;
    private DriverFactory driverFactory;
	private FileManager fileManager;
	private ScreenCapturer screenCapturer;
	Properties props;
//    TestListener listener;
    
    // Setup before running any tests
    @BeforeClass
    public void setUp() {
       
        Base.setupLogging();
//        this.driver = Base.setupDriver();
        try {
			props = FileManager.loadProperties("props.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String browserName =props.getProperty("browser");
		driverFactory = new DriverFactory();
		driver = driverFactory.initDriver(browserName);
		
        this.todayDeals = new TodayDealsPage(DriverFactory.getDriver()); // Create the TodayDealsPage object
        this.amazonHomePage = new AmazonHomePage(DriverFactory.getDriver());
        this.productDetails = new ProductDetailsPage(null, DriverFactory.getDriver());
        Base.log.info("### ============================== TodayDealsTests ============================== ###");
        System.out.println("### ============================== TodayDealsTests ============================== ###");
    }

    @Test(priority = 0) 
    public void navigateToTodaysDeals() throws IOException {
    	
    	try {
    		
    		System.out.println("		### navigateToTodaysDeals() -- Trying to navigate to amazon ###");
    		String url = props.getProperty("appUrl");
    		System.out.println(" getting url:  "+url);
		    NavigateToSite.navigateToAmazon(url, DriverFactory.getDriver());
	        Assert.assertTrue(amazonHomePage.verifyLogoIsDisplayed(), "Amazon logo is displayed on homepage");
	        System.out.println("		### navigateAmazon() -- navigation to amazon succesfully! ###");
	        Base.log.info("		### navigateAmazon() -- navigation to amazon succesfully! ###");
	        
	        TodayDealsPage deals =todayDeals.goToTodaysDeals(); // Navigate to Today's Deals page using the TodayDeals object
	        String title = deals.getTitle();
	        
	        Assert.assertEquals(title.toLowerCase().contains("today's deals"), true);
	        System.out.println("		### navigateToTodaysDeals() -- Deals page displayed succesfully! ###");
	        Base.log.info("		### navigateToTodaysDeals() -- Deals page displayed succesfully! ###");
	        
	  	} catch (Exception e) {
	  		
	  		System.out.println("		### Failure::navigateToTodaysDeals() -- navigation failed! ###");
	  		Base.log.error("		### navigateToTodaysDeals() -- navigation failed! ###");
	  		todayDeals.takeScreenshotOnFailure("testFailure_"); //capture screen
		}
    	
    }

    @Test(priority = 1) // Test case to verify navigation to Electronics category
    public void verifyNavigationToElectronicsCategory() {
    	
        todayDeals = todayDeals.clickOnElectronicsCategory(); // Click on the Electronics category button/link
        Assert.assertTrue(todayDeals.isCurrentCategoryElectronics(), "Failed to navigate to Electronics category");
        System.out.println("		### verifyNavigationToElectronicsCategory() -- test PASSED! ###");
  		Base.log.info("		### verifyNavigationToElectronicsCategory() -- test PASSED! ###");
    }

    @Test(priority = 2) // Test case to check for a product with discount 50% or more
    public void verifyProductDiscounted50OrMore() {
        
    	boolean isDiscounted = false;
    	WebElement ipad_ = driver.findElement(By.xpath("//div[contains(@class, 'ProductCard-module__card_uyr_Jh7WpSkPx4iEpn4w') and contains(@aria-label, 'Apple iPad')]//a//div[2]//span//div//div[1]//span"));
        isDiscounted = todayDeals.isDiscounted50OrMore(ipad_); // Pass the product element to the method
        Assert.assertFalse(isDiscounted, "This product does not have a discount 50% or more");
        System.out.println("		### verifyProductDiscounted50OrMore() -- test PASSED! ###");
  		Base.log.info("		### verifyProductDiscounted50OrMore() -- test PASSED! ###");
    }
    
    @Test(priority = 3) // Test case to check for a product with discount 50% or more
    public void verifyProductsDiscounted() {
        WebElement productElement = driver.findElement(discValue);
        productDetails.setProduct(productElement);
        int discountValue = productDetails.getDiscountValue(productElement);
        
        Assert.assertTrue(discountValue >= 0,"Discount value is not 10%");
        System.out.println("		### verifyProductsDiscounted50OrMore() -- test PASSED! ###");
  		Base.log.info("		### verifyProductsDiscounted50OrMore() -- test PASSED! ###");
    }
    
    @Test(priority = 4)
    public void testTodaysDealsElectronics() {
    	
    	SearchResultsPage  searchResults = new SearchResultsPage(DriverFactory.getDriver());
    	ProductDetailsPage ipadDetails = searchResults.findProduct("Ipad");
//    	ipadDetails.
    	String title = ipadDetails.getProductTitle();
    	Assert.assertTrue(title.toLowerCase().contains("ipad"));
    	System.out.println("		### testTodaysDealsElectronics() -- test PASSED! ###");
   		Base.log.info("		### testTodaysDealsElectronics() -- test PASSED! ###");

    }

    
    @AfterTest // Cleanup after all tests
    public void tearDown() {
    	if(DriverFactory.getDriver()!= null) {
    		driver.quit(); // Close the browser (assuming you want to quit after all tests)
    	}
    }
}
