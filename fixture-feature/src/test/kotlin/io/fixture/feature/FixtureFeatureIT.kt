package io.fixture.feature

import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

[RunWith(javaClass<Cucumber>())]
[Cucumber.Options(
        format = array(
                // TODO Reenable when cukes > 1.1.3
                // "pretty",
                "junit:target/failsafe-reports/cucumber-junit.xml",
                "html:target/cucumber"
        ),
        glue = array(
                "io.fixture.feature.hook",
                "io.fixture.feature.step"
        )
)]
class FixtureFeatureIT
