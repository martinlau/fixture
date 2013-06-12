package io.fixture.feature.hook

import org.openqa.selenium.WebDriver
import cucumber.api.java.After
import cucumber.api.Scenario
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.OutputType
import org.springframework.beans.factory.annotation.Autowired

public class EmbedScreenshotHook [Autowired(required = false)] (
        val takesScreenshot: TakesScreenshot?
) {

    [After]
    fun embedScreenshot(scenario: Scenario) {
        if (takesScreenshot != null) {
            val screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES)

            scenario.write("<p>")
            scenario.embed(screenshot, "image/png")
            scenario.write("</p>")
        }
    }

}
