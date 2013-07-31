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

Feature: No Broken References

    Scenario: Broken images
        Given I open any page
        Then all "img" tags should have valid "src" attributes

    Scenario: Broken links
        Given I open any page
        Then all "link" tags should have valid "href" attributes

    Scenario: Broken scripts
        Given I open any page
        Then all "script" tags should have valid "src" attributes
