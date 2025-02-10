package com.example.stock_monitoring_app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeSeriesData {

    // Getters and setters
    @JsonProperty("1. open")
    private String open;

    @JsonProperty("2. high")
    private String high;

    @JsonProperty("3. low")
    private String low;

    @JsonProperty("4. close")
    private String close;

    @JsonProperty("5. volume")
    private String volume;

    public String getClose() {
        return close;
    }

}

