package pu.com.ay.DesignPatterns.CreationalPatterns.Builder;

/**
 * BUILDER PATTERN SUMMARY:
 * 
 * The Builder pattern is a creational design pattern that separates the construction
 * of complex objects from their representation. It allows the same construction process
 * to create different representations.
 * 
 * KEY COMPONENTS:
 * - Product: The complex object being built (Car, Manual)
 * - Builder: Interface or abstract class defining steps to build the product
 * - ConcreteBuilder: Implements Builder to construct and assemble parts of the product
 * - Director: Controls the building process using the builder (optional)
 * 
 * BENEFITS:
 * 1. Allows step-by-step construction of complex objects
 * 2. Same construction process can create different representations
 * 3. Isolates complex construction code from business logic
 * 4. More control over the construction process
 */

// Product: Car - This is one of the complex objects that will be built using the Builder pattern
class Car {
    // Private fields representing various parts/components of the Car
    private String model;
    private String engine;
    private int seats;
    private String navigation;
    private String tripComputer;
    
    // Private constructor that only accepts a builder - forces use of the builder pattern
    private Car(CarBuilder builder) {
        // Copy all properties from builder to the car instance
        this.model = builder.model;
        this.engine = builder.engine;
        this.seats = builder.seats;
        this.navigation = builder.navigation;
        this.tripComputer = builder.tripComputer;
    }
    
    // To string method for displaying the car details
    @Override
    public String toString() {
        return "Car{" +
               "model='" + model + '\'' +
               ", engine='" + engine + '\'' +
               ", seats=" + seats +
               ", navigationSystem='" + navigation + '\'' +
               ", tripComputer='" + tripComputer + '\'' +
               '}';
    }
    
    /**
     * Concrete Builder for Car - Nested static class
     * This builder provides a fluent interface (method chaining) for constructing a Car
     * with various configurations
     */
    public static class CarBuilder {
        // Fields that match the Car's properties
        private String model;
        private String engine;
        private int seats;
        private String navigation;
        private String tripComputer;
        
        // Empty constructor for the builder
        public CarBuilder() {
            // Empty constructor
        }
        
        // Builder methods for setting each property, returning this for method chaining
        public CarBuilder model(String model) {
            this.model = model;
            return this;
        }
        
        public CarBuilder engine(String engine) {
            this.engine = engine;
            return this;
        }
        
        public CarBuilder seats(int seats) {
            this.seats = seats;
            return this;
        }
        
        public CarBuilder navigation(String navigation) {
            this.navigation = navigation;
            return this;
        }
        
        public CarBuilder tripComputer(String tripComputer) {
            this.tripComputer = tripComputer;
            return this;
        }
        
        // Final build method that returns a new Car instance
        public Car build() {
            return new Car(this);
        }
    }
}

// Product: Manual - Another complex object that will be built using the Builder pattern
class Manual {
    // Private fields for the Manual's components
    private String model;
    private String engineInstructions;
    private String seatsInstructions;
    private String navigationInstructions;
    private String tripComputerInstructions;
    
    // Private constructor that only accepts a builder
    private Manual(ManualBuilder builder) {
        // Copy all properties from builder to this manual instance
        this.model = builder.model;
        this.engineInstructions = builder.engineInstructions;
        this.seatsInstructions = builder.seatsInstructions;
        this.navigationInstructions = builder.navigationInstructions;
        this.tripComputerInstructions = builder.tripComputerInstructions;
    }
    
    // To string method for displaying the manual details
    @Override
    public String toString() {
        return "Manual{" +
               "model='" + model + '\'' +
               ", engineInstructions='" + engineInstructions + '\'' +
               ", seatsInstructions='" + seatsInstructions + '\'' +
               ", navigationInstructions='" + navigationInstructions + '\'' +
               ", tripComputerInstructions='" + tripComputerInstructions + '\'' +
               '}';
    }
    
    /**
     * Concrete Builder for Manual
     * Similar structure to CarBuilder but builds Manuals instead of Cars
     */
    public static class ManualBuilder {
        // Fields that match the Manual's properties
        private String model;
        private String engineInstructions;
        private String seatsInstructions;
        private String navigationInstructions;
        private String tripComputerInstructions;
        
        // Empty constructor for the builder
        public ManualBuilder() {
            // Empty constructor
        }
        
        // Builder methods for setting each property, returning this for method chaining
        public ManualBuilder model(String model) {
            this.model = model;
            return this;
        }
        
        public ManualBuilder engineInstructions(String instructions) {
            this.engineInstructions = instructions;
            return this;
        }
        
        public ManualBuilder seatsInstructions(String instructions) {
            this.seatsInstructions = instructions;
            return this;
        }
        
        public ManualBuilder navigationInstructions(String instructions) {
            this.navigationInstructions = instructions;
            return this;
        }
        
        public ManualBuilder tripComputerInstructions(String instructions) {
            this.tripComputerInstructions = instructions;
            return this;
        }
        
        // Final build method that returns a new Manual instance
        public Manual build() {
            return new Manual(this);
        }
    }
}

/**
 * Director class - Optional but useful component of the Builder pattern
 * The Director defines the order in which to execute the building steps.
 * It works with a builder object through common interface.
 * The Director is not strictly necessary - client can directly control builders.
 */
class Director {
    // Method to configure builder to build a sports car
    public void constructSportsCar(Car.CarBuilder builder) {
        builder.model("Sports Car")
               .engine("3.0L V6")
               .seats(2)
               .navigation("Sport Navigation System")
               .tripComputer("Advanced Trip Computer");
    }
    
    // Method to configure builder to build an SUV
    public void constructSUV(Car.CarBuilder builder) {
        builder.model("SUV")
               .engine("2.5L I4")
               .seats(7)
               .navigation("Family Navigation System")
               .tripComputer("Standard Trip Computer");
    }
    
    // Method to configure builder to build a sports car manual
    public void constructSportsCarManual(Manual.ManualBuilder builder) {
        builder.model("Sports Car")
               .engineInstructions("Sports Car Engine Manual: Handle with care, high performance engine")
               .seatsInstructions("Adjust sport seats for optimal racing position")
               .navigationInstructions("Sport Navigation: Includes track locations")
               .tripComputerInstructions("Advanced features including lap timing");
    }
    
    // Method to configure builder to build an SUV manual
    public void constructSUVManual(Manual.ManualBuilder builder) {
        builder.model("SUV")
               .engineInstructions("SUV Engine Manual: Efficient family engine")
               .seatsInstructions("Configure 7 seats for maximum comfort")
               .navigationInstructions("Family Navigation: Includes school locations")
               .tripComputerInstructions("Features include fuel efficiency tracking");
    }
}

/**
 * Demo class - Shows how the Builder pattern is used
 * This demonstrates different ways to use builders:
 * 1. With a Director (standardized builds)
 * 2. Without a Director (custom builds)
 */
public class BuilderPatternDemo {
    public static void main(String[] args) {
        // Create a Director to manage the building process
        Director director = new Director();
        
        // EXAMPLE 1: Using Director to build a sports car
        Car.CarBuilder carBuilder = new Car.CarBuilder();
        director.constructSportsCar(carBuilder); // Director configures the builder
        Car sportsCar = carBuilder.build();      // Builder creates the final object
        System.out.println("Built sports car:\n" + sportsCar);
        
        // EXAMPLE 2: Using Director to build a sports car manual
        Manual.ManualBuilder manualBuilder = new Manual.ManualBuilder();
        director.constructSportsCarManual(manualBuilder);
        Manual sportsCarManual = manualBuilder.build();
        System.out.println("\nBuilt sports car manual:\n" + sportsCarManual);
        
        // EXAMPLE 3: Using Director to build an SUV
        carBuilder = new Car.CarBuilder(); // Create a new builder
        director.constructSUV(carBuilder);
        Car suv = carBuilder.build();
        System.out.println("\nBuilt SUV:\n" + suv);
        
        // EXAMPLE 4: Using Director to build an SUV manual
        manualBuilder = new Manual.ManualBuilder();
        director.constructSUVManual(manualBuilder);
        Manual suvManual = manualBuilder.build();
        System.out.println("\nBuilt SUV manual:\n" + suvManual);
        
        // EXAMPLE 5: Building without Director - direct use of builder for custom configurations
        Car customCar = new Car.CarBuilder()
            .model("Custom Car")         // Step 1: Set model
            .engine("Electric Motor")    // Step 2: Set engine
            .seats(4)                    // Step 3: Set seats
            .navigation("Custom Navigation") // Step 4: Set navigation
            .tripComputer("Custom Trip Computer") // Step 5: Set trip computer
            .build();                    // Final step: build the car
        System.out.println("\nBuilt custom car:\n" + customCar);
    }
}