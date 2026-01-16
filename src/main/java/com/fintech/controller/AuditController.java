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

    @GetMapping("/logs/action/{action}")
    public List<AuditLog> getByAction(@PathVariable String action) {
        return auditService.getLogsByAction(action);
    }

    @GetMapping("/logs/user/{username}")
    public List<AuditLog> getByUser(@PathVariable String username) {
        return auditService.getLogsByUsername(username);
    }

    @GetMapping("/logs/stock")
    public List<AuditLog> stockFilter(
            @RequestParam String action,
            @RequestParam int days,
            @RequestParam int limit) {
        return auditService.getStockLogs(action, days, limit);
    }
}
