package io.fixture.feature.step

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.springframework.beans.factory.annotation.Autowired

class StepDefs [Autowired] (
        val driver: WebDriver
) {

    [Given("I open any page")]
    fun I_open_any_page() {
        driver.get("http://localhost:8080/fixture-web/home")
    }

    [Given("I select the \"([^\"]*)\" theme")]
    fun I_select_the_theme(theme: String) {
        driver.get(driver.getCurrentUrl() + "?theme=${theme}")
    }

    [When("I click on the \"([^\"]*)\" link")]
    fun I_click_on_the_link(link: String) {
        driver.findElement(By.linkText(link))!!.click()
    }

    [Then("the theme should change to \"([^\"]*)\"")]
    fun the_theme_should_change_to(theme: String) {
        assertTrue(driver.getPageSource().contains("href=\"/fixture-web/static/bootswatch/2.3.1/${theme}/bootstrap.min.css\""))
    }

    [Then("I should see the \"([^\"]*)\" page")]
    fun I_should_see_the_page(title: String) {
        assertEquals(title, driver.getTitle())
    }

}



