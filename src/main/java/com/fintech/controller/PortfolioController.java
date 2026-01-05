package com.fintech.controller;

import com.fintech.service.PortfolioService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/portfolio")
    public Map<String, Object> getPortfolio() {

        long start = System.currentTimeMillis();

        Map<String, Object> data = portfolioService.getPortfolio();

        long end = System.currentTimeMillis();
        System.out.println("API Latency: " + (end - start) + " ms");

        return data;
    }
}
