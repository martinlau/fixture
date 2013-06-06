package io.fixture.aspect.test

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory

class LoggedMethodClass {

    class object {
        val logger: Logger = LoggerFactory.getLogger(javaClass<LoggedMethodClass>()) as Logger
    }

    fun doSomething(message: String, `object`: Any): Any {
        logger.info(message)
        if (`object` is Exception) {
            throw `object` as Exception
        }
        return `object`
    }

    fun doNothing(message: String, `object`: Any) {
        // Does nothing
    }

}
