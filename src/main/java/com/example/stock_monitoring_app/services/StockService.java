package com.example.stock_monitoring_app.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Service
public class StockService {

    private final String API_KEY = "WQ9NWEE069YEMCT8"; // Get from https://www.alphavantage.co/
    private final RestTemplate restTemplate = new RestTemplate();

    @Async
    public CompletableFuture<BigDecimal> getStockPrice(String symbol) {
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + API_KEY;
        try {
            String response = restTemplate.getForObject(url, String.class);
            if (response != null && response.contains("\"05. price\"")) {
                String priceStr = response.split("\"05. price\": \"")[1].split("\"")[0];
                BigDecimal price = new BigDecimal(priceStr);
                return CompletableFuture.completedFuture(price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }
}
