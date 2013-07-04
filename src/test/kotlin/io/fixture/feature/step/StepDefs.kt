package io.fixture.feature.step

import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import java.net.URI
import java.util.concurrent.TimeUnit

class StepDefs [Autowired] (
        val driver: WebDriver
) {

    [Value("#{systemProperties['fixture.base.url']}")]
    var baseUri: String? = null

    [Given("""^I open any page""")]
    fun I_open_any_page() {
        driver.get("${baseUri}")
    }

    [Given("""^I select the theme "([^"]*)"$""")]
    fun I_select_the_theme(theme: String) {
        driver.get(driver.getCurrentUrl() + "?theme=${theme}")
    }

    [When("""^I go to the page "([^"]*)"$""")]
    fun I_go_to_the_page(page: String) {
        driver.get("${baseUri}${page}")
    }

    [When("""^I click on the link "([^"]*)"$""")]
    fun I_click_on_the_link(link: String) {
        driver.findElement(By.linkText(link))!!.click()
    }

    [When("""^I log in with the credentials "([^"]*)" and "([^"]*)"$""")]
    fun I_log_in_with_the_credentials(username: String, password: String) {
        driver.findElement(By.id("username"))!!.sendKeys(username)
        driver.findElement(By.id("password"))!!.sendKeys(password)
        driver.findElement(By.id("submit"))!!.click()
    }

    [Then("""^the theme should change to "([^"]*)"$""")]
    fun the_theme_should_change_to(theme: String) {
        // HACK: drone.io seems to need some time to load the page - this allows that
        driver.getCurrentUrl()

        assertTrue(driver.getPageSource().contains("href=\"/fixture/static/bootswatch/2.3.1/${theme}/bootstrap.min.css\""))
    }

    [Then("""^I should see the page "([^"]*)"$""")]
    fun I_should_see_the_page(title: String) {
        // HACK: drone.io seems to need some time to load the page - this allows that
        driver.getCurrentUrl()

        assertEquals(title, driver.getTitle())
    }

    [Then("""^I should see the alert "([^"]*)"$""")]
    fun I_should_see_the_alert(message: String) {
        assertTrue(driver.findElement(By.className("alert"))!!.getText()!!.contains(message))
    }

}



