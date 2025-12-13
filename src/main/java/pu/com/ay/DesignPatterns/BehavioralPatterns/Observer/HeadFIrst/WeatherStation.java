package pu.com.ay.DesignPatterns.BehavioralPatterns.Observer.HeadFIrst;

import java.util.ArrayList;
import java.util.List;

/**
 * WeatherStation - Concrete Subject
 * This is the "YouTube Channel" that observers subscribe to
 * 
 * Responsibilities:
 * 1. Hold the weather data (state)
 * 2. Maintain list of observers
 * 3. Notify all observers when data changes
 */
public class WeatherStation implements Subject {
    // List of all subscribers (observers)
    private List<Observer> observers;
    
    // The data (state) that observers care about
    private float temperature;
    private float humidity;
    private float pressure;
    
    /**
     * Constructor - initialize the list of observers
     */
    public WeatherStation() {
        this.observers = new ArrayList<>();
    }
    
    /**
     * Subscribe - Add a new observer
     * Like clicking "Subscribe" button on YouTube
     */
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println("✅ New observer registered! Total observers: " + observers.size());
    }
    
    /**
     * Unsubscribe - Remove an observer
     * Like clicking "Unsubscribe" button
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("❌ Observer removed! Total observers: " + observers.size());
    }
    
    /**
     * The MAGIC happens here!
     * When this is called, ALL observers get notified automatically
     * Like when YouTube channel posts a video - all subscribers get notification
     */
    @Override
    public void notifyObservers() {
        System.out.println("\n📢 Broadcasting to " + observers.size() + " observers...");
        for (Observer observer : observers) {
            // Call update() on each observer
            // Pass the current state data
            observer.update(temperature, humidity, pressure);
        }
        System.out.println("✓ All observers notified!\n");
    }
    
    /**
     * When weather measurements change, notify all observers
     * This is called when new data comes in from weather sensors
     */
    public void setMeasurements(float temperature, float humidity, float pressure) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🌡️  NEW WEATHER DATA RECEIVED!");
        System.out.println("=".repeat(60));
        
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        
        // Automatically notify all observers
        // This is the "push" mechanism
        notifyObservers();
    }
    
    // Getters (in case observers want to "pull" data themselves)
    public float getTemperature() {
        return temperature;
    }
    
    public float getHumidity() {
        return humidity;
    }
    
    public float getPressure() {
        return pressure;
    }
}