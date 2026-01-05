package com.fintech.service;

import com.fintech.entity.Transaction;
import com.fintech.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    private final TransactionRepository transactionRepository;

    public TransferService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public String transferMoney(String from, String to, Double amount) {
        // Debit from sender
        Transaction debit = new Transaction();
        debit.setFromUser(from);
        debit.setToUser(to);
        debit.setAmount(amount);
        debit.setType("DEBIT");
        debit.setTimestamp(java.time.LocalDateTime.now());
        transactionRepository.save(debit);

        // Credit to receiver
        Transaction credit = new Transaction();
        credit.setFromUser(from);
        credit.setToUser(to);
        credit.setAmount(amount);
        credit.setType("CREDIT");
        credit.setTimestamp(java.time.LocalDateTime.now());
        transactionRepository.save(credit);

        return "Transfer successful: " + amount + " from " + from + " to " + to;
    }
}
