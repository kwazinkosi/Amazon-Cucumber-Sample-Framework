package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import BaseClass.Base;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AmazonHomePage;
import pages.SearchResultsPage;

public class SearchStepDefinitions {

	AmazonHomePage homePage;
	SearchResultsPage searchPage;
	private WebDriver driver;

	@Given("User is on the home or any page with search bar")
	public void user_is_on_the_home_or_any_page_with_search_bar() {

		/*homePage = new AmazonHomePage(driver);
		String url = homePage.getUrl();
		homePage.visitPage(url);

		Assert.assertTrue(homePage.verifyLogoIsDisplayed(), "Logo is not displayed, page still loading");
		Assert.assertTrue(homePage.verifySearchBoxIsDisplayed());*/
	}

	@When("User enters ProductName {string} and clicked on Search Button or pressed enter")
	public void user_enters_product_name(String product) {

		/*homePage.searchForItem(product);
		Assert.assertTrue(searchPage.isSearch(), "This is not the search results page");*/
	}

	@Then("User is landed to the searchResults page")
	public void user_is_landed_to_the_search_results_page() {
		/*Assert.assertTrue(searchPage.isSearch(), "This is not the search results page");*/
	}

	@When("User enters unrecognizable text")
	public void user_enters_unrecognizable_text() {
		
	}

	@Then("User is landed to the searchResults page with no results outcome")
	public void user_is_landed_to_the_search_results_page_with_no_results_outcome() {
	
	}

}
