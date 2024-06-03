package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import BaseClass.Base;

public class CartPage extends Base {

	@FindBy(xpath = "//*[@id='sc-buy-box-ptc-button']/span/input")
	private WebElement buyButton; ////input[@name='proceedToRetailCheckout']

	@FindBy(xpath = "sc-item-product-title-cont")
	private WebElement productTitle;
	
	@FindBy(xpath = "//*[@id='sc-retail-cart-container']")
	private WebElement cartPage;

	@FindBy(xpath = "//*[@id='sc-subtotal-label-activecart']")
	private WebElement subTotal;

	@FindBy(xpath = "//*[@id='sc-subtotal-amount-activecart']/span")
	private WebElement subTotalCartAmount;
	
	@FindBy(css ="span .a-dropdown-prompt")
	private WebElement quantity;
	
	@FindBy(xpath = "//*[@id='quantity_0']")
	private WebElement delete;
	private WebDriver driver;
	public CartPage(WebDriver driver) {

		super("screenshots", driver);
    	this.driver = driver;
		initializePageElements();
	}

	// ========== Verification methods =============\\
	public boolean isCartEmpty() {

		boolean isEmpty = false;
		if (isDisplayed(buyButton)) {
			isEmpty = true;
		}
		return isEmpty;
	}

	public boolean verifyItemInCart(String itemName) {

		String title = productTitle.getText();
		if (title.contains(itemName)) {
			return true;
		}
		return false;
	}

	public boolean verifyItemCount(int expectedCount) {
		String subTotalText = subTotal.getText(); // Get the text content of the subtotal element

		// Improved regular expression to capture digits and decimals (if applicable)
		String itemCountString = subTotalText.replaceAll("[^\\d.]", "");

		int actualItemCount;
		try {
			actualItemCount = Integer.parseInt(itemCountString); // Parse the extracted string to int
			System.out.println("		### VerifyItemCount() -- actual item count captured ###");
			log.info("		### VerifyItemCount() -- actual item count captured ###");
		} catch (NumberFormatException e) {
			// Handle cases where parsing fails (e.g., unexpected format)
			System.out.println("		### Failure::VerifyItemCount() -- actual item count not captured ###");
			log.error("		  ###  Failure::VerifyItemCount() -- actual item count not captured ###");
			System.err.println("Error parsing item count: " + e.getMessage());
			return false;
		}

		// Compare actual and expected count
		return actualItemCount == expectedCount;
	}

	// Remove Item from cart
	public void removeItemFromCart(String itemName) {
		
		if(isDisplayed(quantity)) {
			
			click(quantity); // click on the qauntity item
			click(delete);
		}
	}

	public boolean verifyCartPageIsDisplayed() {

		boolean isDisplayed = isDisplayed(buyButton);
		if (isDisplayed) {

			System.out.println("		### AmazonHomePage::verifyCartPageIsDisplayed() -- Logo is displayed ###");
			log.info("		### CartPage::verifyCartPageIsDisplayed() -- Search box is displayed. ###");
		} else {
			log.error("		### CartPage::verifyCartPageIsDisplayed() -- Search box is not displayed. ###");
			System.out.println("		### AmazonHomePage::verifyCartPageIsDisplayed() -- Logo is not displayed ###");
		}
		return isDisplayed;
	}

	public double getTotalCartPrice() {

		String totalPriceText = subTotalCartAmount.getText(); // Get the text content of the subTotalCartAmount element

		String priceString = totalPriceText.replaceAll("[^\\d.]", ""); // Extract digits and decimals

		double totalPrice;
		try {
			totalPrice = Double.parseDouble(priceString); // Parse the extracted string to double
			System.out.println("		### getTotalCartPrice() -- total price captured ###");
			log.info("		### getTotalCartPrice() -- total price captured ###");
		} catch (NumberFormatException e) {
			// Handle cases where parsing fails (e.g., unexpected format)
			System.out.println("		### Failure::getTotalCartPrice() -- total price not captured ###");
			log.error("		  ###  Failure::getTotalCartPrice() -- total price not captured ###");
			System.err.println("Error parsing total price: " + e.getMessage());
			return -1.0; // Or any value to indicate an error
		}

		return totalPrice;
	}
	
	public boolean isCartPage() {
		
		if(isDisplayed(cartPage)) {
			return true;
		}
		return false;
	}

	// If there are products in the cart buy
	public void proceedToCheckout() {
		// if proceed to buy button is visible, then cart is not empty

		// wait.until(ExpectedConditions.visibilityOf(buyButton)); // Wait for button
		// element
		boolean isDisplayed = isDisplayed(buyButton);
		if (isDisplayed) {

			System.out.println("		### buyButton is displayed. ###");
			log.info("		### buyButton is displayed. ###");
			click(buyButton);

		} else {

			System.out.println("		### proceedToCart is not displayed. ###");
			log.error("		### proceedToCart is not displayed. ###");
		}
	}
}
