package com.fintech.service;

import com.fintech.util.PDFGenerator;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatementService {

    public byte[] generateMonthlyStatement(int month, int year) throws Exception {
        // Mock data – replace with DB queries
        List<Map<String, String>> transactions = new ArrayList<>();

        Map<String, String> txn1 = new HashMap<>();
        txn1.put("date", "2026-01-01");
        txn1.put("description", "Deposit");
        txn1.put("amount", "$1000");
        transactions.add(txn1);

        Map<String, String> txn2 = new HashMap<>();
        txn2.put("date", "2026-01-15");
        txn2.put("description", "Withdrawal");
        txn2.put("amount", "$500");
        transactions.add(txn2);

        return PDFGenerator.generateMonthlyStatement(transactions);
    }
}
