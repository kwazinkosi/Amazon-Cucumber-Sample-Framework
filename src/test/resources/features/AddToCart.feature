Feature: Add to Cart Feature in Amazon
  As a user
  I want to add products to my cart on Amazon
  So that I can purchase them later

Background:
  Given I am on the Amazon website

Scenario: Add a product to the cart
  When I search for "iPad" and select the first search result
  And I click on the "Add to Cart" button, the product "iPad" should be added to my cart
  Then the product should be added to my cart

Scenario: Remove a product from the cart
  Given I have an item in my cart
  When I navigate to the cart page
  And I click on the "Remove" button for the item "iPad"
  Then the item "iPad" should be removed from my cart
