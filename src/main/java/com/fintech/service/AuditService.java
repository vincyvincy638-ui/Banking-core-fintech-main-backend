package com.fintech.service;

import com.fintech.entity.AuditLog;
import com.fintech.repository.AuditLogRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void saveLog(AuditLog log) {
        auditLogRepository.save(log);
    }

    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    public List<AuditLog> getLogsByAction(String action) {
        return auditLogRepository.findByActionIgnoreCase(action);
    }

    public List<AuditLog> getLogsByUsername(String username) {
        return auditLogRepository.findByUsernameIgnoreCase(username);
    }

    public List<AuditLog> getStockLogs(String action, int days, int limit) {
        LocalDateTime fromDate = LocalDateTime.now().minusDays(days);
        return auditLogRepository.findAll(PageRequest.of(0, limit, Sort.by("timestamp").descending()))
                .getContent().stream()
                .filter(log -> log.getAction() != null
                        && log.getAction().equalsIgnoreCase(action)
                        && log.getTimestamp() != null
                        && log.getTimestamp().isAfter(fromDate))
                .toList();
    }
}
