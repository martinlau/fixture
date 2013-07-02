package io.fixture.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

[Controller]
[RequestMapping(array("/administration"))]
public class AdministrationController {

    [RequestMapping]
    fun index() = ".administration.index"

    [RequestMapping(array("/users"))]
    fun users() = ".administration.users"

}
