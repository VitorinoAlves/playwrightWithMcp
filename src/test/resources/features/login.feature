@regression
Feature: Login and User Registration
  As a user of Automation Exercise (https://automationexercise.com/)
  I want to register, log in, and log out of my account
  So that I can securely manage my profile and orders

  Background:
    Given the user navigates to the Automation Exercise home page
    And the home page is displayed successfully
    When the user clicks on the "Signup / Login" button
    Then the "Login to your account" section is visible
    And the "New User Signup!" section is visible

  @register @smoke
  Scenario: Register User
    When the user enters a new signup name "Test User" and email "testuser_auto_123@example.com"
    And clicks the "Signup" button
    Then the "ENTER ACCOUNT INFORMATION" title is visible
    When the user fills the account information:
      | Title       | Mr.             |
      | Name        | Test User       |
      | Password    | StrongPass123!  |
      | Day         | 15              |
      | Month       | June            |
      | Year        | 1995            |
    And selects the checkbox "Sign up for our newsletter!"
    And selects the checkbox "Receive special offers from our partners!"
    And fills the address information:
      | First Name  | John            |
      | Last Name   | Doe             |
      | Company     | Quality Corp    |
      | Address     | 123 Test Avenue |
      | Address2    | Suite 100       |
      | Country     | United States   |
      | State       | Texas           |
      | City        | Austin          |
      | Zipcode     | 73301           |
      | Mobile      | 5125551234      |
    And clicks the "Create Account" button
    Then the "ACCOUNT CREATED!" message is visible
    When the user clicks the "Continue" button
    Then the user is logged in as "Test User"
    When the user clicks the "Delete Account" button
    Then the "ACCOUNT DELETED!" message is visible
    And the user clicks the "Continue" button

  @login @smoke
  Scenario: Login User with correct email and password
    Given the user with the email "valid_user@example.com" and password "ValidPassword123!" is created using the API
    When the user enters the login email "valid_user@example.com" and password "ValidPassword123!"
    And clicks the "Login" button
    Then the user is logged in as "Valid User"
    When the user clicks the "Delete Account" button
    Then the "ACCOUNT DELETED!" message is visible

  @login @negative
  Scenario: Login User with incorrect email and password
    When the user enters the login email "invalid_user@example.com" and password "WrongPassword123!"
    And clicks the "Login" button
    Then an error message "Your email or password is incorrect!" is visible

  @logout @smoke
  Scenario: Logout User
    Given the user with the email "valid_user@example.com" and password "ValidPassword123!" is created using the API
    When the user enters the login email "valid_user@example.com" and password "ValidPassword123!"
    And clicks the "Login" button
    Then the user is logged in as "Valid User"
    When the user clicks the "Logout" button
    Then the user is navigated to the login page

  @register @negative
  Scenario: Register User with existing email
    When the user enters a new signup name "Existing User" and email "existing_user@example.com"
    And clicks the "Signup" button
    Then an error message "Email Address already exist!" is visible

  @login @negative @data-driven
  Scenario Outline: Login with invalid credentials and empty fields
    When the user enters the login email "<email>" and password "<password>"
    And clicks the "Login" button
    Then an error message "<error_message>" is visible

    Examples:
      | email                     | password           | error_message                          |
      | non_existing@example.com  | WrongPassword123!  | Your email or password is incorrect!   |
