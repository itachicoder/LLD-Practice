package pu.com.ay.SolidPrinciples.LiskovSubstitutionPrinciple;

// Base Vehicle class with common properties
abstract class Vehicle {
    private String brand;
    private String model;
    private int year;
    
    public Vehicle(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }
    
    // Common methods for all vehicles
    public abstract void move();
    public abstract void stop();
    
    // Getters
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
}

// Abstract class for motorized vehicles
abstract class Motorized extends Vehicle {
    private double engineSize;
    private String fuelType;
    private int currentSpeed;
    
    public Motorized(String brand, String model, int year, 
                     double engineSize, String fuelType) {
        super(brand, model, year);
        this.engineSize = engineSize;
        this.fuelType = fuelType;
        this.currentSpeed = 0;
    }
    
    // Methods specific to motorized vehicles
    public abstract void startEngine();
    public abstract void stopEngine();
    public abstract void accelerate(int speed);
    
    // Getters and setters
    public double getEngineSize() { return engineSize; }
    public String getFuelType() { return fuelType; }
    public int getCurrentSpeed() { return currentSpeed; }
    protected void setCurrentSpeed(int speed) { this.currentSpeed = speed; }
}

// Abstract class for manual vehicles
abstract class Manual extends Vehicle {
    private int numberOfGears;
    private int currentSpeed;
    
    public Manual(String brand, String model, int year, int numberOfGears) {
        super(brand, model, year);
        this.numberOfGears = numberOfGears;
        this.currentSpeed = 0;
    }
    
    // Methods specific to manual vehicles
    public abstract void pedal();
    public abstract void brake();
    
    // Getters and setters
    public int getNumberOfGears() { return numberOfGears; }
    public int getCurrentSpeed() { return currentSpeed; }
    protected void setCurrentSpeed(int speed) { this.currentSpeed = speed; }
}

// Concrete Car class
class Car extends Motorized {
    private boolean isEngineRunning;
    
    public Car(String brand, String model, int year, 
               double engineSize, String fuelType) {
        super(brand, model, year, engineSize, fuelType);
        this.isEngineRunning = false;
    }
    
    @Override
    public void startEngine() {
        if (!isEngineRunning) {
            System.out.println("Starting " + getBrand() + " " + getModel() + "'s engine...");
            isEngineRunning = true;
        }
    }
    
    @Override
    public void stopEngine() {
        if (isEngineRunning) {
            System.out.println("Stopping " + getBrand() + " " + getModel() + "'s engine...");
            isEngineRunning = false;
        }
    }
    
    @Override
    public void accelerate(int speed) {
        if (isEngineRunning) {
            setCurrentSpeed(speed);
            System.out.println("Accelerating to " + speed + " km/h");
        } else {
            System.out.println("Cannot accelerate. Engine is not running.");
        }
    }
    
    @Override
    public void move() {
        if (isEngineRunning) {
            System.out.println("Car is moving...");
        } else {
            System.out.println("Start the engine first!");
        }
    }
    
    @Override
    public void stop() {
        setCurrentSpeed(0);
        System.out.println("Car has stopped.");
    }
}

// Concrete Bicycle class
class Bicycle extends Manual {
    private boolean isMoving;
    
    public Bicycle(String brand, String model, int year, int numberOfGears) {
        super(brand, model, year, numberOfGears);
        this.isMoving = false;
    }
    
    @Override
    public void pedal() {
        isMoving = true;
        setCurrentSpeed(getCurrentSpeed() + 5);
        System.out.println("Pedaling... Current speed: " + getCurrentSpeed() + " km/h");
    }
    
    @Override
    public void brake() {
        if (getCurrentSpeed() > 0) {
            setCurrentSpeed(Math.max(0, getCurrentSpeed() - 5));
            System.out.println("Applying brakes... Current speed: " + getCurrentSpeed() + " km/h");
        }
        if (getCurrentSpeed() == 0) {
            isMoving = false;
        }
    }
    
    @Override
    public void move() {
        if (!isMoving) {
            System.out.println("Start pedaling to move!");
        } else {
            System.out.println("Bicycle is moving...");
        }
    }
    
    @Override
    public void stop() {
        while (getCurrentSpeed() > 0) {
            brake();
        }
        System.out.println("Bicycle has stopped.");
    }
}

// Demo class to test LSP
class VehicleDemo {
    public static void main(String[] args) {
        // Testing Car
        System.out.println("=== Testing Car ===");
        Motorized car = new Car("Toyota", "Camry", 2024, 2.5, "Gasoline");
        car.startEngine();
        car.move();
        car.accelerate(60);
        car.stop();
        car.stopEngine();
        
        System.out.println("\n=== Testing Bicycle ===");
        Manual bicycle = new Bicycle("Trek", "Mountain Bike", 2024, 21);
        bicycle.move();
        bicycle.pedal();  // Start moving
        bicycle.pedal();  // Increase speed
        bicycle.brake();  // Decrease speed
        bicycle.stop();
        
        // Demonstrating LSP
        System.out.println("\n=== Testing LSP with Vehicle array ===");
        Vehicle[] vehicles = new Vehicle[] {
            new Car("Honda", "Civic", 2024, 1.8, "Gasoline"),
            new Bicycle("Giant", "City Bike", 2024, 18)
        };
        
        for (Vehicle vehicle : vehicles) {
            System.out.println("\nTesting " + vehicle.getBrand() + " " + vehicle.getModel());
            vehicle.move();
            vehicle.stop();
        }
    }
}
