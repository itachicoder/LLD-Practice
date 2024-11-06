package pu.com.ay.SolidPrinciples.InterfaceSegregationPrinciple;


// Interface for 2D shapes
interface TwoDimensionalShape {
    double calculateArea();
    void draw();
}

// Interface for 3D shapes
interface ThreeDimensionalShape {
    double calculateVolume();
    double calculateSurfaceArea();
    void render();
}

// Square class implementing 2D shape interface
class Square implements TwoDimensionalShape {
    private double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a square with side: " + side);
    }
}

// Rectangle class implementing 2D shape interface
class Rectangle implements TwoDimensionalShape {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a rectangle with length: " + length + " and width: " + width);
    }
}

// Cube class implementing 3D shape interface
class Cube implements ThreeDimensionalShape {
    private double side;

    public Cube(double side) {
        this.side = side;
    }

    @Override
    public double calculateVolume() {
        return side * side * side;
    }

    @Override
    public double calculateSurfaceArea() {
        return 6 * side * side;
    }

    @Override
    public void render() {
        System.out.println("Rendering a cube with side: " + side);
    }
}

// Sphere class implementing 3D shape interface
class Sphere implements ThreeDimensionalShape {
    private double radius;
    private final double PI = 3.14159;

    public Sphere(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateVolume() {
        return (4.0/3.0) * PI * radius * radius * radius;
    }

    @Override
    public double calculateSurfaceArea() {
        return 4 * PI * radius * radius;
    }

    @Override
    public void render() {
        System.out.println("Rendering a sphere with radius: " + radius);
    }
}

// Main class to demonstrate the usage
public class ISPDemo {
    public static void main(String[] args) {
        // Creating 2D shapes
        TwoDimensionalShape square = new Square(5);
        TwoDimensionalShape rectangle = new Rectangle(4, 6);

        // Creating 3D shapes
        ThreeDimensionalShape cube = new Cube(3);
        ThreeDimensionalShape sphere = new Sphere(2);

        // Working with 2D shapes
        System.out.println("2D Shapes:");
        System.out.println("Square area: " + square.calculateArea());
        square.draw();
        System.out.println("Rectangle area: " + rectangle.calculateArea());
        rectangle.draw();

        // Working with 3D shapes
        System.out.println("\n3D Shapes:");
        System.out.println("Cube volume: " + cube.calculateVolume());
        System.out.println("Cube surface area: " + cube.calculateSurfaceArea());
        cube.render();
        System.out.println("Sphere volume: " + sphere.calculateVolume());
        System.out.println("Sphere surface area: " + sphere.calculateSurfaceArea());
        sphere.render();
    }
}