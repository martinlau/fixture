package io.fixture.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

[Controller]
[RequestMapping(value = array("/administration"))]
public class AdministrationController {

    [RequestMapping]
    fun index() = ".administration.index"

    [RequestMapping(value = array("/users"))]
    fun users() = ".administration.users"

}
