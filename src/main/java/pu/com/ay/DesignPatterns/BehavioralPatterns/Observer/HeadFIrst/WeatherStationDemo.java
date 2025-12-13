package pu.com.ay.DesignPatterns.BehavioralPatterns.Observer.HeadFIrst;

/**
 * WeatherStationDemo - Demonstrates Observer Pattern
 * 
 * This shows:
 * 1. How to create subject (WeatherStation)
 * 2. How observers subscribe
 * 3. How one update notifies ALL observers automatically
 * 4. How to unsubscribe observers
 */
public class WeatherStationDemo {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          OBSERVER PATTERN - WEATHER STATION DEMO           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        // 1. CREATE THE SUBJECT (The thing being observed)
        // Think: Creating a YouTube Channel
        System.out.println("🏗️  Step 1: Creating Weather Station (Subject)");
        WeatherStation weatherStation = new WeatherStation();
        System.out.println();
        
        // 2. CREATE OBSERVERS (The subscribers)
        // Think: People subscribing to YouTube channel
        System.out.println("👥 Step 2: Creating Observers (Subscribers)");
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherStation);
        StatisticsDisplay statsDisplay = new StatisticsDisplay(weatherStation);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherStation);
        HeatIndexDisplay heatIndexDisplay = new HeatIndexDisplay(weatherStation);
        System.out.println();
        
        // 3. UPDATE DATA - ALL OBSERVERS GET NOTIFIED AUTOMATICALLY!
        // Think: YouTube channel posting new video
        System.out.println("🎬 Step 3: Updating Weather Data");
        System.out.println("Watch how ONE update notifies ALL 4 observers!\n");
        
        weatherStation.setMeasurements(26.6f, 65f, 30.4f);
        
        // Wait a moment...
        pause();
        
        // Another update - again, all observers notified automatically
        weatherStation.setMeasurements(28.5f, 70f, 29.2f);
        
        pause();
        
        // 4. DEMONSTRATE UNSUBSCRIBE
        System.out.println("🔕 Step 4: Unsubscribing Current Conditions Display");
        weatherStation.removeObserver(currentDisplay);
        System.out.println();
        
        // Update after unsubscribe - only 3 observers get notified now
        System.out.println("📤 Updating weather data (CurrentDisplay won't be notified)");
        weatherStation.setMeasurements(29.8f, 90f, 29.2f);
        
        pause();
        
        // 5. RE-SUBSCRIBE
        System.out.println("🔔 Step 5: Re-subscribing Current Conditions Display");
        weatherStation.registerObserver(currentDisplay);
        System.out.println();
        
        // Update after re-subscribe - all 4 observers notified again
        System.out.println("📤 Updating weather data (All displays notified)");
        weatherStation.setMeasurements(31.0f, 75f, 30.1f);
        
        // 6. SUMMARY
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📝 OBSERVER PATTERN BENEFITS DEMONSTRATED:");
        System.out.println("=".repeat(60));
        System.out.println("✅ Loose Coupling: WeatherStation doesn't know observer details");
        System.out.println("✅ Dynamic: Observers can subscribe/unsubscribe at runtime");
        System.out.println("✅ Broadcast: One update reaches all observers automatically");
        System.out.println("✅ Flexibility: Each observer reacts differently to same data");
        System.out.println("✅ Extensibility: Easy to add new observer types");
        System.out.println("=".repeat(60));
    }
    
    /**
     * Helper method to pause between updates for readability
     */
    private static void pause() {
        try {
            System.out.println("\n⏸️  (Pausing for readability...)\n");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
}