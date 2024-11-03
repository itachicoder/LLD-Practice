package pu.com.ay.SolidPrinciples.OpenClosedPrinciple;

// Abstract base class for all shapes
abstract class Shape {
    abstract double volume();
}

// Concrete class for cuboid boxes
class Cuboid extends Shape {
    private double length;
    private double width;
    private double height;
    
    public Cuboid(double length, double width, double height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }
    
    @Override
    double volume() {
        return length * width * height;
    }
}

// Concrete class for cone boxes
class Cone extends Shape {
    private double radius;
    private double height;
    private final double PI = Math.PI;
    
    public Cone(double radius, double height) {
        this.radius = radius;
        this.height = height;
    }
    
    @Override
    double volume() {
        return (1.0/3.0) * PI * radius * radius * height;
    }
}

// Concrete class for cylinder boxes
class Cylinder extends Shape {
    private double radius;
    private double height;
    private final double PI = Math.PI;
    
    public Cylinder(double radius, double height) {
        this.radius = radius;
        this.height = height;
    }
    
    @Override
    double volume() {
        return PI * radius * radius * height;
    }
}

// Volume calculator class that works with any shape
class VolumeCalculator {
    public double sumVolume(Shape[] shapes) {
        double totalVolume = 0;
        for (Shape shape : shapes) {
            totalVolume += shape.volume();
        }
        return totalVolume;
    }
}

// Demo class to show usage
class BoxBusinessDemo {
    public static void main(String[] args) {
        // Create different types of boxes
        Shape[] boxes = new Shape[] {
            new Cuboid(10, 20, 30),
            new Cone(10, 20),
            new Cylinder(10, 20),
            new Cuboid(5, 10, 15)
        };
        
        // Calculate total volume
        VolumeCalculator calculator = new VolumeCalculator();
        double totalVolume = calculator.sumVolume(boxes);
        
        // Print results
        System.out.println("Volumes of individual boxes:");
        for (int i = 0; i < boxes.length; i++) {
            System.out.printf("Box %d: %.2f cubic units%n", i + 1, boxes[i].volume());
        }
        System.out.printf("%nTotal volume: %.2f cubic units%n", totalVolume);
    }
}

// Example of how easy it is to extend with a new shape without modifying existing code
class Sphere extends Shape {
    private double radius;
    private final double PI = Math.PI;
    
    public Sphere(double radius) {
        this.radius = radius;
    }
    
    @Override
    double volume() {
        return (4.0/3.0) * PI * radius * radius * radius;
    }
}