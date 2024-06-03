package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import BaseClass.Base;
public class TodayDealsPage extends Base {
	 
    @FindBy(xpath = "//a[@href='/deals?ref_=nav_cs_gb']") // XPath for better reliability
    private WebElement todaysDealsLink;

    @FindBy(xpath = "//button[normalize-space()='Electronics']") // XPath for better reliability
    private WebElement electronicsButton;

    @FindBy(xpath = "//button[@class='Pill-module__pill_bVlh9i_q2QGLwbJa6Qwa Pill-module__selected_J8Ys84bZM_ANnsj2E7nj']") // CSS Selector for better maintainability 
    private WebElement filterByElectronicsCategory;
   
    @FindBy(className = "ProductCard-module__card_uyr_Jh7WpSkPx4iEpn4w")
    private List<WebElement> productCards;
    
    @FindBy(css = "#nav-subnav a span")
    private WebElement dealsPage;
    
  //*[@id="nav-subnav"]/a[1] 
//    private Actions actions; // for scrolling mainly
    public TodayDealsPage(WebDriver driver) {
        super("screenshots", driver);
        initializePageElements();
    }
//
    public TodayDealsPage goToTodaysDeals() {
        click(todaysDealsLink);
        return this;
    }

    public TodayDealsPage clickOnElectronicsCategory() {
        click(electronicsButton);
        return this;
    }
    public String getDiscountPercentage(WebElement discountElement) {
        return discountElement.getText().replaceAll("%", "");
    }

    public boolean isDiscounted50OrMore(WebElement discountElement) {
    	ProductDetailsPage productDetails =new ProductDetailsPage(discountElement, getDriver());
    	int discountValue = productDetails.getDiscountValue(discountElement);
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

    // get title
    public String getTitle() {
        String title =dealsPage.getText();
        return title;
    }
    // Takes a product card and finds the percentage discount value
    private int getDiscountFromProductCard(WebElement productCard) {
        // 
    	WebElement element =productCard.findElement(By.xpath(".//span[contains(text(), '% off')]"));
    	int discountValue = Integer.parseInt(getDiscountPercentage(element));
        return discountValue;
    }
    /*private int getDiscountFromProductCard(WebElement productCard) {
        return productCard.findElements(By.xpath(".//span[contains(text(), '% off')]"))
                .stream()
                .findFirst()
                .map(this::getDiscountPercentage)
                .map(Integer::parseInt)
                .orElse(0);
    }
*/
    
    public boolean isCurrentCategoryElectronics() {
    	
	    return isDisplayed(filterByElectronicsCategory); // Using the isDisplayed method from Base class
    }
    
    
}
