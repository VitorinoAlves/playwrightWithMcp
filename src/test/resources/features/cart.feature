@regression @cart
Feature: Shopping Cart and Checkout
  As a customer of Automation Exercise (https://automationexercise.com/)
  I want to add products to my cart in various quantities and complete the checkout
  So that I can successfully purchase items online

  Background:
    Given the user navigates to the Automation Exercise home page
    And the home page is displayed successfully

  @smoke @cart-add-single
  Scenario: Add a single product to the cart
    When the user adds "Blue Top" to the cart
    And navigates to the shopping cart
    Then the cart displays "Blue Top" with a price of "Rs. 500" and quantity of 1

  @cart-add-multiple
  Scenario: Add multiple products to the cart
    When the user adds the following products to the cart:
      | product    | price   |
      | Blue Top   | Rs. 500 |
      | Men Tshirt | Rs. 400 |
    And navigates to the shopping cart
    Then the cart displays the following items:
      | product    | price   | quantity | total   |
      | Blue Top   | Rs. 500 | 1        | Rs. 500 |
      | Men Tshirt | Rs. 400 | 1        | Rs. 400 |

  @quantity @cart-quantity
  Scenario Outline: Add a product to the cart with varying quantities
    Given the user views the product details for "<product>"
    When the user sets the product quantity to <quantity>
    And adds the product to the cart
    And navigates to the shopping cart
    Then the cart displays "<product>" with quantity <quantity> and total price "<total_price>"

    Examples:
      | product          | quantity | total_price |
      | Blue Top         | 3        | Rs. 1500    |
      | Sleeveless Dress | 2        | Rs. 2000    |

  @cart-remove
  Scenario: Remove a product from the shopping cart
    Given the user has added "Blue Top" to the cart
    And navigates to the shopping cart
    When the user removes "Blue Top" from the cart
    Then "Blue Top" is no longer visible in the cart
    And the cart displays "Cart is empty!"

  @checkout @cart-guest-checkout
  Scenario: Require login or registration when a guest proceeds to checkout
    Given the user is an unauthenticated guest
    And the user has added "Blue Top" to the cart
    When the user navigates to the shopping cart
    And proceeds to checkout
    Then the user is prompted to register or login before checkout

  @checkout @smoke @cart-purchase
  Scenario: Complete purchase as a logged-in user
    Given the user is logged in with valid credentials
    And the user has added "Blue Top" to the cart
    When the user navigates to the shopping cart
    And proceeds to checkout
    And places the order with comment "Please leave the package at the front door"
    And submits the payment details:
      | name-on-card | card-number      | cvc | expiration-month | expiration-year |
      | John Doe     | 4111111111111111 | 123 | 12               | 2028            |
    Then the order confirmation message "Congratulations! Your order has been confirmed!" is displayed

  @checkout @cart-invoice
  Scenario: Complete purchase and download order invoice
    Given the user is logged in with valid credentials
    And the user has added "Men Tshirt" to the cart
    When the user navigates to the shopping cart
    And proceeds to checkout
    And places the order with comment "Order for office supplies"
    And submits the payment details:
      | name-on-card | card-number      | cvc | expiration-month | expiration-year |
      | Jane Doe     | 4111111111111111 | 456 | 08               | 2027            |
    And downloads the order invoice
    Then the invoice file is downloaded successfully
