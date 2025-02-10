package com.example.stock_monitoring_app.controller;

import com.example.stock_monitoring_app.model.AlphaVantageResponse;
import com.example.stock_monitoring_app.services.StockDataService;
import com.example.stock_monitoring_app.services.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class StockController {

    private final StockService stockService;
    private final StockDataService stockDataService;

    public StockController(StockService stockService, StockDataService stockDataService) {
        this.stockService = stockService;
        this.stockDataService = stockDataService;
    }

    @GetMapping("/fetch-prices")
    public List<String> getStockPrices(@RequestParam List<String> symbols) {
        List<CompletableFuture<BigDecimal>> futures = symbols.stream()
                .map(stockService::getStockPrice)
                .collect(Collectors.toList());

        return futures.stream()
                .map(CompletableFuture::join)
                .map(price -> price != null ? "Price: $" + price : "Unavailable")
                .collect(Collectors.toList());
    }
    @GetMapping("/get-maximum-price")
    public String getMaximumPrice(@RequestParam String symbol, @RequestParam String from, @RequestParam String to) {
        AlphaVantageResponse response = stockDataService.getStockData(symbol, "5min");
        double maxPrice = stockDataService.getMaximumPrice(response, from, to);
        return "Maximum price: $" + maxPrice;
    }
}

