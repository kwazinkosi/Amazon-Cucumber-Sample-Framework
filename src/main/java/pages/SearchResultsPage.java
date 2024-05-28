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

//    public ProductDetailsPage product = new ProductDetailsPage();
    
    public SearchResultsPage() {
        super("screenshots");
    }

    

    public ProductDetailsPage selectItemWithDiscount(String itemName, double minDiscount) {
    	 ProductDetailsPage productPage;
        List<WebElement> productElements = productListings.findElements(By.cssSelector(".s-result-item[data-component-type='s-search-result']")); 

        for (WebElement productElement : productElements) {
        	
        	productPage = new ProductDetailsPage(productElement);
            WebElement titleElement = productElement.findElement(By.cssSelector("h2 a"));
            String productName = titleElement.findElement(By.className("a-text-normal")).getText();

            log.info("		### SearchResultsPage::selectItemWithDiscount() -- Checking product: " + productName);
            System.out.println("		### SearchResultsPage::selectItemWithDiscount() -- Checking product: " + productName);
            if (productName.contains(itemName)) {
                int discountValue = productPage.getDiscountValue(titleElement);

                if (discountValue >= minDiscount) {
                	
                    Actions actions = new Actions(driver);
                    wait.until(ExpectedConditions.elementToBeClickable(productElement));
                    actions.moveToElement(productElement).click().perform();
                    System.out.println("		### SearchResultsPage::selectItemWithDiscount() -- Returning item: " + productName);
                    log.info("		### SearchResultsPage::selectItemWithDiscount() -- Returning item: " + productName);
                    //Return page 
                    return new ProductDetailsPage(productElement);
                } else {
                    log.info(itemName + " discount is less than " + minDiscount + "%");
                }
            }
        }

        log.error("Couldn't find item: " + itemName);
        return null;
    }

    //Find item
    public ProductDetailsPage findProduct(String itemName) {
    	
    	return selectItemWithDiscount(itemName, 0.0);
    }
    
}
