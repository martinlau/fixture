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

Feature: Static Content

    Scenario Outline: Site information
        Given I open any page
        When I click on the link "<link>"
        Then I should see the page "<title>"

    Examples:
        | link         | title                     |
        | fixture.io   | fixture.io                |
        | About        | fixture.io - About        |
        | Contact      | fixture.io - Contact      |
        | Register     | fixture.io - Registration |
        | Log in       | fixture.io - Login        |
        | Privacy      | fixture.io - Privacy      |
        | Terms of use | fixture.io - Terms of use |

