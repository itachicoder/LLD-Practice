package pu.com.ay.DesignPatterns.StructuralPatterns.Composite;

import java.util.ArrayList;
import java.util.List;

// Component interface
interface Shape {
    void move(int x, int y);
    void draw();
    Rectangle getBounds();
    String getName();
}

// Basic Rectangle class for bounds calculation
class Rectangle {
    int x, y, width, height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}

// Leaf class - Circle
class Circle implements Shape {
    private int x, y, radius;
    private final String name;

    public Circle(String name, int x, int y, int radius) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public void draw() {
        System.out.println(name + ": Drawing Circle at (" + x + "," + y + ") with radius " + radius);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    @Override
    public String getName() {
        return name;
    }
}

// Leaf class - RectangleShape
class RectangleShape implements Shape {
    private int x, y, width, height;
    private final String name;

    public RectangleShape(String name, int x, int y, int width, int height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public void draw() {
        System.out.println(name + ": Drawing Rectangle at (" + x + "," + y + 
                          ") with width " + width + " and height " + height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public String getName() {
        return name;
    }
}

// Composite class
class CompoundShape implements Shape {
    private List<Shape> shapes = new ArrayList<>();
    private final String name;

    public CompoundShape(String name) {
        this.name = name;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
        System.out.println("Added " + shape.getName() + " to " + this.name);
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
        System.out.println("Removed " + shape.getName() + " from " + this.name);
    }

    @Override
    public void move(int x, int y) {
        System.out.println("Moving " + name + " by (" + x + "," + y + ")");
        shapes.forEach(shape -> shape.move(x, y));
    }

    @Override
    public void draw() {
        System.out.println(name + ": Drawing compound shape:");
        shapes.forEach(Shape::draw);
    }

    @Override
    public Rectangle getBounds() {
        if (shapes.isEmpty()) {
            return new Rectangle(0, 0, 0, 0);
        }

        Rectangle bounds = shapes.get(0).getBounds();
        for (Shape shape : shapes.subList(1, shapes.size())) {
            Rectangle shapeBounds = shape.getBounds();
            int minX = Math.min(bounds.x, shapeBounds.x);
            int minY = Math.min(bounds.y, shapeBounds.y);
            int maxX = Math.max(bounds.x + bounds.width, shapeBounds.x + shapeBounds.width);
            int maxY = Math.max(bounds.y + bounds.height, shapeBounds.y + shapeBounds.height);
            
            bounds = new Rectangle(minX, minY, maxX - minX, maxY - minY);
        }
        return bounds;
    }

    @Override
    public String getName() {
        return name;
    }
}

// Demo class
public class ShapeEditorDemo {
    public static void main(String[] args) {
        // Create basic shapes
        Circle circle1 = new Circle("Small Circle", 10, 10, 5);
        Circle circle2 = new Circle("Large Circle", 20, 20, 10);
        RectangleShape rectangle = new RectangleShape("Rectangle", 30, 30, 15, 10);

        // Create a compound shape
        CompoundShape group1 = new CompoundShape("Group 1");
        group1.addShape(circle1);
        group1.addShape(rectangle);

        // Create another compound shape
        CompoundShape group2 = new CompoundShape("Main Group");
        group2.addShape(group1);  // Adding a group to another group
        group2.addShape(circle2);

        // Demonstrate the operations
        System.out.println("\n=== Initial Drawing ===");
        group2.draw();

        System.out.println("\n=== Moving the Main Group ===");
        group2.move(5, 5);

        System.out.println("\n=== Final Drawing ===");
        group2.draw();

        // Calculate and display bounds
        Rectangle bounds = group2.getBounds();
        System.out.println("\n=== Bounds of Main Group ===");
        System.out.println("Position: (" + bounds.x + "," + bounds.y + ")");
        System.out.println("Size: " + bounds.width + " x " + bounds.height);
    }
}