package tests;

import pages.ProductDetailsPage;
import pages.SearchResultsPage;
import utils.NavigateToSite;
//import utils.TestListener;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import BaseClass.Base;
import pages.AmazonHomePage;
import pages.CartPage;

public class AmazonHomePageTests {

    
	private AmazonHomePage amazonHomePage;
    private SearchResultsPage searchResultsPage;
//    private ProductDetailsPage productDetailsPage;
    private CartPage cartPage;
    private WebDriver driver;
//    TestListener listener;

	@DataProvider(name = "searchItems")
    public Object[][] testData() {
        return new Object[][] {
                {"headphones"},
//                {"ipad"},
                // to add more search terms later
        };
    }
    
    @BeforeClass
    public void setup() {
    	
        Base.setupLogging();
        Base.setupDriver();
//        listener= new TestListener(); 
        this.driver = amazonHomePage.getDriver();
        amazonHomePage = new AmazonHomePage(driver);
        searchResultsPage = new SearchResultsPage(driver);
//        productDetailsPage = new ProductDetailsPage(null);
        cartPage = new CartPage(driver);
        Base.log.info("\n//============== AmazonHomePage Tests =============\\");
        System.out.println("\n\n### ============================== AmazonHomePage Tests ============================== ###");
    }

    
    
    @Test(priority =1)
    public void testVerifyAmazonLogoIsDisplayed(AmazonHomePage amazonHome) throws IOException {
    	
    	try {
    		System.out.println("		### AmazonHomePageTests::testVerifyAmazonLogoIsDisplayed() -- testing logo ###");
    		
//    		amazonHomePage.setTestListener(listener);
    		String url = amazonHomePage.getUrl();
    		
		    NavigateToSite.navigateToAmazon(url, driver);
	        Assert.assertTrue(amazonHomePage.verifyLogoIsDisplayed(), "Amazon logo is not displayed on homepage");
	        Base.log.info("		### AmazonHomePageTests::testVerifyAmazonLogoIsDisplayed() -- logo displayed ###");
	        
	    } catch (Exception e) {
	  		System.out.println("		### AmazonHomePage::testVerifyAmazonLogoIsDisplayed() -- logo display failed ###");
	  		amazonHomePage.takeScreenshotOnFailure("LOGO-testFailure_"); //capture screen
	  		Base.log.error("		### AmazonHomePageTests::testVerifyAmazonLogoIsDisplayed() -- logo display failed ###");
	        throw e;
		}
    }
    
    @Test(dataProvider = "searchItems", priority =2)
    public void testSearchForProductAndVerifyResult(String searchTerm) {
//    	AmazonHomePage amazonHome =new AmazonHomePage();
        // Search for the specified item
    	String url = amazonHomePage.getUrl();
	    NavigateToSite.navigateToAmazon(url, driver);
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
    	Base.log.info("		### AmazonHomePage::verifySearchResultIsDisplayed() -- verifying if search result is displayed()");
    	
		String url = amazonHomePage.getUrl();
	    NavigateToSite.navigateToAmazon(url, driver);
        amazonHomePage.searchForItem("ipad");
    	boolean isSearchResultsDisplayed = amazonHomePage.verifySearchResultIsDisplayed();
    	Assert.assertTrue(isSearchResultsDisplayed, "### Search results header is not displayed ###");	
    }
    
    @Test(priority = 4)
    public void testVerifyGlobalWaitTime() {
    	

    }
    
    @Test(priority = 5)
    public void verifySearchProductAndBuy() {
    	
//    	AmazonHomePage amazonHome = new AmazonHomePage();
//    	SearchResultsPage searchResults = new SearchResultsPage();
//    	CartPage cart =new CartPage();
    	String item ="ipad";
    	
    	String url = amazonHomePage.getUrl();
	    NavigateToSite.navigateToAmazon(url, driver);
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


