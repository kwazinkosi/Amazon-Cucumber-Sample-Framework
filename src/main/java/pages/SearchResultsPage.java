package pages;

import java.util.List;

import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import BaseClass.Base;

public class SearchResultsPage extends Base {

    @FindBy(css = ".s-search-results")
    private WebElement productListings;

    public ProductDetailsPage product = new ProductDetailsPage();
    
    public SearchResultsPage() {
        super("screenshots");
    }

    /*public ProductDetailsPage selectItemWithDiscount(String itemName, double minDiscount) {
        List<WebElement> productElements = productListings.findElements(By.className("s-result-item"));  // Assuming product listings are within <li> elements

        for (WebElement productElement : productElements) {
        	
        	WebElement element = productElement.findElement(By.cssSelector("h2 a "));
            String productName = element.findElement(By.className("a-text-narmal")).getText();
             
            System.out.println("SearchResultsPage::selectItemWithDiscount() --  product name is "+ productName);
            if (productName.contains(itemName)) {
                int discountValue = product.getDiscountValue(element);

                if (discountValue >= minDiscount) {
                    
                	Actions actions = new Actions(driver);
                    pause(1000);
                    actions.moveToElement(productElement).click().perform();  // Hover and click on the product
                    return new ProductDetailsPage(); 
                    
                } else {
                    System.out.println(itemName + " discount is less than " + minDiscount + "%");
                }
            }
        }
        System.out.println("Couldn't find item: " + itemName);
        return null; // Indicate item not found
    }*/

    public ProductDetailsPage selectItemWithDiscount(String itemName, double minDiscount) {
        List<WebElement> productElements = productListings.findElements(By.cssSelector(".s-result-item[data-component-type='s-search-result']"));  // Improved selector using data-component-type

        for (WebElement productElement : productElements) {
            WebElement titleElement = productElement.findElement(By.cssSelector("h2 a"));
            String productName = titleElement.findElement(By.className("a-text-narmal")).getText();

            log.info("Checking product: " + productName);
            if (productName.contains(itemName)) {
                int discountValue = product.getDiscountValue(titleElement);

                if (discountValue >= minDiscount) {
                	
                    Actions actions = new Actions(driver);
                    wait.until(ExpectedConditions.elementToBeClickable(productElement));
                    actions.moveToElement(productElement).click().perform();

                    return new ProductDetailsPage();
                } else {
                    log.info(itemName + " discount is less than " + minDiscount + "%");
                }
            }
        }

        log.error("Couldn't find item: " + itemName);
        return null;
    }

    
}
