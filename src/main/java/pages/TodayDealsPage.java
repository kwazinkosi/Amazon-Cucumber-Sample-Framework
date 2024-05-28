package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import BaseClass.Base;
public class TodayDealsPage extends Base {

    @FindBy(xpath = "//a[@href='https://www.amazon.co.za/deals?ref_=nav_cs_gb']") // XPath for better reliability
    private WebElement todaysDealsLink;

    @FindBy(xpath = "//button[normalize-space()='Electronics']") // XPath for better reliability
    private WebElement electronicsButton;

    @FindBy(css = "a[contains(text(),'Electronics')]") // CSS Selector for better maintainability
    private WebElement electronicsCategoryLink;
   
    @FindBy(className = "ProductCard-module__card_uyr_Jh7WpSkPx4iEpn4w")
    private List<WebElement> productCards;
    
//    private Actions actions; // for scrolling mainly
    public TodayDealsPage() {
        super("screenshots");
        PageFactory.initElements(getDriver(), this); // Initialize web elements
    }

    public void goToTodaysDeals() {
       click(todaysDealsLink);
    }

    public void clickOnElectronicsCategory() {
    	
    	click(electronicsButton); // Assuming the button navigates to the Electronics category
    }

    public String getDiscountPercentage(WebElement discountElement) {
        return discountElement.getText().replaceAll("%", "");
    }

    public boolean isDiscounted50OrMore(WebElement discountElement) {
        int discountValue = Integer.parseInt(getDiscountPercentage(discountElement));
        return discountValue >= 50;
    }

    // Takes as input the minimum discount and returns a list of all the products with specified minimum discount value
    public List<WebElement> findDiscountedProducts(int minDiscount) { // Minimum discount percentage
    	
        List<WebElement> discountedProducts = new ArrayList<>();
        for (WebElement productCard : productCards) {
        	
            int discount = getDiscountFromProductCard(productCard); 
            if (discount >= minDiscount) {
                discountedProducts.add(productCard);
            }
        }
        return discountedProducts;
    }

    // Takes a product card and finds the percentage discount value
    private int getDiscountFromProductCard(WebElement productCard) {
        // 
    	WebElement element =productCard.findElement(By.xpath(".//span[contains(text(), '% off')]"));
    	int discountValue = Integer.parseInt(getDiscountPercentage(element));
        return discountValue;
    }
    
    public boolean isCurrentCategoryElectronics() {
    	
	    return isDisplayed(electronicsCategoryLink); // Using the isDisplayed method from Base class
    }
}
