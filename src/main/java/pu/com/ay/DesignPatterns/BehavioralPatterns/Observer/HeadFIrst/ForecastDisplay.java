package pu.com.ay.DesignPatterns.BehavioralPatterns.Observer.HeadFIrst;

/**
 * ForecastDisplay - Concrete Observer #3
 * Predicts weather based on pressure changes
 * 
 * Think of this as: "A subscriber who uses updates to make predictions"
 */
public class ForecastDisplay implements Observer {
    private float currentPressure = 29.92f;
    private float lastPressure;
    
    public ForecastDisplay(Subject weatherStation) {
        weatherStation.registerObserver(this);
    }
    
    /**
     * Gets called when weather updates
     * This observer COMPARES current with previous data
     */
    @Override
    public void update(float temperature, float humidity, float pressure) {
        lastPressure = currentPressure;
        currentPressure = pressure;
        display();
    }
    
    public void display() {
        System.out.println("🔮 Forecast Display:");
        System.out.print("   Forecast: ");
        
        if (currentPressure > lastPressure) {
            System.out.println("Improving weather on the way! ☀️");
        } else if (currentPressure == lastPressure) {
            System.out.println("More of the same 🌤️");
        } else if (currentPressure < lastPressure) {
            System.out.println("Watch out for cooler, rainy weather 🌧️");
        }
        System.out.println();
    }
}