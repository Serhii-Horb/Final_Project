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

    @Around("execution(* de.telran.shoponline.controller..*(..)))")
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

    private void logBeforeControllerQuery(final String queryMethod, final Object[] args) {
        MDCFields.CONTROLLER_STEP.putMdcField("CONTROLLER_IN");
        MDCFields.CONTROLLER_METHOD.putMdcFieldWithFieldName(queryMethod);
        String argsAsString = Arrays.toString(args);
        log.info("args={};", argsAsString);
        MDCFields.CONTROLLER_METHOD.removeMdcField();
        MDCFields.CONTROLLER_STEP.removeMdcField();
    }

    private void logAfterControllerQuery(final String queryMethod, final Object[] args, final Object result, final long startTime) {
        long callTime = System.currentTimeMillis() - startTime;
        String resultInfo = LogUtils.getDaoResultLogInfo(log, result);
        MDCFields.CONTROLLER_STEP.putMdcField("CONTROLLER_OUT");
        MDCFields.CONTROLLER_METHOD.putMdcFieldWithFieldName(queryMethod);
        MDCFields.CONTROLLER_TIME.putMdcFieldWithFieldName(callTime);
        String argsAsString = Arrays.toString(args);
        log.info(
                "args={}; RESULT: [{}]",
                argsAsString,
                resultInfo
        );
        MDCFields.CONTROLLER_TIME.removeMdcField();
        MDCFields.CONTROLLER_METHOD.removeMdcField();
        MDCFields.CONTROLLER_STEP.removeMdcField();
    }

    private void logAndGetErrorMessage(final String queryMethod, final Object[] args, final Exception ex, final long startTime) {
        long callTime = System.currentTimeMillis() - startTime;
        String errorMsg = String.format(
                "args=%s;",
                Arrays.toString(args)
        );

        MDCFields.CONTROLLER_STEP.putMdcField("CONTROLLER_ERROR");
        MDCFields.CONTROLLER_METHOD.putMdcFieldWithFieldName(queryMethod);
        MDCFields.CONTROLLER_TIME.putMdcFieldWithFieldName(callTime);
        log.error(errorMsg, ex);
        MDCFields.CONTROLLER_TIME.removeMdcField();
        MDCFields.CONTROLLER_METHOD.removeMdcField();
        MDCFields.CONTROLLER_STEP.removeMdcField();
        throw new LogException(ex.getMessage(), ex);
    }

}
