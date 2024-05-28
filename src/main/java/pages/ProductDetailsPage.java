package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import BaseClass.Base;

public class ProductDetailsPage extends Base {

	

	@FindBy(id = "add-to-cart-button")
	private WebElement addToCartButton;
	
	
	public ProductDetailsPage(String screenshotDir) {
		super(screenshotDir);
	}

    public void addToCartIfDiscountedOver(double minDiscount) {
    	
        // Check for discount using existing logic (from SearchResultsPage)
        if (isDiscountedOver(driver.findElements(By.id("productDetails")), minDiscount)) {
            click(addToCartButton);
        } else {
            System.out.println("Product discount is less than " + minDiscount + "%");
        }
    }
}
