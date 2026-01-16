package com.fintech.repository;

import com.fintech.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    // By action
    List<AuditLog> findByActionIgnoreCase(String action);

    // By username
    List<AuditLog> findByUsernameIgnoreCase(String username);

    // By date range
    List<AuditLog> findByTimestampBetween(LocalDateTime from, LocalDateTime to);

    // Stock action + date filter
    List<AuditLog> findByActionIgnoreCaseAndTimestampAfter(String action, LocalDateTime fromDate);
}
