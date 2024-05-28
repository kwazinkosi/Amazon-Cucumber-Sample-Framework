package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import BaseClass.Base;

public class ProductDetailsPage extends Base {

	

	@FindBy(id = "add-to-cart-button")
	private WebElement addToCartButton;
	
	@FindBy(id="attach-sidesheet-checkout-button")
	private WebElement proceedToCheckout; // visible after adding an item to cart
	
	@FindBy(id="attach-sidesheet-view-cart-button")
	private WebElement cart; // visible after adding an item to cart
	
	public ProductDetailsPage() {
		super("screenshots");
	}

	// Add product to cart
    public void addToCart(WebElement product, double minDiscount) {
    	
        // Check for discount
        if (getDiscountValue(product) >= minDiscount) {
        	
            click(addToCartButton);
            // check if the proceedToCart element is displayed
            boolean isDisplayed = isDisplayed(cart);
            if (isDisplayed) {
            	
                System.out.println("		### proceedToCart is displayed. ###");
            	log.info("		### proceedToCheckout is displayed. ###");
                click(cart);
                
            } else {
            	
            	System.out.println("		### proceedToCart is not displayed. ###");
                log.error("		### proceedToCart is not displayed. ###");
            }
            
        } else {
            System.out.println("Product discount is less than " + minDiscount + "%");
        }
    }
    
    // Get the discount percentage
    private String getDiscountPercentage(WebElement discountElement) {
        
    	return discountElement.getText().replaceAll("%", "");
    }
    
    public int getDiscountValue(WebElement discountElement) {
        
    	int discountValue = 0;
        try {
        	
            String discountText = getDiscountPercentage(discountElement); 
            discountValue = Integer.parseInt(discountText);  
            System.out.println("		### getDiscountValue() -- discount value captured ###");
            log.info("		### getDiscountValue() -- discount value captured ###");
        } catch (NumberFormatException e) {
            // Handle the case where parsing fails (e.g., text doesn't contain a number)
        	System.out.println("		### Failure::getDiscountValue() -- discount value not captured ###");
            log.error("		  ###  Failure::getDiscountValue() -- discount value not captured ###");
            System.err.println("Error parsing discount value: " + e.getMessage());
            return -1; 
        }
        return discountValue;
    }
}
