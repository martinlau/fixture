package io.fixture.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

[Controller]
public class SecureController {

    [RequestMapping(array("/secure"))]
    fun index() = ".secure.index"

}
