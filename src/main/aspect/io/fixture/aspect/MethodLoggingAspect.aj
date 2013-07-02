package io.fixture.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public aspect MethodLoggingAspect extends AbstractLoggingAspect pertypewithin(io.fixture..* && !io.fixture.aspect.*LoggingAspect) {

    before(): execution(* *..*(..)) {
        if (logger.isTraceEnabled()) {
            MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();

            StringBuilder builder = buildPrefix(signature, thisJoinPoint);
            builder.append(" - start");

            logger.trace(builder.toString());
        }
    }

    after() returning(Object result): execution(* *..*(..)) {
        if (logger.isTraceEnabled()) {
            MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();

            StringBuilder builder = buildPrefix(signature, thisJoinPoint);
            builder.append(" - end");

            if (signature.getReturnType() != Void.TYPE) {
                builder.append(" - returning: ").append(result);
            }

            logger.trace(builder.toString());
        }
    }

    after() throwing(Throwable error): execution(* *..*(..)) {

        if (logger.isErrorEnabled()) {
            MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();

            StringBuilder builder = buildPrefix(signature, thisJoinPoint);
            builder.append(" - end - throwing: ").append(error.getClass().getSimpleName());

            logger.error(builder.toString(), error);
        }
    }

    protected StringBuilder buildPrefix(MethodSignature signature, JoinPoint joinPoint) {
        String name = signature.getMethod().getName();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        return buildPrefix(name, parameterNames, args);
    }

}
