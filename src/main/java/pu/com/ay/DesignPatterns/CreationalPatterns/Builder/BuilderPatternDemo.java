package pu.com.ay.DesignPatterns.CreationalPatterns.Builder;

// Product: Car
// Product: Car
class Car {
    private String model;
    private String engine;
    private int seats;
    private String navigation;
    private String tripComputer;
    
    private Car(CarBuilder builder) {
        this.model = builder.model;
        this.engine = builder.engine;
        this.seats = builder.seats;
        this.navigation = builder.navigation;
        this.tripComputer = builder.tripComputer;
    }
    
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
    
    // Builder class
    public static class CarBuilder {
        private String model;
        private String engine;
        private int seats;
        private String navigation;
        private String tripComputer;
        
        public CarBuilder() {
            // Empty constructor
        }
        
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
        
        public Car build() {
            return new Car(this);
        }
    }
}

// Product: Manual
class Manual {
    private String model;
    private String engineInstructions;
    private String seatsInstructions;
    private String navigationInstructions;
    private String tripComputerInstructions;
    
    private Manual(ManualBuilder builder) {
        this.model = builder.model;
        this.engineInstructions = builder.engineInstructions;
        this.seatsInstructions = builder.seatsInstructions;
        this.navigationInstructions = builder.navigationInstructions;
        this.tripComputerInstructions = builder.tripComputerInstructions;
    }
    
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
    
    // Builder class
    public static class ManualBuilder {
        private String model;
        private String engineInstructions;
        private String seatsInstructions;
        private String navigationInstructions;
        private String tripComputerInstructions;
        
        public ManualBuilder() {
            // Empty constructor
        }
        
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
        
        public Manual build() {
            return new Manual(this);
        }
    }
}

// Director
class Director {
    public void constructSportsCar(Car.CarBuilder builder) {
        builder.model("Sports Car")
               .engine("3.0L V6")
               .seats(2)
               .navigation("Sport Navigation System")
               .tripComputer("Advanced Trip Computer");
    }
    
    public void constructSUV(Car.CarBuilder builder) {
        builder.model("SUV")
               .engine("2.5L I4")
               .seats(7)
               .navigation("Family Navigation System")
               .tripComputer("Standard Trip Computer");
    }
    
    public void constructSportsCarManual(Manual.ManualBuilder builder) {
        builder.model("Sports Car")
               .engineInstructions("Sports Car Engine Manual: Handle with care, high performance engine")
               .seatsInstructions("Adjust sport seats for optimal racing position")
               .navigationInstructions("Sport Navigation: Includes track locations")
               .tripComputerInstructions("Advanced features including lap timing");
    }
    
    public void constructSUVManual(Manual.ManualBuilder builder) {
        builder.model("SUV")
               .engineInstructions("SUV Engine Manual: Efficient family engine")
               .seatsInstructions("Configure 7 seats for maximum comfort")
               .navigationInstructions("Family Navigation: Includes school locations")
               .tripComputerInstructions("Features include fuel efficiency tracking");
    }
}

// Demo class
public class BuilderPatternDemo {
    public static void main(String[] args) {
        Director director = new Director();
        
        // Building a sports car
        Car.CarBuilder carBuilder = new Car.CarBuilder();
        director.constructSportsCar(carBuilder);
        Car sportsCar = carBuilder.build();
        System.out.println("Built sports car:\n" + sportsCar);
        
        // Building sports car manual
        Manual.ManualBuilder manualBuilder = new Manual.ManualBuilder();
        director.constructSportsCarManual(manualBuilder);
        Manual sportsCarManual = manualBuilder.build();
        System.out.println("\nBuilt sports car manual:\n" + sportsCarManual);
        
        // Building an SUV
        carBuilder = new Car.CarBuilder();
        director.constructSUV(carBuilder);
        Car suv = carBuilder.build();
        System.out.println("\nBuilt SUV:\n" + suv);
        
        // Building SUV manual
        manualBuilder = new Manual.ManualBuilder();
        director.constructSUVManual(manualBuilder);
        Manual suvManual = manualBuilder.build();
        System.out.println("\nBuilt SUV manual:\n" + suvManual);
        
        // Building a custom car without director
        Car customCar = new Car.CarBuilder()
            .model("Custom Car")
            .engine("Electric Motor")
            .seats(4)
            .navigation("Custom Navigation")
            .tripComputer("Custom Trip Computer")
            .build();
        System.out.println("\nBuilt custom car:\n" + customCar);
    }
}