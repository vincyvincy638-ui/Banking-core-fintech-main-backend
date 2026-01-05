package com.fintech.controller;

import com.fintech.entity.Transaction;
import com.fintech.service.AdminDashboardService;
import com.fintech.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminDashboardService dashboardService;
    private final TransactionService transactionService;

    public AdminController(AdminDashboardService dashboardService,
            TransactionService transactionService) {
        this.dashboardService = dashboardService;
        this.transactionService = transactionService;
    }

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {
        return dashboardService.dashboard();
    }

    @GetMapping("/all-transactions")
    public List<Transaction> getAllTransactions() {
        return transactionService.allTransactions();
    }
}
