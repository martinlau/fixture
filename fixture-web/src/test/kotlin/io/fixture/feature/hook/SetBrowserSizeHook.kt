package io.fixture.feature.hook

import org.openqa.selenium.WebDriver
import cucumber.api.Scenario
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.OutputType
import org.springframework.beans.factory.annotation.Autowired
import cucumber.api.java.Before
import org.openqa.selenium.Dimension

public class SetBrowserSizeHook [Autowired] (
        val driver: WebDriver
) {

    [Before]
    fun setBrowserSize() {
        driver.manage().window().setSize(Dimension(960, 720))
    }

}
