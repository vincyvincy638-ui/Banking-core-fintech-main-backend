package com.fintech.repository;

import com.fintech.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Fetch transactions for a user
    List<Transaction> findByFromUserOrToUserOrderByTimestampDesc(String fromUser, String toUser);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.toUser = ?1 AND t.type = 'CREDIT'")
    Double totalCredit(String username);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.fromUser = ?1 AND t.type = 'DEBIT'")
    Double totalDebit(String username);
}
