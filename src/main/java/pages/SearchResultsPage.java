package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import BaseClass.Base;

public class SearchResultsPage extends Base {

	public SearchResultsPage(String screenshotDir) {
		super(screenshotDir);
	}

	
	@FindBy(css = ".s-search-results")
	private WebElement productListings;
    

    public ProductDetailsPage selectSonyHeadphonesWithDiscount(double minDiscount) {
        
    	for (WebElement element : findElements(productListings)) {
            
        	String productName = element.findElement(By.cssSelector(".s-product-title")).getText();
            if (productName.contains("Sony") && isDiscountedOver(element, minDiscount)) {
                click(element.findElement(By.linkText(productName))); // Adjust locator if needed
                return new ProductDetailsPage();
            }
        }
        throw new RuntimeException("No Sony headphones with discount over " + minDiscount + "% found");
    }

    private boolean isDiscountedOver(WebElement productElement, double minDiscount) {
        // Implement logic to check for discount element and extract discount percentage
        // This is a pseudo example, replace with actual logic for your website
        String discountText = productElement.findElement(By.className("discount-percentage")).getText();
        double discount = Double.parseDouble(discountText.substring(0, discountText.length() - 1));
        return discount > minDiscount;
    }
}
