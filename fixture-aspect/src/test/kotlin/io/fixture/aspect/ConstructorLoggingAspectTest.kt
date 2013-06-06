package io.fixture.aspect

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import io.fixture.aspect.test.LoggedConstructorClass
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ConstructorLoggingAspectTest {

    val appender = ListAppender<ILoggingEvent>()

    val oldLevel = LoggedConstructorClass.logger.getLevel()

    [Before]
    fun setUp() {
        appender.setContext(LoggedConstructorClass.logger.getLoggerContext())
        appender.start()

        LoggedConstructorClass.logger.addAppender(appender)
        LoggedConstructorClass.logger.setLevel(Level.TRACE)
    }

    [After]
    fun tearDown() {
        appender.stop()
        appender.list.clear()

        LoggedConstructorClass.logger.setLevel(oldLevel)
        LoggedConstructorClass.logger.detachAppender(appender)
    }

    [Test]
    fun testAfterReturning() {
        LoggedConstructorClass("message", "value")

        assertTrue("a message should be logged") {
            appender.list.any {
                it.getMessage() == "LoggedConstructorClass(message=message, object=value) - end"
            }
        }
    }

    [Test]
    fun testAfterThrowing() {
        try {
            LoggedConstructorClass("message", RuntimeException("Test Exception"))
        }
        catch (e: RuntimeException) {
            // Need to catch it here, rather than in Test(expected=) so we can assert below
        }

        assertTrue("a message with an exception should be logged") {
            appender.list.any {
                it.getMessage() == "LoggedConstructorClass(message=message, object=java.lang.RuntimeException: Test Exception) - end - throwing: RuntimeException"
            }
        }
    }

    [Test]
    fun testAfterThrowingWithoutLogging() {
        LoggedConstructorClass.logger.setLevel(Level.OFF)
        try {
            LoggedConstructorClass("message", RuntimeException("Test Exception"))
        }
        catch (e: RuntimeException) {
            // Need to catch it here, rather than in Test(expected=) so we can assert below
        }

        assertTrue("a message without an exception should not be logged") {
            appender.list.all {
                it.getMessage() != "LoggedConstructorClass(message=message, object=java.lang.RuntimeException: Test Exception) - end - throwing: RuntimeException"
            }
        }
    }

    [Test]
    fun testBefore() {
        LoggedConstructorClass("message", "value")

        assertTrue("a message should be logged") {
            appender.list.any {
                it.getMessage() == "LoggedConstructorClass(message=message, object=value) - start"
            }
        }
    }

}
