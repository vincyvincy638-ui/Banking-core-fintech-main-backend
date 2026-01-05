package com.fintech.service;

import com.fintech.entity.StockPrice;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class StockService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<StockPrice> fetchStocksWithLatency() {

        long start = System.currentTimeMillis();

        StockPrice[] response = restTemplate.getForObject(
                "http://localhost:8080/api/stocks/prices",
                StockPrice[].class);

        long end = System.currentTimeMillis();
        long latency = end - start;

        System.out.println("⏱ Stock API latency: " + latency + " ms");

        return Arrays.asList(response);
    }
}
