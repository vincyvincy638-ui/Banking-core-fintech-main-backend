package com.fintech.controller;

import com.fintech.service.TransactionService;
import com.fintech.entity.Transaction;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    // Transfer money (from authenticated user)
    @PostMapping("/transfer")
    public List<Transaction> transfer(
            @RequestParam String toUser,
            @RequestParam Double amount,
            Authentication auth) {

        String fromUser = auth.getName();

        Transaction debit = service.createTransaction(fromUser, toUser, amount, "DEBIT");
        Transaction credit = service.createTransaction(fromUser, toUser, amount, "CREDIT");

        return List.of(debit, credit);
    }

    // Get my transactions
    @GetMapping("/my")
    public List<Transaction> myTransactions(Authentication auth) {
        return service.userTransactions(auth.getName());
    }

    // ADMIN: Get all transactions with per-user summary
    @GetMapping("/all")
    public Map<String, Object> allTransactionsSummary() {
        List<Transaction> allTxns = service.allTransactions();
        Map<String, Double> balances = new HashMap<>();
        Map<String, Double> totalCredit = new HashMap<>();
        Map<String, Double> totalDebit = new HashMap<>();

        for (Transaction tx : allTxns) {
            String from = tx.getFromUser();
            String to = tx.getToUser();
            double amt = tx.getAmount();

            if ("CREDIT".equalsIgnoreCase(tx.getType())) {
                balances.put(to, balances.getOrDefault(to, 0.0) + amt);
                totalCredit.put(to, totalCredit.getOrDefault(to, 0.0) + amt);
            }

            if ("DEBIT".equalsIgnoreCase(tx.getType())) {
                balances.put(from, balances.getOrDefault(from, 0.0) - amt);
                totalDebit.put(from, totalDebit.getOrDefault(from, 0.0) + amt);
            }
        }

        // Prepare per-user summary
        Map<String, Map<String, Double>> totalPerUser = new HashMap<>();
        Set<String> users = new HashSet<>();
        users.addAll(balances.keySet());
        users.addAll(totalCredit.keySet());
        users.addAll(totalDebit.keySet());

        for (String user : users) {
            Map<String, Double> totals = new HashMap<>();
            totals.put("credit", totalCredit.getOrDefault(user, 0.0));
            totals.put("debit", totalDebit.getOrDefault(user, 0.0));
            totals.put("balance", balances.getOrDefault(user, 0.0));
            totalPerUser.put(user, totals);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("transactions", allTxns);
        result.put("perUserSummary", totalPerUser);

        return result;
    }
}
