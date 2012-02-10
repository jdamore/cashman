Story: Cash Machine

As a customer
I want to use the Cash Machine
In order to withdraw some money

Scenario: Initialisation
Given I go to the Cash Machine Home page
Then I should be on the Cash Machine Home page
And The quantity for 200 should be 5
And The quantity for 100 should be 5
And The quantity for 50 should be 5

Scenario: Successful withdrawal
Given I go to the Cash Machine Home page
When I ask for amount 500
And I click on Withdraw
Then The machine should give me 2 notes of 200
And The machine should give me 1 notes of 100
And The quantity for 200 should be 3
And The quantity for 100 should be 4