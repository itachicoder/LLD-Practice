package pu.com.ay.DesignPatterns.StructuralPatterns;

import org.json.JSONObject;
import org.json.XML;

public class StockMarketAnalysis {

    // Client interface for displaying stock data
    interface StockDataDisplay {
        void displayStockData(String xmlData);
    }

    // Existing client implementation that uses XML data
    static class StockApp implements StockDataDisplay {
        @Override
        public void displayStockData(String xmlData) {
            System.out.println("Displaying stock data in XML format:");
            System.out.println(xmlData);
        }
    }

    // 3rd-party analytics library that only accepts JSON data
    static class AnalyticsLibrary {
        public void analyzeData(String jsonData) {
            System.out.println("Analyzing stock data in JSON format:");
            System.out.println(jsonData);
        }
    }

    // Adapter class that converts XML data to JSON and passes it to the AnalyticsLibrary
    static class StockDataAdapter implements StockDataDisplay {
        private final AnalyticsLibrary analyticsLibrary;

        public StockDataAdapter(AnalyticsLibrary analyticsLibrary) {
            this.analyticsLibrary = analyticsLibrary;
        }

        @Override
        public void displayStockData(String xmlData) {
            // Convert XML data to JSON
            String jsonData = convertXmlToJson(xmlData);
            // Pass JSON data to the analytics library
            analyticsLibrary.analyzeData(jsonData);
        }

        private String convertXmlToJson(String xmlData) {
            JSONObject json = XML.toJSONObject(xmlData);
            return json.toString();
        }
    }

    public static void main(String[] args) {
        // Sample XML stock data
        String xmlStockData = "<stock><symbol>APPL</symbol><price>150</price></stock>";

        // Use the StockApp with XML data directly
        StockApp stockApp = new StockApp();
        stockApp.displayStockData(xmlStockData);

        // Use the StockDataAdapter to allow the StockApp to work with the AnalyticsLibrary
        AnalyticsLibrary analyticsLibrary = new AnalyticsLibrary();
        StockDataDisplay adapter = new StockDataAdapter(analyticsLibrary);
        adapter.displayStockData(xmlStockData);
    }
}
