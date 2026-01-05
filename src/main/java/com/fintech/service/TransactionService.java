package com.fintech.service;

import com.fintech.entity.Transaction;
import com.fintech.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository txRepo;

    public TransactionService(TransactionRepository txRepo) {
        this.txRepo = txRepo;
    }

    // Create a transaction
    public Transaction createTransaction(String fromUser, String toUser, Double amount, String type) {
        Transaction tx = new Transaction();
        tx.setFromUser(fromUser);
        tx.setToUser(toUser);
        tx.setAmount(amount);
        tx.setType(type);
        tx.setTimestamp(LocalDateTime.now());
        return txRepo.save(tx);
    }

    // Get all transactions for a user
    public List<Transaction> userTransactions(String username) {
        return txRepo.findByFromUserOrToUserOrderByTimestampDesc(username, username);
    }

    // Get all transactions (ADMIN)
    public List<Transaction> allTransactions() {
        return txRepo.findAll();
    }

    public Double totalCredit(String username) {
        return txRepo.totalCredit(username);
    }

    public Double totalDebit(String username) {
        return txRepo.totalDebit(username);
    }
}
