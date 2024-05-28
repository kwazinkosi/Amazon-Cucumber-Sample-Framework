package pages;



public class AmazonTest {
	

    public static void main(String[] args) {
    	
		AmazonHomePage amazonHomePage = new AmazonHomePage();
		
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

