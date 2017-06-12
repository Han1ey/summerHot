package xyz.fz.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/6/12.
 */
@Aspect
@Component
public class QueryTimeAspect {

    private static final Logger logger = LoggerFactory.getLogger(QueryTimeAspect.class);

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {}

    @Around("controller()")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object object = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        logger.warn("Query Time: {}ms, {}", (endTime - beginTime), proceedingJoinPoint.getSignature().toLongString());
        return object;
    }
}
