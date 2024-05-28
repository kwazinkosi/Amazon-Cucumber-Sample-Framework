package pages;

import BaseClass.Base;

public class AmazonTest extends Base {
	

    public AmazonTest() {
		super("screenshots");
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
    	
    	setupLogging();
		AmazonHomePage amazonHomePage = new AmazonHomePage();
		SearchResultsPage searchPage = new SearchResultsPage();
		
		// Initialize WebDriver and navigate to Amazon
		amazonHomePage.goToAmazon();
		
		// Search for your desired item
		amazonHomePage.searchForItem("stm32");
		
		System.out.println("AmazonTest::main() -- Done searching");
		
		// Add your address-filling automation logic here
		// ...
		
		// Close the browser
		// driver.quit();
    }
}

