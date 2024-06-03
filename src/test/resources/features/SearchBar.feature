Feature: Search Functionality for amazon.in
In order to search products on the application

Background:
	Given User is on the home or any page with search bar
	
	Scenario: User is able to successfully search a product using the search bar
	When User enters ProductName "iPad" and clicked on Search Button or pressed enter
	Then User is landed to the searchResults page
	
	Scenario: User is is unable to successfully search an unrecognizable product
	When User enters unrecognizable text
	Then User enters ProductName "atchar" and clicked on Search Button or pressed enter
	Then User is landed to the searchResults page with no results outcome