/*
 * Copyright 2013 Martin Lau
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.fixture.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

[Controller]
public class StaticController {

    [RequestMapping(value = array("/"))]
    fun index() = ".static.index"

    [RequestMapping(value = array("/about"))]
    fun about() = ".static.about"

    [RequestMapping(value = array("/access-denied"))]
    fun accessDenied() = ".static.access-denied"

    [RequestMapping(value = array("/contact"))]
    fun contact() = ".static.contact"

    [RequestMapping(value = array("/join"))]
    fun join() = ".static.join"

    [RequestMapping(value = array("/login"))]
    fun login() = ".static.login"

    [RequestMapping(value = array("/terms"))]
    fun terms() = ".static.terms"

    [RequestMapping(value = array("/privacy"))]
    fun privacy() = ".static.privacy"

}
