package io.fixture.feature

import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith
import org.junit.Ignore

[RunWith(javaClass<Cucumber>())]
[Cucumber.Options(
        format = array(
                // "junit:target/failsafe-reports/TEST-io.fixture.feature.FixtureFeatureIT-Cucumber.xml",
                "json:target/cucumber.json"
                // TODO Reenable when cucumber > 1.1.3
                // "pretty"
        ),
        glue = array(
                "cucumber.api.spring",
                "io.fixture.feature.hook",
                "io.fixture.feature.step"
        )
)]
class FixtureFeatureIT
