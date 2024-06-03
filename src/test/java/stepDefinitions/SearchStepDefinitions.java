package stepDefinitions;

import java.util.Properties;

import org.testng.Assert;

import factory.DriverFactory;
//import BaseClass.Base;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AmazonHomePage;
import pages.SearchResultsPage;

public class SearchStepDefinitions {

	AmazonHomePage homePage = new AmazonHomePage(DriverFactory.getDriver());
	SearchResultsPage searchPage =new SearchResultsPage(DriverFactory.getDriver());
	Properties props;
	
	@Given("User is on the home or any page with search bar")
	public void user_is_on_the_home_or_any_page_with_search_bar() {

		
		String url = "https://amazon.in";
		homePage.visitPage(url);
		Assert.assertTrue(homePage.verifyLogoIsDisplayed(), "Logo is not displayed, page still loading");
		Assert.assertTrue(homePage.verifySearchBoxIsDisplayed());
		System.out.println("		###SearchSteps::user_is_on_the_home_or_any_page_with_search_bar() -- PASSED!! ###");
	}

	
	@When("User enters ProductName {string} and clicked on Search Button or pressed enter")
	public void user_enters_product_name(String product) {

		
		homePage.searchForItem(product); //goes to searchPage after clicking
		int searchedProductsSize = searchPage.getSearchProducts().size();
		Assert.assertTrue(searchedProductsSize >0, "There are no results for this search item");
		Assert.assertTrue(searchPage.isSearch(), "This is not the search results page");
		System.out.println("		###SearchSteps::user_enters_product_name() -- PASSED!! ###");
	}

	
	@Then("User is landed to the searchResults page")
	public void user_is_landed_to_the_search_results_page() {
		
		Assert.assertTrue(searchPage.isSearch(), "This is not the search results page");
		System.out.println("		###SearchSteps::user_is_landed_to_the_search_results_page() -- PASSED!! ###");
	}

	@When("User enters unrecognizable ProductName {string} and clicked on Search Button or pressed enter")
	public void user_enters_unrecognizable_text(String item) {
		
		homePage.searchForItem(item);
		Assert.assertTrue(searchPage.isSearch(), "This is not the search results page");
		System.out.println("		###SearchSteps::user_enters_unrecognizable_text(String item) -- PASSED!! ###");
	}

	@Then("User is landed to the searchResults page with no results outcome")
	public void user_is_landed_to_the_search_results_page_with_no_results_outcome() {
		
		int searchedProductsSize = searchPage.getSearchProducts().size();
		Assert.assertTrue(searchedProductsSize ==0, "searched item exist in amazon database");
		System.out.println("		###SearchSteps::user_is_landed_to_the_search_results_page_with_no_results_outcome() -- PASSED!! ###");
	}
}
