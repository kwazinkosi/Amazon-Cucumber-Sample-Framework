package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import BaseClass.Base;
import hooks.AmazonHooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AmazonHomePage;
import pages.CartPage;
import pages.ProductDetailsPage;
import pages.SearchResultsPage;

public class AddToCartStepDefinitions{

	//	AmazonHomePage homePage;
	WebDriver driver;

//	public AddToCartStepDefinitions(String screenshotDir, WebDriver driver) {
//		super("screenshots", driver);
//		// TODO Auto-generated constructor stub
//	}

	//	public AddToCartStepDefinitions(AmazonHooks amazonHooks) {
	//        this.driver = amazonHooks.getDriver();
	//    }
	AmazonHomePage homePage;
	SearchResultsPage searchPage;
	ProductDetailsPage product;
	CartPage cartPage;
	
	@Given("I am on the Amazon website")
	public void i_am_on_the_amazon_website() {
		driver =new ChromeDriver();
		homePage =new AmazonHomePage(driver);
		String url = homePage.getUrl();
		homePage.visitPage(url);
	    System.out.println("step 1");
//	    Assert.assertTrue(homePage.verifyLogoIsDisplayed(), "Logo is not displayed, page still loading");
//		Assert.assertTrue(homePage.verifySearchBoxIsDisplayed());
	}
	

	@Given("I search for {string} and select the first search result")
	public void i_search_for(String product) {
		
		System.out.println("step 1");
//		homePage.searchForItem(product);
//		Assert.assertTrue(searchPage.isSearch(), "This is not the search results page");
	}

	@When("I click on the {string} button, the product should be added to my cart")
	public void i_click_on_the_button(String addToCart) {
		
		Assert.assertTrue(product.isSliderDisplayed(), "The slider is not displayed");
		product.addToCart(0, product);
		
	}

	@Then("the product should be added to my cart")
	public void the_product_should_be_added_to_my_cart() {
		System.out.println("step 4");
		product.goToCart();
		Assert.assertTrue(cartPage.isCartPage(), "This is not the cart page");
	}

	@Given("I have an item in my cart")
	public void i_have_an_item_in_my_cart() {
		System.out.println("step 1");
		Assert.assertTrue(cartPage.isCartPage(), "This is not the cart page");
	}

	@When("I navigate to the cart page")
	public void i_navigate_to_the_cart_page() {
		System.out.println("step 5");
		product.goToCart();
		Assert.assertTrue(cartPage.isCartPage(), "This is not the cart page");
	}

	@When("I click on the {string} button for the item {string}")
	public void i_click_on_the_button_for_the_item(String remove, String item) {
		System.out.println("step 6");
		boolean isInCart = cartPage.verifyItemInCart(item);
		Assert.assertTrue(isInCart, "The product is in the cart");
	}

	@Then("the item {string} should be removed from my cart")
	public void the_item_should_be_removed_from_my_cart(String item) {
		
		System.out.println("step 7");
		cartPage.removeItemFromCart(item);
		Assert.assertTrue(cartPage.isCartEmpty(), "The cart is not empty");
	}
}
