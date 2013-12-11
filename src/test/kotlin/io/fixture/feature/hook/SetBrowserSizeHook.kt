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

package io.fixture.feature.hook

import org.openqa.selenium.WebDriver
import cucumber.api.Scenario
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.OutputType
import org.springframework.beans.factory.annotation.Autowired
import cucumber.api.java.Before
import org.openqa.selenium.Dimension

public class SetBrowserSizeHook [Autowired] (
        val driver: WebDriver? = null
) {

    [Before]
    fun setBrowserSize() {
        driver?.manage()?.window()?.setSize(Dimension(960, 720))
    }

}
