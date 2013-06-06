package io.fixture.aspect.test

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory

class LoggedConstructorClass(
        val message: String,
        val `object`: Any
) {

    class object {
        val logger = LoggerFactory.getLogger(javaClass<LoggedConstructorClass>()) as Logger
    }

    {
        logger.info(message)
        if (`object` is Exception) {
            throw `object`
        }
    }

}
