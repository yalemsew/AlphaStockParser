# 📈 Stock Market Data Parser in Java

## 🚀 Overview

This project parses stock market data from Alpha Vantage's API and converts it into Java objects using the **Jackson** library. The parsed data includes stock metadata and time-series information (open, high, low, close prices, and volume) at a one-minute interval.

## 📡 API Source

The project retrieves stock data from the Alpha Vantage API:

- Example API URL:  
https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=1min&apikey=YOUR_API_KEY



## 🛠️ Features

- Fetches stock market data in **JSON** format.
- Converts JSON data into **Java objects** using **Jackson**.
- Stores **metadata** and **time-series** stock information.
- Provides structured access to stock data for further processing.

## 📂 Project Structure




## 🔧 Dependencies

Ensure you have **Java 11+** and **Maven** installed.

### 🏗️ Maven Dependency:
Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.0</version>
</dependency>
