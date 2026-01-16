package com.fintech.aspect;

import com.fintech.entity.AuditLog;
import com.fintech.service.AuditService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    private final AuditService auditService;

    public AuditAspect(AuditService auditService) {
        this.auditService = auditService;
    }

    @Around("execution(* com.fintech.controller.*.*(..))")
    public Object auditControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            result = joinPoint.proceed();

            AuditLog log = new AuditLog();
            log.setUsername("SYSTEM");
            log.setAction(getAction(joinPoint.getSignature().getName()));
            log.setDescription("Executed: " + joinPoint.getSignature().toShortString());
            auditService.saveLog(log);

            return result;
        } catch (Exception ex) {
            AuditLog log = new AuditLog();
            log.setUsername("SYSTEM");
            log.setAction("ERROR");
            log.setDescription(ex.getMessage());
            auditService.saveLog(log);
            throw ex;
        }
    }

    private String getAction(String methodName) {
        if (methodName.toLowerCase().contains("buy"))
            return "BUY";
        if (methodName.toLowerCase().contains("sell"))
            return "SELL";
        if (methodName.toLowerCase().contains("transfer"))
            return "TRANSFER";
        if (methodName.toLowerCase().contains("login"))
            return "LOGIN";
        return "GENERAL";
    }
}
