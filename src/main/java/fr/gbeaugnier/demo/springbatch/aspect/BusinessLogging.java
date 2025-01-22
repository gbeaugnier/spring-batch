package fr.gbeaugnier.demo.springbatch.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class BusinessLogging {

    @Before("execution(* fr.gbeaugnier.demo.springbatch.business.service.TeamUserService.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("  --> {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* fr.gbeaugnier.demo.springbatch.business.service.TeamUserService.*(..))", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        log.info("  <-- {} : result {}", joinPoint.getSignature().getName(), result);
    }

}
