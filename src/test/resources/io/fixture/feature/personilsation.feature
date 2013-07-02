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
