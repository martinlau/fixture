package io.fixture.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

[Controller]
public class SecureController {

    [RequestMapping(value = array("/secure"))]
    fun index() = ".secure.index"

}
