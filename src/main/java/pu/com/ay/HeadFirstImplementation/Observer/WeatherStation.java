package pu.com.ay.HeadFirstImplementation.Observer;

public class WeatherStation {
    public static void main(String[] args) {
        // Create the WeatherData object (Subject)
        WeatherData weatherData = new WeatherData();
        
        // Create displays and register them with WeatherData
        CurrentConditionsDisplay currentDisplay = 
            new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = 
            new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = 
            new ForecastDisplay(weatherData);
        HeatIndexDisplay heatIndexDisplay = 
            new HeatIndexDisplay(weatherData);
        
        // Simulate new weather measurements
        System.out.println("=== Weather Update 1 ===");
        weatherData.setMeasurements(80, 65, 30.4f);
        
        System.out.println("\n=== Weather Update 2 ===");
        weatherData.setMeasurements(82, 70, 29.2f);
        
        System.out.println("\n=== Weather Update 3 ===");
        weatherData.setMeasurements(78, 90, 29.2f);
        
        // Demonstrate removing an observer
        System.out.println("\n=== Removing Current Conditions Display ===");
        weatherData.removeObserver(currentDisplay);
        
        System.out.println("\n=== Weather Update 4 (without current conditions) ===");
        weatherData.setMeasurements(75, 80, 30.1f);
    }
}