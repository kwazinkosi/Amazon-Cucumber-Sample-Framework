package pages;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

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

    	log.info("		### Navigating to Amazon homepage. ###");
        visitPage(getApplicationUrl()); // Using Base class visitPage method
        log.info("		### Amazon homepage opened successfully. ###");
    }

    public void searchForItem(String item) {
    	
    	log.info("		### AmazonHomePage::searchForItem() -- Searching for item: " + item + " ###");
    	System.out.println("		### AmazonHomePage::searchForItem() -- Searching for item: " + item + " ###");
    	typeText(searchBox, item);
        searchBox.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(searchResults));
        
        log.info("		### AmazonHomePage::searchForItem() -- Search submitted for item: " + item + " ###");
        System.out.println("		### AmazonHomePage::searchForItem() -- Search submitted for item:: " + item + " ###");
        // TODO: return searchResultsPage instead
    }
    
    public boolean verifyLogoIsDisplayed() {
    	
    	boolean isDisplayed = isDisplayed(amazonLogo);
        if (isDisplayed) {
        	System.out.println("		### AmazonHomePage::verifyLogoIsDisplayed() -- Logo is displayed ###");
            log.info("		### AmazonHomePage::verifyLogoIsDisplayed() -- Logo is displayed. ###");
        } else {
            log.error("		###  AmazonHomePage::verifyLogoIsDisplayed() -- Logo is not displayed. ###");
            System.out.println("		### AmazonHomePage::verifyLogoIsDisplayed() -- Logo is not displayed ###");
        }
        return isDisplayed;
    }

    public boolean verifySearchBoxIsDisplayed() {
    	
    	boolean isDisplayed = isDisplayed(searchBox);
        if (isDisplayed) {
            
        	 System.out.println("		### AmazonHomePage::verifySearchBoxIsDisplayed() -- Logo is displayed ###");
        	 log.info("		### AmazonHomePage::verifySearchBoxIsDisplayed() -- Search box is displayed. ###");
        } else {
            log.error("		### AmazonHomePage::verifySearchBoxIsDisplayed() -- Search box is not displayed. ###");
            System.out.println("		### AmazonHomePage::verifySearchBoxIsDisplayed() -- Logo is not displayed ###");
        }
        return isDisplayed;
    }
    
	public boolean verifySearchResultIsDisplayed() {
	    	
    	boolean isDisplayed = isDisplayed(searchResults);
        if (isDisplayed) {
            log.info("		### AmazonHomePage::verifySearchResultIsDisplayed() -- Search results page displayed. ###");
            System.out.println("		### AmazonHomePage::verifySearchResultIsDisplayed() -- Search results page displayed. ###");
        } else {
        	System.out.println("		### AmazonHomePage::verifySearchResultIsDisplayed() -- Search results page displayed. ###");
            log.error("		### AmazonHomePage::verifySearchResultIsDisplayed() -- Search results page not displayed. ###");
        }
        return isDisplayed;
    }
}

