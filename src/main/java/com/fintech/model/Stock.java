package com.fintech.model;

public class Stock {

    private String symbol;
    private int quantity;
    private double currentPrice;
    private double value;

    public Stock(String symbol, int quantity, double currentPrice) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.value = quantity * currentPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getValue() {
        return value;
    }
}
