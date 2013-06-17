package io.fixture.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

[Controller]
public class AdministrationController {

    [RequestMapping(array("/administration"))]
    fun index() = ".administration.index"

}