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

Feature: User Registration

    Scenario: User Activation
        Given I open any page
        When I go to the page "/registration"
        And I fill in the form "registration" with:
            | field      | value             |
            | givenName  | given name        |
            | familyName | family name       |
            | email      | email@example.com |
            | username   | username          |
            | password   | password          |
            | confirm    | password          |
            | accept     | true              |
        And I click the link in the activation email
        # TODO this should read more like (following):
        # Then my account should be activated
        # And I should be able to log in
        And I go to the page "/secure"
        And I log in with the credentials "username" and "password"
        Then I should see the page "fixture.io - Secure"

