package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import BaseClass.Base;

public class SearchResultsPage extends Base {

//	@FindBy(css = ".s-search-results.s-result-list")
//	private List<WebElement> productListings; // list
	@FindBy(css = ".s-search-results.s-result-list>[data-component-type='s-search-result']")
	private List<WebElement> productListings; // list //"
	
	@FindBy(css = ".s-search-results.s-result-list>[data-component-type='s-search-result'] h2 a span")
	private WebElement productListingTitle;
	
	@FindBy(id = ".s-result-list.s-search-results")
	private WebElement searchResults;
	
	@FindBy(id = "twotabsearchtextbox")
	private WebElement searchBox;

//    public ProductDetailsPage product = new ProductDetailsPage();
	private WebDriver driver;

	public SearchResultsPage(WebDriver driver) {

		super("screenshots", driver);
		this.driver = driver;
		initializePageElements();
	}

	// TODO: to improve the function, currently used only by searching
	public ProductDetailsPage selectItemWithDiscount(String itemName, double minDiscount) {

		// if not of search page, then search item
		if(!isSearch() && isDisplayed(searchBox)) {
		
				searchForItem(itemName); //navigate to search page
		}
		
		else {
				log.error("		### SearchResultsPage::selectItemWithDiscount() -- Search box not available " );
				System.out.println("		### SearchResultsPage::selectItemWithDiscount() -- Search box not available ");
				// throw error
				return null;
		}
		
		ProductDetailsPage productPage = new ProductDetailsPage(null, driver);
		WebElement temp = null;
		for (WebElement productElement : productListings) {

			productPage.setProduct(productElement);
			WebElement titleElement = productElement.findElement(By.cssSelector("h2 a span"));
			WebElement discountElement = productElement.findElement(By.cssSelector("[data-cy='price-recipe'] a ~span~span"));
			String productName = titleElement.getText();
			productPage.setProductTitle(productName);

			log.info("		### SearchResultsPage::selectItemWithDiscount() -- Checking product: " + productName);
			System.out.println("		### SearchResultsPage::selectItemWithDiscount() -- Checking product: " + productName);
			if (productName.toLowerCase().contains(itemName.toLowerCase())) {
				
				int discountValue = productPage.getDiscountValue(discountElement);
				productPage.setProductDiscount(discountValue);
				if (discountValue >= minDiscount) {
					
					temp = productElement;
					productPage.setProduct(productElement);
					break;
				} 
				else {
					log.info(itemName + " discount is less than " + minDiscount + "%");
					System.out.println(itemName + " discount is less than " + minDiscount + "%");
				}
			}
		}
		
		if(temp ==null) {
			
			log.error("Couldn't find item: " + itemName);
			System.out.println("Couldn't find item: " + itemName);
			return null;
		}
		click(temp);
		return productPage;	
	}

	public void searchForItem(String item) {

		log.info("		### SearchResultsPage::searchForItem() -- Searching for item: " + item + " ###");
		System.out.println("		### AmazonHomePage::searchForItem() -- Searching for item: " + item + " ###");
		typeText(searchBox, item);
		searchBox.sendKeys(Keys.ENTER);

		log.info("		### SearchResultsPage::searchForItem() -- Search submitted for item: " + item + " ###");
		System.out.println("		### SearchResultsPage::searchForItem() -- Search submitted for item:: " + item + " ###");
		// TODO: return searchResultsPage instead
	}

	// check if on search page
	public boolean isSearch() {

		if (isDisplayed(searchResults)) {
			return true;
		}
		return false;
	}

	// Find item
	public ProductDetailsPage findProduct(String itemName) {

		return selectItemWithDiscount(itemName, 0.0);
	}

}
