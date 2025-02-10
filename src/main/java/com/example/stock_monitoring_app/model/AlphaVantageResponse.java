package com.example.stock_monitoring_app.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class AlphaVantageResponse {

    @JsonProperty("Meta Data")
    private MetaData metaData;

    // The time series is a map of date-time (String) to a TimeSeriesData object
    @JsonProperty("Time Series (5min)")
    private Map<String, TimeSeriesData> timeSeries5min;

    // Getters and setters (or use Lombok @Data to generate them)
    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public Map<String, TimeSeriesData> getTimeSeries5min() {
        return timeSeries5min;
    }

    public void setTimeSeries5min(Map<String, TimeSeriesData> timeSeries5min) {
        this.timeSeries5min = timeSeries5min;
    }
}

