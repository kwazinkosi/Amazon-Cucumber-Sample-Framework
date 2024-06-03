package testRunners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/SearchBar.feature", glue = {"stepDefinitions", "hooks"}, plugin = { "pretty",
		"html:target/results.html" }, dryRun = false)
public class AmazonSearch {
}
