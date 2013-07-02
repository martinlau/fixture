package io.fixture.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLogger;

public abstract aspect AbstractLoggingAspect {

    Logger logger = NOPLogger.NOP_LOGGER;

    after(): staticinitialization(*) {
        Class<?> withinType = thisJoinPoint.getStaticPart().getSourceLocation().getWithinType();

        logger = LoggerFactory.getLogger(withinType);
    }

    protected StringBuilder buildPrefix(String name, String[] parameterNames, Object[] parameterValues) {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append('(');

        for (int i = 0; i < parameterNames.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(parameterNames[i]).append('=').append(parameterValues[i]);
        }
        builder.append(')');

        return builder;
    }

}
