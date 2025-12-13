package pu.com.ay.DesignPatterns.BehavioralPatterns.Observer.HeadFIrst;

/**
 * StatisticsDisplay - Concrete Observer #2
 * Tracks statistics over time (min, max, average temperature)
 * 
 * Think of this as: "A subscriber who analyzes all updates over time"
 */
public class StatisticsDisplay implements Observer {
    private float maxTemp = Float.MIN_VALUE;
    private float minTemp = Float.MAX_VALUE;
    private float tempSum = 0.0f;
    private int numReadings = 0;
    
    public StatisticsDisplay(Subject weatherStation) {
        weatherStation.registerObserver(this);
    }
    
    /**
     * Gets called when weather updates
     * This observer ACCUMULATES data over time
     */
    @Override
    public void update(float temperature, float humidity, float pressure) {
        tempSum += temperature;
        numReadings++;
        
        if (temperature > maxTemp) {
            maxTemp = temperature;
        }
        
        if (temperature < minTemp) {
            minTemp = temperature;
        }
        
        display();
    }
    
    public void display() {
        System.out.println("📊 Statistics Display:");
        System.out.println("   Avg Temperature: " + (tempSum / numReadings) + "°C");
        System.out.println("   Max Temperature: " + maxTemp + "°C");
        System.out.println("   Min Temperature: " + minTemp + "°C");
        System.out.println();
    }
}