/*
 * #%L
 * fixture
 * %%
 * Copyright (C) 2013 Martin Lau
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
import kotlin.test.assertNotNull

class StepDefs [Autowired] (
        val driver: WebDriver
) {

    [Value(value = "#{systemProperties['fixture.base.url']}")]
    var baseUri: String? = null

    [Given(value = """^I open any page""")]
    fun I_open_any_page() {
        driver.get("${baseUri}")
    }

    [Given(value = """^I select the theme "([^"]*)"$""")]
    fun I_select_the_theme(theme: String) {
        driver.get(driver.getCurrentUrl() + "?theme=${theme}")
    }

    [When(value = """^I go to the page "([^"]*)"$""")]
    fun I_go_to_the_page(page: String) {
        driver.get("${baseUri}${page}")
    }

    [When(value = """^I click on the link "([^"]*)"$""")]
    fun I_click_on_the_link(link: String) {
        driver.findElement(By.linkText(link))!!.click()
    }

    [When(value = """^I log in with the credentials "([^"]*)" and "([^"]*)"$""")]
    fun I_log_in_with_the_credentials(username: String, password: String) {
        // HACK: drone.io seems to need some time to load the page - this allows that
        driver.getCurrentUrl()

        driver.findElement(By.id("username"))!!.sendKeys(username)
        driver.findElement(By.id("password"))!!.sendKeys(password)
        driver.findElement(By.id("submit"))!!.click()
    }

    [Then(value = """^the theme should change to "([^"]*)"$""")]
    fun the_theme_should_change_to(theme: String) {
        // HACK: drone.io seems to need some time to load the page - this allows that
        driver.getCurrentUrl()

        assertTrue(driver.getPageSource().contains("href=\"/fixture/static/bootswatch/2.3.1/${theme}/bootstrap.min.css\""))
    }

    [Then("""^I should see the "([^"]*)" link$""")]
    fun I_should_see_the_link(link: String) {
        // HACK: drone.io seems to need some time to load the page - this allows that
        driver.getCurrentUrl()

        assertNotNull(driver.findElement(By.linkText(link)))
    }

    [Then(value = """^I should see the page "([^"]*)"$""")]
    fun I_should_see_the_page(title: String) {
        // HACK: drone.io seems to need some time to load the page - this allows that
        driver.getCurrentUrl()

        assertEquals(title, driver.getTitle())
    }

    [Then(value = """^I should see the alert "([^"]*)"$""")]
    fun I_should_see_the_alert(message: String) {
        // HACK: drone.io seems to need some time to load the page - this allows that
        driver.getCurrentUrl()

        assertTrue(driver.findElement(By.className("alert"))!!.getText()!!.contains(message))
    }

}



