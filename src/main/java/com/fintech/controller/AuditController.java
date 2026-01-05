package com.fintech.controller;

import com.fintech.entity.AuditLog;
import com.fintech.service.AuditService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/logs")
    public List<AuditLog> getAllLogs() {
        return auditService.getAllLogs();
    }
}
