package com.example.stock_monitoring_app.controller;

//package com.example.stockanalyzer.controller;

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

    public StockController(StockService stockService) {
        this.stockService = stockService;
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
}

