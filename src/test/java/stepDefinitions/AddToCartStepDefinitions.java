package stepDefinitions;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import factory.DriverFactory;
import io.cucumber.java.en.And;
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
	AmazonHomePage homePage = new AmazonHomePage(DriverFactory.getDriver());
	SearchResultsPage searchPage =new SearchResultsPage(DriverFactory.getDriver());
	Properties props;
	
	ProductDetailsPage product;
	CartPage cartPage;
	
	@Given("I am on the Amazon website")
	public void i_am_on_the_amazon_website() {
		
		String url = "https://amazon.in";
		homePage.visitPage(url);
		Assert.assertTrue(homePage.verifyLogoIsDisplayed(), "Logo is not displayed, page still loading");
		Assert.assertTrue(homePage.verifySearchBoxIsDisplayed());
		System.out.println("		###SearchSteps::user_is_on_the_home_or_any_page_with_search_bar() -- PASSED!! ###");
	}
	

	@When("I search for {string} and select the first search result")
	public void i_search_for(String product) {
		
		System.out.println("step 1");
		homePage.searchForItem(product);
		Assert.assertTrue(searchPage.isSearch(), "This is not the search results page");
		
		System.out.println("		###SearchSteps::i_search_for(String product) -- PASSED!! ###");
	}
	 
	@And("I click on the {string} button, the product {string} should be added to my cart")
	public void i_click_on_the_button(String addToCart, String item) {
		
		ProductDetailsPage productItem = searchPage.selectItemWithDiscount(item, 0); // after this line we are in the productDetailsPage
		Assert.assertTrue(productItem.isProductPage(), "Not on product page");
		productItem.addToCart(0, productItem);
		
		product.setProduct(productItem.getProduct());
		Assert.assertTrue(productItem.isSliderDisplayed(), "The slider is not displayed");
		System.out.println("		###SearchSteps:: i_click_on_the_button(String addToCart) -- PASSED!! ###");	
	}

	@Then("the product should be added to my cart")
	public void the_product_should_be_added_to_my_cart() {
		
		product.goToCart();
		Assert.assertTrue(cartPage.isCartPage(), "This is not the cart page");
	}

	@Given("I have an item in my cart")
	public void i_have_an_item_in_my_cart() {
		
		Assert.assertTrue(cartPage.isCartPage(), "This is not the cart page");
	}

	@When("I navigate to the cart page")
	public void i_navigate_to_the_cart_page() {
		
		product.goToCart();
		Assert.assertTrue(cartPage.isCartPage(), "This is not the cart page");
	}

	@When("I click on the {string} button for the item {string}")
	public void i_click_on_the_button_for_the_item(String remove, String item) {
		
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
