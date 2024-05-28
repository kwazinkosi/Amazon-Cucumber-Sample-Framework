package pages;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;

import BaseClass.Base;



public class AmazonHomePage extends Base {

    @FindBy(id = "twotabsearchtextbox")
//    @CacheLookup
    private WebElement searchBox;
    
    @FindBy(id = "nav-logo-sprites")
    private WebElement amazonLogo;
    
    @FindBy(id = "search")
    private WebElement searchResults;
    
    
    public AmazonHomePage() {
    	
		super("screenshots"); // Call Base class constructor (implicitly calls setupDriver)
        PageFactory.initElements(driver, this); // Initialize web elements
    }
    public void goToAmazon() {
    	pause(1000);
    	log.info("		### Navigating to Amazon homepage. ###");
        visitPage(getApplicationUrl()); // Using Base class visitPage method
        log.info("		### Amazon homepage opened successfully. ###");
    }

    public void searchForItem(String item) {
    	
    	log.info("		### Searching for item: " + item + " ###");
    	typeText(searchBox, item);
        searchBox.sendKeys(Keys.ENTER);
        log.info("		### Search submitted for item: " + item + " ###");
        // TODO: return searchResultsPage instead
    }
    
    public boolean verifyLogoIsDisplayed() {
    	
    	boolean isDisplayed = isDisplayed(amazonLogo);
        if (isDisplayed) {
            log.info("		### Logo is displayed. ###");
        } else {
            log.error("		### Logo is not displayed. ###");
        }
        return isDisplayed;
    }

    public boolean verifySearchBoxIsDisplayed() {
    	
    	boolean isDisplayed = isDisplayed(searchBox);
        if (isDisplayed) {
            
        	 log.info("		### Search box is displayed. ###");
        } else {
            log.error("		### Search box is not displayed. ###");
        }
        return isDisplayed;
    }
    
	public boolean verifySearchResultIsDisplayed() {
	    	
    	boolean isDisplayed = isDisplayed(searchResults);
        if (isDisplayed) {
            log.info("		### Search results page displayed. ###");
        } else {
            log.error("		### Search results page not displayed. ###");
        }
        return isDisplayed;
    }
}

