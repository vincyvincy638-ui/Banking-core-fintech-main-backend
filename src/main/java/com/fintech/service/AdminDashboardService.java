package com.fintech.service;

import com.fintech.entity.Transaction;
import com.fintech.repository.TransactionRepository;
import com.fintech.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminDashboardService {

    private final UserRepository userRepo;
    private final TransactionRepository txRepo;

    public AdminDashboardService(UserRepository userRepo, TransactionRepository txRepo) {
        this.userRepo = userRepo;
        this.txRepo = txRepo;
    }

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();

        data.put("totalUsers", userRepo.count());
        data.put("totalTransactions", txRepo.count());

        List<Transaction> allTxns = txRepo.findAll();

        Map<String, Double> totalCredit = new HashMap<>();
        Map<String, Double> totalDebit = new HashMap<>();
        Map<String, Double> balances = new HashMap<>();

        for (Transaction tx : allTxns) {
            String fromUser = tx.getFromUser();
            String toUser = tx.getToUser();
            double amount = tx.getAmount();

            if ("CREDIT".equalsIgnoreCase(tx.getType())) {
                balances.put(toUser, balances.getOrDefault(toUser, 0.0) + amount);
                totalCredit.put(toUser, totalCredit.getOrDefault(toUser, 0.0) + amount);
            }
            if ("DEBIT".equalsIgnoreCase(tx.getType())) {
                balances.put(fromUser, balances.getOrDefault(fromUser, 0.0) - amount);
                totalDebit.put(fromUser, totalDebit.getOrDefault(fromUser, 0.0) + amount);
            }
        }

        List<Transaction> recent = allTxns.stream()
                .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
                .limit(5)
                .collect(Collectors.toList());

        data.put("recentTransactions", recent);
        data.put("balances", balances);

        Map<String, Map<String, Double>> totalPerUser = new HashMap<>();
        for (String user : balances.keySet()) {
            Map<String, Double> totals = new HashMap<>();
            totals.put("credit", totalCredit.getOrDefault(user, 0.0));
            totals.put("debit", totalDebit.getOrDefault(user, 0.0));
            totalPerUser.put(user, totals);
        }
        data.put("totalPerUser", totalPerUser);

        return data;
    }
}
