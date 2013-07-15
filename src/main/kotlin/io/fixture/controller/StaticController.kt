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
