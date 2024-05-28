package tests;

import BaseClass.Base;
import pages.ProductDetailsPage;
import pages.SearchResultsPage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.CartPage;

public class AmazonHomePageTests extends Base {

    
//	private AmazonHomePage amazonHomePage;
	
	@DataProvider(name = "searchItems")
    public Object[][] testData() {
        return new Object[][] {
                {"headphones"},
                {"ipad"},
                // to add more search terms later
        };
    }
    
	public AmazonHomePageTests() {
		super("screenshots");
	}

    @BeforeClass
    public void setup() {

		log.info("\n//============== AmazonHomePage Tests =============\\");
        System.out.println("\n### ============================== AmazonHomePage Tests ============================== ###");
//        amazonHomePage = new AmazonHomePage(); // No need for getDriver()
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
        	System.out.println("		### AmazonHomePage::tearDown() -- Quiting ###");
            driver.quit();
        }
        System.out.println("### ============================== AmazonHomePage Tests End ============================== ###");
        log.info("//============== AmazonHomePage Tests End =============\\");
    }

    @AfterMethod
    public void tear() {
    	if (driver != null) {
        	System.out.println("		### AmazonHomePage::tear() -- Destroying page ###");
            driver.quit();
        }
    }
    
    @Test
    public void testVerifyAmazonLogoIsDisplayed() {
    	AmazonHomePage amazonHome =new AmazonHomePage();
    	try {
    		System.out.println("		### AmazonHomePageTests::testVerifyAmazonLogoIsDisplayed() -- testing logo ###");
    		amazonHome.goToAmazon();
	        Assert.assertTrue(amazonHome.verifyLogoIsDisplayed(), "Amazon logo is not displayed on homepage");
	        log.info("		### AmazonHomePageTests::testVerifyAmazonLogoIsDisplayed() -- logo displayed ###");
	        
	    } catch (Exception e) {
	  		System.out.println("		### AmazonHomePage::testVerifyAmazonLogoIsDisplayed() -- logo display failed ###");
	  		takeScreenshotOnFailure("LOGO-testFailure_"); //capture screen
	        log.error("		### AmazonHomePageTests::testVerifyAmazonLogoIsDisplayed() -- logo display failed ###");
		}
    }
    
    @Test(dataProvider = "searchItems")
    public void testSearchForProductAndVerifyResult(String searchTerm) {
    	AmazonHomePage amazonHome =new AmazonHomePage();
        // Search for the specified item
        amazonHome.goToAmazon();
        amazonHome.searchForItem(searchTerm);

        // Verify that the search box is displayed
        boolean isSearchBoxDisplayed = amazonHome.verifySearchBoxIsDisplayed();
        Assert.assertTrue(isSearchBoxDisplayed, "Search results header is not displayed for search term: " + searchTerm);

        // Add additional verification,  check search results
        
       
    }

    @Test
	public void verifySearchResultIsDisplayed() {
    	
    	System.out.println("		### AmazonHomePageTests::verifySearchResultIsDisplayed() -- verifying if search result is displayed()");
    	log.info("		### AmazonHomePage::verifySearchResultIsDisplayed() -- verifying if search result is displayed()");
    	AmazonHomePage amazonHome =new AmazonHomePage();
    	amazonHome.goToAmazon();
        amazonHome.searchForItem("ipad");
    	boolean isSearchResultsDisplayed = amazonHome.verifySearchResultIsDisplayed();
    	Assert.assertTrue(isSearchResultsDisplayed, "### Search results header is not displayed ###");	
    }
    
    @Test
    public void testVerifyGlobalWaitTime() {
    	
    	AmazonHomePage amazonHome =new AmazonHomePage();
        int expectedWaitTime = amazonHome.getGlobalWaitTime();
        Assert.assertEquals(expectedWaitTime, 10, "Global wait time is not set to default value (10 seconds)");
    }
    
    @Test
    public void verifySearchProductAndBuy() {
    	
    	AmazonHomePage amazonHome = new AmazonHomePage();
    	SearchResultsPage searchResults = new SearchResultsPage();
    	CartPage cart =new CartPage();
    	String item ="ipad";
    	
    	
    	amazonHome.goToAmazon();
    	amazonHome.searchForItem(item);
    	// Find the product 
    	System.out.println("		### AmazonHomePageTests::verifySearchProductAndBuy() -- searching for item: "+item);
    	ProductDetailsPage product = searchResults.findProduct(item);
    	//Add product to cart
    	product.addToCart(0.0);
    	if(!cart.isCartEmpty()) {
    		// proceed to checkout
    		cart.proceedToCheckout();
    	}
    	
    	
    }
}

