package tests;

import BaseClass.Base;
import pages.ProductDetailsPage;
import pages.SearchResultsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.CartPage;

public class AmazonHomePageTests extends Base {

    
	private AmazonHomePage amazonHomePage;
    private SearchResultsPage searchResultsPage;
//    private ProductDetailsPage productDetailsPage;
    private CartPage cartPage;

	@DataProvider(name = "searchItems")
    public Object[][] testData() {
        return new Object[][] {
                {"headphones"},
//                {"ipad"},
                // to add more search terms later
        };
    }
    
	public AmazonHomePageTests() {
		super("screenshots");
	}

    @BeforeClass
    public void setup() {
        amazonHomePage = new AmazonHomePage();
        searchResultsPage = new SearchResultsPage();
//        productDetailsPage = new ProductDetailsPage(null);
        cartPage = new CartPage();
        log.info("\n//============== AmazonHomePage Tests =============\\");
        System.out.println("\n\n### ============================== AmazonHomePage Tests ============================== ###");
    }

    
    
    @Test(priority =1)
    public void testVerifyAmazonLogoIsDisplayed() {
//    	AmazonHomePage amazonHome =new AmazonHomePage();
    	try {
    		System.out.println("		### AmazonHomePageTests::testVerifyAmazonLogoIsDisplayed() -- testing logo ###");
    		amazonHomePage.goToAmazon();
	        Assert.assertTrue(amazonHomePage.verifyLogoIsDisplayed(), "Amazon logo is not displayed on homepage");
	        log.info("		### AmazonHomePageTests::testVerifyAmazonLogoIsDisplayed() -- logo displayed ###");
	        
	    } catch (Exception e) {
	  		System.out.println("		### AmazonHomePage::testVerifyAmazonLogoIsDisplayed() -- logo display failed ###");
	  		takeScreenshotOnFailure("LOGO-testFailure_"); //capture screen
	        log.error("		### AmazonHomePageTests::testVerifyAmazonLogoIsDisplayed() -- logo display failed ###");
		}
    }
    
    @Test(dataProvider = "searchItems", priority =2)
    public void testSearchForProductAndVerifyResult(String searchTerm) {
//    	AmazonHomePage amazonHome =new AmazonHomePage();
        // Search for the specified item
        amazonHomePage.goToAmazon();
        amazonHomePage.searchForItem(searchTerm);

        // Verify that the search box is displayed
        boolean isSearchBoxDisplayed = amazonHomePage.verifySearchBoxIsDisplayed();
        Assert.assertTrue(isSearchBoxDisplayed, "Search results header is not displayed for search term: " + searchTerm);

        // Verify search results title contains the search term
        String actualTitle = driver.findElement(By.cssSelector("h2.s-line-clamp-2")).getText();
        Assert.assertTrue(actualTitle.contains(searchTerm), "Search results title does not contain search term: " + searchTerm);
        
       
    }

    @Test(priority =3)
	public void verifySearchResultIsDisplayed() {
    	
    	System.out.println("		### AmazonHomePageTests::verifySearchResultIsDisplayed() -- verifying if search result is displayed()");
    	log.info("		### AmazonHomePage::verifySearchResultIsDisplayed() -- verifying if search result is displayed()");
//    	AmazonHomePage amazonHome =new AmazonHomePage();
    	amazonHomePage.goToAmazon();
        amazonHomePage.searchForItem("ipad");
    	boolean isSearchResultsDisplayed = amazonHomePage.verifySearchResultIsDisplayed();
    	Assert.assertTrue(isSearchResultsDisplayed, "### Search results header is not displayed ###");	
    }
    
    @Test(priority = 4)
    public void testVerifyGlobalWaitTime() {
    	
//    	AmazonHomePage amazonHome =new AmazonHomePage();
        int expectedWaitTime = amazonHomePage.getGlobalWaitTime();
        Assert.assertEquals(expectedWaitTime, 120, "Global wait time is not set to default value (10 seconds)");
    }
    
    @Test(priority = 5)
    public void verifySearchProductAndBuy() {
    	
//    	AmazonHomePage amazonHome = new AmazonHomePage();
//    	SearchResultsPage searchResults = new SearchResultsPage();
//    	CartPage cart =new CartPage();
    	String item ="ipad";
    	
    	
    	amazonHomePage.goToAmazon();
    	amazonHomePage.searchForItem(item);
    	// Find the product 
    	System.out.println("		### AmazonHomePageTests::verifySearchProductAndBuy() -- searching for item: "+item);
    	ProductDetailsPage product = searchResultsPage.findProduct(item);
    	//Add product to cart
    	product.addToCart(0.0);
    	if(!cartPage.isCartEmpty()) {
    		// proceed to checkout
    		cartPage.proceedToCheckout();
    	}
    }
}


