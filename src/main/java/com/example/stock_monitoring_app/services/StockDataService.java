package com.example.stock_monitoring_app.services;

import com.example.stock_monitoring_app.model.AlphaVantageResponse;
import com.example.stock_monitoring_app.model.TimeSeriesData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class StockDataService {

    private static final String BASE_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&apikey=WQ9NWEE069YEMCT8";

    private final RestTemplate restTemplate;

    // Inject the RestTemplate via constructor (best practice)
    public StockDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AlphaVantageResponse getStockData(String symbol, String interval) {
        String url = String.format("%s&symbol=%s&interval=%s", BASE_URL, symbol, interval);
        return restTemplate.getForObject(url, AlphaVantageResponse.class);
    }

    /**
     * Helper method to extract the closing prices within a given date-time range.
     */
    private List<Double> getClosePricesInRange(AlphaVantageResponse response, String from, String to) {
        if (response == null || response.getTimeSeries5min() == null) {
            return Collections.emptyList();
        }

        Map<String, TimeSeriesData> timeSeries = response.getTimeSeries5min();
        List<Double> prices = new ArrayList<>();

        for (Map.Entry<String, TimeSeriesData> entry : timeSeries.entrySet()) {
            String dateTime = entry.getKey();
            // Check if the dateTime is within the specified range
            if (dateTime.compareTo(from) >= 0 && dateTime.compareTo(to) <= 0) {
                double closePrice = Double.parseDouble(entry.getValue().getClose());
                prices.add(closePrice);
            }
        }
        return prices;
    }

    public double getMaximumPrice(AlphaVantageResponse response, String from, String to) {
        List<Double> prices = getClosePricesInRange(response, from, to);
        return prices.isEmpty() ? 0 : Collections.max(prices);
    }

    public double getMinimumPrice(AlphaVantageResponse response, String from, String to) {
        List<Double> prices = getClosePricesInRange(response, from, to);
        return prices.isEmpty() ? 0 : Collections.min(prices);
    }

    public double getAveragePrice(AlphaVantageResponse response, String from, String to) {
        List<Double> prices = getClosePricesInRange(response, from, to);
        if (prices.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (double price : prices) {
            sum += price;
        }
        return sum / prices.size();
    }

    public double getStandardDeviation(AlphaVantageResponse response, String from, String to) {
        List<Double> prices = getClosePricesInRange(response, from, to);
        int count = prices.size();

        if (count <= 1) {
            return 0;
        }

        double mean = getAveragePrice(response, from, to);
        double sumOfSquares = 0;

        for (double price : prices) {
            sumOfSquares += Math.pow(price - mean, 2);
        }

        return Math.sqrt(sumOfSquares / (count - 1));
    }
}
