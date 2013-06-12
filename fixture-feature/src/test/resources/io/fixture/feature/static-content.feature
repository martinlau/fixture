Feature: Static Content

    Scenario Outline: Site information
        Given I open any page
        When I click on the "<link>" link
        Then I should see the "<title>" page

    Examples:
        | link         | title                     |
        | fixture.io   | fixture.io                |
        | About        | fixture.io - About        |
        | Contact      | fixture.io - Contact      |
        | Join         | fixture.io - Join         |
        | Log in       | fixture.io - Login        |
        | Privacy      | fixture.io - Privacy      |
        | Terms of use | fixture.io - Terms of use |

