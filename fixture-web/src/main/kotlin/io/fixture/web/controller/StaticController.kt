package io.fixture.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

[Controller]
public class StaticController {

    [RequestMapping(array("/"))]
    fun index() = ".static.index"

    [RequestMapping(array("/about"))]
    fun about() = ".static.about"

    [RequestMapping(array("/contact"))]
    fun contact() = ".static.contact"

    [RequestMapping(array("/join"))]
    fun join() = ".static.join"

    [RequestMapping(array("/login"))]
    fun login() = ".static.login"

    [RequestMapping(array("/terms"))]
    fun terms() = ".static.terms"

    [RequestMapping(array("/privacy"))]
    fun privacy() = ".static.privacy"

}
