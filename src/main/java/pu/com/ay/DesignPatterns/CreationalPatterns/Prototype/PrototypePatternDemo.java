package pu.com.ay.DesignPatterns.CreationalPatterns.Prototype;

import java.util.HashMap;
import java.util.Map;

abstract class Shape implements Cloneable {
    private String id;
    private String type;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }

    @Override
    public Shape clone() {
        try {
            // Call Object's clone method
            Shape clone = (Shape) super.clone();
            // You can copy additional fields if necessary (e.g., deep copy)
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported!", e); // Should never happen since Cloneable is implemented
        }
    }

    public abstract void draw();
}

class Circle extends Shape {
    private int radius;

    public Circle() {
        setType("Circle");
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public Circle clone() {
        // Use the parent clone method
        Circle clone = (Circle) super.clone();
        // Clone specific fields if necessary
        clone.setRadius(this.radius);
        return clone;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a Circle with radius: " + radius);
    }
}

class Rectangle extends Shape {
    private int width;
    private int height;

    public Rectangle() {
        setType("Rectangle");
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public Rectangle clone() {
        Rectangle clone = (Rectangle) super.clone();
        clone.setWidth(this.width);
        clone.setHeight(this.height);
        return clone;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a Rectangle with width: " + width + ", height: " + height);
    }
}

class ShapeRegistry {
    private Map<String, Shape> prototypes = new HashMap<>();

    public void addPrototype(String key, Shape prototype) {
        prototypes.put(key, prototype);
    }

    public Shape getPrototype(String key) {
        Shape prototype = prototypes.get(key);
        if (prototype == null) {
            throw new IllegalArgumentException("No prototype found for key: " + key);
        }
        return prototype.clone();
    }
}

public class PrototypePatternDemo {
    public static void main(String[] args) {
        // Create the prototype registry
        ShapeRegistry registry = new ShapeRegistry();

        // Add prototypes
        Circle circlePrototype = new Circle();
        circlePrototype.setId("1");
        circlePrototype.setRadius(10);
        registry.addPrototype("circle", circlePrototype);

        Rectangle rectanglePrototype = new Rectangle();
        rectanglePrototype.setId("2");
        rectanglePrototype.setWidth(5);
        rectanglePrototype.setHeight(8);
        registry.addPrototype("rectangle", rectanglePrototype);

        // Retrieve and clone prototypes
        Shape clonedCircle = registry.getPrototype("circle");
        clonedCircle.draw(); // Output: Drawing a Circle with radius: 10

        Shape clonedRectangle = registry.getPrototype("rectangle");
        clonedRectangle.draw(); // Output: Drawing a Rectangle with width: 5, height: 8
    }
}


