package pu.com.ay.DesignPatterns.BehavioralPatterns.Observer.HeadFIrst;

/**
 * CurrentConditionsDisplay - Concrete Observer #1
 * Displays current weather conditions
 * 
 * Think of this as: "A subscriber who wants instant updates"
 */
public class CurrentConditionsDisplay implements Observer {
    private float temperature;
    private float humidity;
    private Subject weatherStation; // Reference to subject (optional)
    
    /**
     * Constructor - automatically subscribe to weather station
     * @param weatherStation The subject to observe
     */
    public CurrentConditionsDisplay(Subject weatherStation) {
        this.weatherStation = weatherStation;
        // Subscribe to the weather station
        weatherStation.registerObserver(this);
    }
    
    /**
     * This gets called automatically when weather changes!
     * The WeatherStation "pushes" data to this observer
     */
    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }
    
    /**
     * Display the current conditions
     */
    public void display() {
        System.out.println("📺 Current Conditions Display:");
        System.out.println("   Temperature: " + temperature + "°C");
        System.out.println("   Humidity: " + humidity + "%");
        System.out.println();
    }
}