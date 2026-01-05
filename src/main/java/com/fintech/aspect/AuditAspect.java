package com.fintech.aspect;

import com.fintech.entity.AuditLog;
import com.fintech.service.AuditService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class AuditAspect {

    private final AuditService auditService;

    public AuditAspect(AuditService auditService) {
        this.auditService = auditService;
    }

    @Around("execution(* com.fintech..*(..)) && !within(com.fintech.aspect..*)")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        String params = Arrays.toString(joinPoint.getArgs());
        Object result = null;

        try {
            result = joinPoint.proceed();
            return result;
        } finally {
            String returnValue = result != null ? result.toString() : "void";
            AuditLog log = new AuditLog(methodName, params, returnValue, LocalDateTime.now());
            auditService.saveLog(log);
        }
    }
}
