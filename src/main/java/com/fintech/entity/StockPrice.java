package com.fintech.entity;

import java.time.LocalDateTime;

public class StockPrice {

    private String symbol;
    private Double price;
    private LocalDateTime timestamp;

    public StockPrice(String symbol, Double price, LocalDateTime timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
