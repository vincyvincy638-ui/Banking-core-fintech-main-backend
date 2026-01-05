package com.fintech.service;

import com.fintech.model.Stock;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioService {

    public Map<String, Object> getPortfolio() {

        List<Stock> stocks = new ArrayList<>();

        stocks.add(new Stock("TCS", 10, 3521));
        stocks.add(new Stock("INFY", 20, 1450));
        stocks.add(new Stock("HDFC", 5, 1670));

        double totalValue = stocks.stream()
                .mapToDouble(Stock::getValue)
                .sum();

        Map<String, Object> response = new HashMap<>();
        response.put("totalValue", totalValue);
        response.put("stocks", stocks);

        return response;
    }
}
