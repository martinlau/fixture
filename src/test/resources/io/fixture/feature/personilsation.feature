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

Feature: Personalisation

    Scenario Outline: Theme Selection
        Given I open any page
        And I select the theme "<theme>"
        When I click on the link "Change theme"
        Then the theme should change to "<next>"

    Examples: Themes
        | theme     | next      |
        | amelia    | cerulean  |
        | cerulean  | cosmo     |
        | cosmo     | cyborg    |
        | cyborg    | default   |
        | default   | journal   |
        | journal   | readable  |
        | readable  | shamrock  |
        | shamrock  | simplex   |
        | simplex   | slate     |
        | slate     | spacelab  |
        | spacelab  | spruce    |
        | spruce    | superhero |
        | superhero | united    |
        | united    | amelia    |
