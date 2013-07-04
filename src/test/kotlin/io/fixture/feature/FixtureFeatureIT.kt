package io.fixture.feature

import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith
import org.junit.Ignore

[RunWith(javaClass<Cucumber>())]
[Cucumber.Options(
        format = array(
                "junit:target/failsafe-reports/TEST-io.fixture.feature.FixtureFeatureIT-Cucumber.xml",
                "html:target/cucumber"
                // TODO Reenable when cucumber > 1.1.3
                // "pretty"
        ),
        glue = array(
                "io.fixture.feature.hook",
                "io.fixture.feature.step"
        )
)]
[Ignore("ML 20130704 - Ignoring while I figure out what drone.io is doing")]
class FixtureFeatureIT
