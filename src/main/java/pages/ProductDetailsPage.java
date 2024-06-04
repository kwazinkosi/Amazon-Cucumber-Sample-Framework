package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import BaseClass.Base;
import factory.DriverFactory;

public class ProductDetailsPage extends Base {

	@FindBy(css = "#rightCol #buybox #addToCart_feature_div .a-button-stack") // "//*[@id='add-to-cart-button']")
	private WebElement addToCartButton;
	
	@FindBy(id="attach-sidesheet-checkout-button")
	private WebElement proceedToCheckout; // visible after adding an item to cart//
	
	@FindBy(id="attach-sidesheet-view-cart-button")
	private WebElement cart; // visible after adding an item to cart
	
	@FindBy(css = ".a-price-whole")
	private WebElement productPrice;
	
	@FindBy(css = "[data-cy='title-recipe'] h2 a span")
	private WebElement title;
	
	@FindBy(xpath = "//*[@id='dp-container']")
	private WebElement pTitle;
	
	@FindBy(css = "[data-cy='price-recipe']  .a-letter-space~span")
	private WebElement productDiscount;
	
	
	private WebElement product;
	private String productTitle;
	private int discountValue;
	private double price;
//	private WebDriver driver;
	public ProductDetailsPage(WebElement product, WebDriver driver) {
		
		super( "screenshots", driver);
//    	this.driver = driver;
		initializePageElements();
		this.product = product;	
		this.discountValue = 0;
		this.price = 0.0;
		this.productTitle = "";
	}

	// Add product to cart
    public void addToCart(double minDiscount, ProductDetailsPage productDetails) {
        // Check for discount
        if (productDetails.getProductDiscount() >= minDiscount) {

        	JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();
        	js.executeScript("window.scrollBy(0,1000)");
        	hoverAndClick(DriverFactory.getDriver(), addToCartButton );
        	submit(addToCartButton);
        	click(addToCartButton);
      
            // check if the proceedToCart element is displayed
            boolean displayed = isDisplayed(cart);
            if (displayed) {
            	
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
    
    public void addToCart(WebElement element) {
    	
		WebElement addButton =element.findElement(By.cssSelector(".a-section.puis-atcb-add-container.aok-inline-block"));
		addButton.click();
	}
    
    // Get the discount percentage
    private String getDiscountPercentage(WebElement discountElement) {
        
    	
    	String discountText = discountElement.getText();
    	String digitsOnly = discountText.replaceAll("[^0-9]", "");
    	return digitsOnly;
    }
    
    public void goToCart() {
    	submit(cart);
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
    
    public void initProduct() {
    	// price init
    	this.price = Integer.parseInt(productPrice.getText().replaceAll("[^0-9,]", ""));
    	
    	//title init
    	this.productTitle = title.getText();
    	
    	//discount init
    	this.discountValue = Integer.parseInt(productDiscount.getText().replaceAll("[^0-9]", ""));	
    }
    
    public boolean isProductPage() {
    	if(isDisplayed(pTitle)) {
    		return true;
    	}
    	return false;
    }
    
    public boolean isSliderDisplayed() {
    	
    	if(isDisplayed(proceedToCheckout)) {
    		return true;
    	}
    	return false;
    }
    //================== Getters and Setters ==================//
    public void setProduct(WebElement prdt) {
    	product =prdt;
    }
    

    public void setProductDiscount(int discount) {
    	this.discountValue = discount;
    }

    public void setProductTitle(String title) {
    	this.productTitle = title;
    }
    
    public WebElement getProduct() {
        if (product == null) {
        	System.out.println("		### getProduct() -- not initialised ###");
            throw new NullPointerException("Product element is null. Please initialize it before accessing.");
        }
        System.out.println("		### getProduct() --  product returned successfully ###");
        return this.product;
    }
    
    public String getProductTitle() {
    	return this.productTitle;
    }
    
    public int getProductDiscount() {
    	return this.discountValue;
    }
    
    public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
    
    // Takes a product listing card and extracts the title
    public String getProductTitle(WebElement productListing) {
    	
    	WebElement titleElement = productListing.findElement(By.cssSelector("h2 a span"));
		String productName = titleElement.getText();
		return productName;
    }
}
