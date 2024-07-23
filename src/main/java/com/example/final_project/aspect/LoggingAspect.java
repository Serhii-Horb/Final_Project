package com.example.final_project.aspect;

import org.antlr.v4.runtime.misc.NotNull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Around advice to log controller method calls.
     * This method logs information before and after the execution of a controller method,
     * and handles any exceptions thrown during execution.
     *
     * @param joinPoint the join point representing the method execution
     * @return the result of the method execution
     * @throws Throwable if the method execution fails
     */
    @Around("execution(* com.example.final_project.controller..*(..)))")
    public Object mdcServiceController(@NotNull final ProceedingJoinPoint joinPoint) throws Throwable {
        String queryMethod = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logBeforeControllerQuery(queryMethod, args);
        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            logAfterControllerQuery(queryMethod, args, result, startTime);
            return result;
        } catch (Exception ex) {
            logAndGetErrorMessage(queryMethod, args, ex, startTime);
            throw ex;
        }
    }

    /**
     * Logs information before the execution of a controller method.
     * Sets MDC fields for tracing and logs the method arguments.
     *
     * @param queryMethod the name of the method being called
     * @param args        the arguments passed to the method
     */
    private void logBeforeControllerQuery(final String queryMethod, final Object[] args) {
        // Set MDC fields to track the start of the controller method execution
        MDCFields.CONTROLLER_STEP.putMdcField("CONTROLLER_IN");
        MDCFields.CONTROLLER_METHOD.putMdcFieldWithFieldName(queryMethod);

        // Convert arguments to a string and log them
        String argsAsString = Arrays.toString(args);
        log.info("args={};", argsAsString);

        // Clean up MDC fields after logging
        MDCFields.CONTROLLER_METHOD.removeMdcField();
        MDCFields.CONTROLLER_STEP.removeMdcField();
    }

    /**
     * Logs information after the execution of a controller method.
     * Includes logging the method execution time and result.
     *
     * @param queryMethod the name of the method being called
     * @param args        the arguments passed to the method
     * @param result      the result of the method execution
     * @param startTime   the start time of the method execution
     */
    private void logAfterControllerQuery(final String queryMethod, final Object[] args, final Object result, final long startTime) {
        long callTime = System.currentTimeMillis() - startTime;
        String resultInfo = LogUtils.getDaoResultLogInfo(log, result);

        // Set MDC fields to track the end of the controller method execution
        MDCFields.CONTROLLER_STEP.putMdcField("CONTROLLER_OUT");
        MDCFields.CONTROLLER_METHOD.putMdcFieldWithFieldName(queryMethod);
        MDCFields.CONTROLLER_TIME.putMdcFieldWithFieldName(callTime);

        // Convert arguments to a string and log the result
        String argsAsString = Arrays.toString(args);
        log.info(
                "args={}; RESULT: [{}]",
                argsAsString,
                resultInfo
        );

        // Clean up MDC fields after logging
        MDCFields.CONTROLLER_TIME.removeMdcField();
        MDCFields.CONTROLLER_METHOD.removeMdcField();
        MDCFields.CONTROLLER_STEP.removeMdcField();
    }

    /**
     * Logs information when an exception occurs during the execution of a controller method.
     * Includes logging the exception message and method execution time.
     *
     * @param queryMethod the name of the method being called
     * @param args        the arguments passed to the method
     * @param ex          the exception thrown during the method execution
     * @param startTime   the start time of the method execution
     */
    private void logAndGetErrorMessage(final String queryMethod, final Object[] args, final Exception ex, final long startTime) {
        long callTime = System.currentTimeMillis() - startTime;
        String errorMsg = String.format(
                "args=%s;",
                Arrays.toString(args)
        );

        // Set MDC fields to track the exception during the controller method execution
        MDCFields.CONTROLLER_STEP.putMdcField("CONTROLLER_ERROR");
        MDCFields.CONTROLLER_METHOD.putMdcFieldWithFieldName(queryMethod);
        MDCFields.CONTROLLER_TIME.putMdcFieldWithFieldName(callTime);

        // Log the error message and exception
        log.error(errorMsg, ex);

        // Clean up MDC fields after logging
        MDCFields.CONTROLLER_TIME.removeMdcField();
        MDCFields.CONTROLLER_METHOD.removeMdcField();
        MDCFields.CONTROLLER_STEP.removeMdcField();

        // Rethrow the exception wrapped in a custom exception
        throw new LogException(ex.getMessage(), ex);
    }
}
