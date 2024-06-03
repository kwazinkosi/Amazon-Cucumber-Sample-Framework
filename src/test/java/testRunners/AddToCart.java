package testRunners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/AddToCart.feature", glue = {"stepDefinitions"}, plugin = { "pretty",
		"html:target/results.html" }, dryRun = false)
public class AddToCart {

}
