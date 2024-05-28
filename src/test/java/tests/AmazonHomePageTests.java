package tests;

import BaseClass.Base;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.AmazonHomePage;

public class AmazonHomePageTests extends Base {

    
	private AmazonHomePage amazonHomePage;
	public AmazonHomePageTests(String screenshotDir) {
		super(screenshotDir);
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

    @Test
    public void testSearchForProductAndVerifyResults() {
    	
    	boolean isSearchBoxDisplayed = amazonHomePage.verifySearchBoxIsDisplayed();
        
        Assert.assertTrue(isSearchBoxDisplayed, "### Search results header is not displayed ###");
        // If the assertion passes, continue with the test
        String item = "Headphones"; // TODO: use @DataProvider
        amazonHomePage.goToAmazon();
        amazonHomePage.searchForItem(item);
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

