package io.fixture.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.ConstructorSignature;

public aspect ConstructorLoggingAspect extends AbstractLoggingAspect pertypewithin(io.fixture..* && !io.fixture.aspect.*LoggingAspect) {

    before(): execution(*.new(..)) {
        if (logger.isTraceEnabled()) {
            ConstructorSignature signature = (ConstructorSignature) thisJoinPoint.getSignature();

            StringBuilder builder = buildPrefix(signature, thisJoinPoint);
            builder.append(" - start");

            logger.trace(builder.toString());
        }
    }

    after() returning(): execution(*.new(..)) {
        if (logger.isTraceEnabled()) {
            ConstructorSignature signature = (ConstructorSignature) thisJoinPoint.getSignature();

            StringBuilder builder = buildPrefix(signature, thisJoinPoint);
            builder.append(" - end");

            logger.trace(builder.toString());
        }
    }

    after() throwing(Throwable error): execution(*.new(..)) {
        if (logger.isErrorEnabled()) {
            ConstructorSignature signature = (ConstructorSignature) thisJoinPoint.getSignature();

            StringBuilder builder = buildPrefix(signature, thisJoinPoint);
            builder.append(" - end - throwing: ").append(error.getClass().getSimpleName());

            logger.error(builder.toString(), error);
        }
    }

    protected StringBuilder buildPrefix(ConstructorSignature signature, JoinPoint joinPoint) {
        String simpleName = signature.getConstructor().getDeclaringClass().getSimpleName();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        return buildPrefix(simpleName, parameterNames, args);
    }

}
