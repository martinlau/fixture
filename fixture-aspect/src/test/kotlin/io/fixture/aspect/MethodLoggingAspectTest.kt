package io.fixture.aspect

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import io.fixture.aspect.test.LoggedMethodClass
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class MethodLoggingAspectTest {

    val appender = ListAppender<ILoggingEvent>()

    val oldLevel: Level? = LoggedMethodClass.logger.getLevel()

    [Before]
    fun setUp() {
        appender.setContext(LoggedMethodClass.logger.getLoggerContext())
        appender.start()

        LoggedMethodClass.logger.addAppender(appender)
        LoggedMethodClass.logger.setLevel(Level.TRACE)
    }

    [After]
    fun tearDown() {
        appender.stop()
        appender.list.clear()

        LoggedMethodClass.logger.setLevel(oldLevel)
        LoggedMethodClass.logger.detachAppender(appender)
    }

    [Test]
    fun testAfterReturning() {
        LoggedMethodClass().doSomething("message", "value")

        assertTrue("a message with a return value should be logged") {
            appender.list.any {
                it.getMessage() == "doSomething(message=message, object=value) - end - returning: value"
            }
        }
    }

    [Test]
    fun testAfterReturningWithoutReturnType() {
        LoggedMethodClass().doNothing("message", "value")

        assertTrue("a message without a return value should be logged") {
            appender.list.any {
                it.getMessage() == "doNothing(message=message, object=value) - end"
            }
        }
    }

    [Test]
    fun testAfterThrowing() {
        try {
            LoggedMethodClass().doSomething("message", RuntimeException("Test Exception"))
        }
        catch (e: RuntimeException) {
            // Need to catch it here, rather than in Test(expected=) so we can assert below
        }

        assertTrue("a message with an exception should be logged") {
            appender.list.any {
                it.getMessage() == "doSomething(message=message, object=java.lang.RuntimeException: Test Exception) - end - throwing: RuntimeException"
            }
        }
    }

    [Test]
    fun testAfterThrowingWithoutLogging() {
        LoggedMethodClass.logger.setLevel(Level.OFF)
        try {
            LoggedMethodClass().doSomething("message", RuntimeException("Test Exception"))
        }
        catch (e: RuntimeException) {
            // Need to catch it here, rather than in Test(expected=) so we can assert below
        }

        assertTrue("a message with an exception should not be logged") {
            appender.list.all {
                it.getMessage() != "doSomething(message=message, object=java.lang.RuntimeException: Test Exception) - end - throwing: RuntimeException"
            }
        }
    }

    [Test]
    fun testBefore() {
        LoggedMethodClass().doSomething("message", "value")

        assertTrue("a message should be logged") {
            appender.list.any {
                it.getMessage() == "doSomething(message=message, object=value) - start"
            }
        }
    }

}
