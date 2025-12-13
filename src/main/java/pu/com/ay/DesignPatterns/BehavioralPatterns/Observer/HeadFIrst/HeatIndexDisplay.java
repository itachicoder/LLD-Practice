package pu.com.ay.DesignPatterns.BehavioralPatterns.Observer.HeadFIrst;

/**
 * HeatIndexDisplay - Concrete Observer #4
 * Calculates and displays heat index (feels-like temperature)
 * Shows warnings for extreme conditions
 * 
 * Think of this as: "A subscriber who only reacts to specific conditions"
 */
public class HeatIndexDisplay implements Observer {
    private float heatIndex = 0.0f;
    
    public HeatIndexDisplay(Subject weatherStation) {
        weatherStation.registerObserver(this);
    }
    
    /**
     * Gets called when weather updates
     * This observer performs CALCULATIONS on the data
     */
    @Override
    public void update(float temperature, float humidity, float pressure) {
        heatIndex = calculateHeatIndex(temperature, humidity);
        display();
    }
    
    /**
     * Heat index formula (simplified)
     * Calculates "feels like" temperature
     */
    private float calculateHeatIndex(float t, float rh) {
        float heatIndex = (float)
            ((16.923 + (0.185212 * t) + (5.37941 * rh) - (0.100254 * t * rh) +
            (0.00941695 * (t * t)) + (0.00728898 * (rh * rh)) +
            (0.000345372 * (t * t * rh)) - (0.000814971 * (t * rh * rh)) +
            (0.0000102102 * (t * t * rh * rh)) - (0.000038646 * (t * t * t)) +
            (0.0000291583 * (rh * rh * rh)) +
            (0.00000142721 * (t * t * t * rh)) +
            (0.000000197483 * (t * rh * rh * rh)) -
            (0.0000000218429 * (t * t * t * rh * rh)) +
            0.000000000843296 * (t * t * rh * rh * rh)) -
            (0.0000000000481975 * (t * t * t * rh * rh * rh)));
        return heatIndex;
    }
    
    public void display() {
        System.out.println("🌡️  Heat Index Display:");
        System.out.println("   Heat Index: " + String.format("%.2f", heatIndex) + "°C");
        
        // Show warnings for extreme conditions
        if (heatIndex > 40) {
            System.out.println("   ⚠️  WARNING: Extreme heat! Stay indoors!");
        } else if (heatIndex > 32) {
            System.out.println("   ⚠️  CAUTION: High heat index!");
        } else if (heatIndex < 0) {
            System.out.println("   ❄️  WARNING: Freezing conditions!");
        }
        System.out.println();
    }
}