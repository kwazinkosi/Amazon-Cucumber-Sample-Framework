package tests;

import BaseClass.Base;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AmazonHomePage;

public class AmazonHomePageTests extends Base {

    
	private AmazonHomePage amazonHomePage;
	
	@DataProvider(name = "searchItems")
    public Object[][] testData() {
        return new Object[][] {
                {"stm32"},
                {"raspberry pi"},
                // to add more search terms later
        };
    }
    
	public AmazonHomePageTests() {
		super("screenshots");
	}

    @BeforeClass
    public void setup() {

		log.info("//============== AmazonHomePage Tests =============\\");
        amazonHomePage = new AmazonHomePage(); // No need for getDriver()
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        log.info("//============== AmazonHomePage Tests End =============\\");
    }

    @Test
    public void testVerifyAmazonLogoIsDisplayed() {
    	try {
	        amazonHomePage.goToAmazon();
	        Assert.assertTrue(amazonHomePage.verifyLogoIsDisplayed(), "Amazon logo is not displayed on homepage");
	  	} catch (Exception e) {
	  		takeScreenshotOnFailure("testFailure_"); //capture screen
		}
    }
    
    @Test(dataProvider = "searchItems")
    public void testSearchForProductAndVerifyResult(String searchTerm) {
    	
        // Search for the specified item
        amazonHomePage.goToAmazon();
        amazonHomePage.searchForItem(searchTerm);

        // Verify that the search box is displayed
        boolean isSearchBoxDisplayed = amazonHomePage.verifySearchBoxIsDisplayed();
        Assert.assertTrue(isSearchBoxDisplayed, "Search results header is not displayed for search term: " + searchTerm);

        // Add additional verification,  check search results
        
       
    }


    @Test
	public void verifySearchResultIsDisplayed() {
	    	
    	boolean isSearchResultsDisplayed = amazonHomePage.verifySearchResultIsDisplayed();
    	Assert.assertTrue(isSearchResultsDisplayed, "### Search results header is not displayed ###");
    	
    }
    
    @Test
    public void testVerifyGlobalWaitTime() {
    	
        int expectedWaitTime = amazonHomePage.getGlobalWaitTime();
        Assert.assertEquals(expectedWaitTime, 10, "Global wait time is not set to default value (10 seconds)");
    }
}

