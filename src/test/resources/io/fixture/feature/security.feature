Feature: Site Security

    Scenario: Anonymous Access to Secure Pages
        Given I open any page
        When I go to the page "/secure"
        Then I should see the page "fixture.io - Login"

    Scenario: Anonymous Access to Administration Pages
        Given I open any page
        When I go to the page "/administration"
        Then I should see the page "fixture.io - Login"

    Scenario: Anonymous Access to Administration Pages
        Given I open any page
        When I go to the page "/administration"
        Then I should see the page "fixture.io - Login"

    Scenario: Authenticated Access to Secure Pages
        Given I open any page
        When I go to the page "/secure"
        And I log in with the credentials "user" and "password"
        Then I should see the page "fixture.io - Secure"

    Scenario: Authenticated Access to Administration Pages
        Given I open any page
        When I go to the page "/administration"
        And I log in with the credentials "user" and "password"
        Then I should see the page "fixture.io - Access denied"

    Scenario: Administrative Access to Secure Pages
        Given I open any page
        When I go to the page "/secure"
        And I log in with the credentials "administrator" and "password"
        Then I should see the page "fixture.io - Secure"

    Scenario: Administrative Access to Administration Pages
        Given I open any page
        When I go to the page "/administration"
        And I log in with the credentials "administrator" and "password"
        Then I should see the page "fixture.io - Administration"

    Scenario: Invalid Authentication
        Given I open any page
        When I go to the page "/login"
        And I log in with the credentials "invalid" and "invalid"
        Then I should see the page "fixture.io - Login"
        And I should see the alert "Log in failed"
