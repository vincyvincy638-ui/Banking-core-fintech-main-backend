package com.fintech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StockController {

        @GetMapping("/stocks")
        public Map<String, Object> getPortfolio() {
                List<Map<String, Object>> stocks = List.of(
                                Map.of(
                                                "symbol", "TCS",
                                                "quantity", 10,
                                                "currentPrice", 3521.0,
                                                "value", 10 * 3521.0),
                                Map.of(
                                                "symbol", "INFY",
                                                "quantity", 20,
                                                "currentPrice", 1450.0,
                                                "value", 20 * 1450.0),
                                Map.of(
                                                "symbol", "HDFC",
                                                "quantity", 5,
                                                "currentPrice", 1670.0,
                                                "value", 5 * 1670.0));

                double totalValue = stocks.stream()
                                .mapToDouble(stock -> (double) stock.get("value"))
                                .sum();

                return Map.of(
                                "totalValue", totalValue,
                                "stocks", stocks);
        }
}
