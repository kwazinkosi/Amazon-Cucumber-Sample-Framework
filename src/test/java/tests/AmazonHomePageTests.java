package tests;

import pages.ProductDetailsPage;
import pages.SearchResultsPage;
import utils.FileManager;
//import pages.TodayDealsPage;
import utils.NavigateToSite;
//import utils.TestListener;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import BaseClass.Base;
import factory.DriverFactory;
import pages.AmazonHomePage;
import pages.CartPage;

public class AmazonHomePageTests {

	private AmazonHomePage amazonHomePage;
	private SearchResultsPage searchResultsPage;
//    private ProductDetailsPage productDetailsPage;
	private CartPage cartPage;
	private WebDriver driver;
	private DriverFactory driverFactory;
//	private FileManager fileManager;
//	private ScreenCapturer screenCapturer;
	Properties props;
	
	@DataProvider(name = "searchItems")
	public Object[][] testData() {
		return new Object[][] { { "headphones" },
//                {"ipad"},
				// to add more search terms later
		};
	}

	@BeforeClass
	public void setup() {

		Base.setupLogging();
//		this.driver = Base.setupDriver();
		try {
			props = FileManager.loadProperties("props.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String browserName = props.getProperty("browser");
		driverFactory = new DriverFactory();
		driver =driverFactory.initDriver(browserName);

		amazonHomePage = new AmazonHomePage(DriverFactory.getDriver());
		searchResultsPage = new SearchResultsPage(DriverFactory.getDriver());
//        productDetailsPage = new ProductDetailsPage(null);
		cartPage = new CartPage(driver);
		Base.log.info("\n//============== AmazonHomePage Tests =============\\");
		System.out.println(
				"\n\n### ============================== AmazonHomePage Tests ============================== ###");
	}

	@Test(priority = 1)
	public void testVerifyAmazonLogoIsDisplayed() throws IOException {

//    	String url = amazonHomePage.getUrl();
//	    NavigateToSite.navigateToAmazon(url, driver);
		try {
			System.out
					.println("		### AmazonHomePageTests::testVerifyAmazonLogoIsDisplayed() -- testing logo ###");

			String url = props.getProperty("appUrl");

			NavigateToSite.navigateToAmazon(url, DriverFactory.getDriver());
			boolean isLogoDisplayed = amazonHomePage.verifyLogoIsDisplayed();
			Assert.assertTrue(isLogoDisplayed, "Amazon logo is not displayed on homepage");
			Base.log.info("		### AmazonHomePageTests::testVerifyAmazonLogoIsDisplayed() -- logo displayed ###");
			System.out.println("		### AmazonHomePageTests::testVerifyAmazonLogoIsDisplayed() -- logo displayed ###");         
		
		} catch (Exception e) {
			System.out.println("		### AmazonHomePage::testVerifyAmazonLogoIsDisplayed() -- logo display failed ###");
			amazonHomePage.takeScreenshotOnFailure("LOGO-testFailure_"); // capture screen
			Base.log.error("		### AmazonHomePageTests::testVerifyAmazonLogoIsDisplayed() -- logo display failed ###");
			throw e;
		}
	}

	@Test(priority = 2)
	public void testIsAmazonHomePage() throws IOException {

		try {
			boolean isHome = amazonHomePage.verifyHomePage();
			Assert.assertEquals(isHome, true);
			Base.log.info("		### AmazonHomePageTests::testIsAmazonHomePage() -- Amazon home page verified ###");
			System.out
					.println("		### AmazonHomePageTests::testIsAmazonHomePage() -- Amazon home page verified ###");

		} catch (Exception e) {
			System.out
					.println("		### AmazonHomePage::testIsAmazonHomePage() -- Amazon home page not verified ###");
			amazonHomePage.takeScreenshotOnFailure("AmazonHomePageVerification-testFailure_"); // capture screen
			Base.log.error(
					"		### AmazonHomePageTests::testIsAmazonHomePage() -- Amazon home page not verified ###");
//			System.out.println("-------Tryinng again/Reloading------");
//			String url = amazonHomePage.getUrl();
//			NavigateToSite.navigateToAmazon(url, driver);
		}
	}

	@Test(dataProvider = "searchItems", priority = 3)
	public void testSearchForProductAndVerifyResult(String searchTerm) {
//    	AmazonHomePage amazonHome =new AmazonHomePage();
		// Search for the specified item
		String url =  props.getProperty("appUrl");
		NavigateToSite.navigateToAmazon(url, DriverFactory.getDriver());
		amazonHomePage.searchForItem(searchTerm);

		// Verify that the search box is displayed
		boolean isSearchBoxDisplayed = amazonHomePage.verifySearchBoxIsDisplayed();
		Assert.assertTrue(isSearchBoxDisplayed,
				"Search results header is not displayed for search term: " + searchTerm);

		// Verify search results title contains the search term
		String actualTitle = driver.findElement(By.cssSelector("h2.s-line-clamp-2")).getText();
		Assert.assertTrue(actualTitle.toLowerCase().contains(searchTerm),
				"Search results title does not contain search term: " + searchTerm);
	}

	@Test(priority = 4)
	public void verifySearchResultIsDisplayed() {

		System.out.println(
				"		### AmazonHomePageTests::verifySearchResultIsDisplayed() -- verifying if search result is displayed()");
		Base.log.info(
				"		### AmazonHomePage::verifySearchResultIsDisplayed() -- verifying if search result is displayed()");

		if (!amazonHomePage.verifySearchBoxIsDisplayed()) {
			String url = props.getProperty("appUrl");
			NavigateToSite.navigateToAmazon(url, DriverFactory.getDriver());
		}

		amazonHomePage.searchForItem("ipad");
		boolean isSearchResultsDisplayed = amazonHomePage.verifySearchResultIsDisplayed();
		Assert.assertTrue(isSearchResultsDisplayed, "### Search results header is not displayed ###");
	}

	@Test(priority = 5)
	public void verifySearchProductAndBuy() {

//    	AmazonHomePage amazonHome = new AmazonHomePage();
//    	SearchResultsPage searchResults = new SearchResultsPage();
//    	CartPage cart =new CartPage();
		String item = "ipad";
		// Find the product in the searchResults
		System.out
				.println("		### AmazonHomePageTests::verifySearchProductAndBuy() -- searching for item: " + item);
		ProductDetailsPage product = searchResultsPage.findProduct(item);
		Assert.assertEquals(product.getProductTitle().toLowerCase().contains(item), true);
		// Add product to cartproductPage.getDiscountValue(discountElement)
		product.addToCart(0.0, product);
		Assert.assertTrue(product.isSliderDisplayed(), "The slider is not displayed");
		if (!cartPage.isCartEmpty()) {
			// proceed to checkout
			cartPage.proceedToCheckout();
			By authPage = By.id("authportal-main-section");
			boolean isAuthPage = cartPage.getWait()
					.until(ExpectedConditions.presenceOfElementLocated(authPage)) != null;
			Assert.assertTrue(isAuthPage == true, "This is not the auth page");
		}
	}
}
