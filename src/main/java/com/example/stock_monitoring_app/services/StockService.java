package com.example.stock_monitoring_app.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Service
public class StockService {

    @Async
    public CompletableFuture<BigDecimal> getStockPrice(String symbol) {
        try {
            Stock stock = YahooFinance.get(symbol);
            BigDecimal price = stock.getQuote().getPrice();
            return CompletableFuture.completedFuture(price);
        } catch (IOException e) {
            return CompletableFuture.completedFuture(null);
        }
    }
}

