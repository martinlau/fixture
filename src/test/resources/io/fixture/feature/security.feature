###
# #%L
# fixture
# %%
# Copyright (C) 2013 Martin Lau
# %%
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#      http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# #L%
###

Feature: Site Security

    Scenario: Anonymous Access to Secure Pages
        Given I open any page
        When I go to the page "/secure"
        Then I should see the page "fixture.io - Log in"

    Scenario: Anonymous Access to Administration Pages
        Given I open any page
        When I go to the page "/administration"
        Then I should see the page "fixture.io - Log in"

    Scenario: Anonymous Access to Administration Pages
        Given I open any page
        When I go to the page "/administration"
        Then I should see the page "fixture.io - Log in"

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
        Then I should see the page "fixture.io - Log in"
        And I should see the alert "Log in failed"

    Scenario: Logged in navigation
        Given I open any page
        When I click on the link "Log in"
        And I log in with the credentials "administrator" and "password"
        Then I should see the "Log out" link

    Scenario: Logged out navigation
        Given I open any page
        And I click on the link "Log in"
        And I log in with the credentials "administrator" and "password"
        When I click on the link "Log out"
        Then I should see the "Log in" link
