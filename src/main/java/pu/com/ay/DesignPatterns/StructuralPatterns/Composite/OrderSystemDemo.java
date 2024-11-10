package pu.com.ay.DesignPatterns.StructuralPatterns.Composite;

import java.util.ArrayList;
import java.util.List;

// Component interface
interface OrderComponent {
    double calculatePrice();
    void display();  // Added for better visualization
}

// Leaf class
class Product implements OrderComponent {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public double calculatePrice() {
        return price;
    }

    @Override
    public void display() {
        System.out.println("Product: " + name + " - $" + price);
    }
}

// Composite class
class Box implements OrderComponent {
    private List<OrderComponent> items = new ArrayList<>();
    private double packagingCost;
    private String name;
    
    public Box(String name, double packagingCost) {
        this.name = name;
        this.packagingCost = packagingCost;
    }

    public void addItem(OrderComponent item) {
        items.add(item);
    }

    public void removeItem(OrderComponent item) {
        items.remove(item);
    }

    @Override
    public double calculatePrice() {
        double totalPrice = items.stream()
            .mapToDouble(OrderComponent::calculatePrice)
            .sum();
        return totalPrice + packagingCost;
    }

    @Override
    public void display() {
        System.out.println("Box: " + name + " (Packaging cost: $" + packagingCost + ")");
        System.out.println("Contents:");
        items.forEach(item -> {
            System.out.print("  ");  // Indentation
            item.display();
        });
    }
}

// Demo class
public class OrderSystemDemo {
    public static void main(String[] args) {
        // Create individual products
        Product phone = new Product("Smartphone", 999.99);
        Product headphones = new Product("Wireless Headphones", 199.99);
        Product charger = new Product("Fast Charger", 49.99);
        Product case_ = new Product("Phone Case", 29.99);

        // Create a box for accessories
        Box accessoryBox = new Box("Accessories Box", 5.0);
        accessoryBox.addItem(headphones);
        accessoryBox.addItem(charger);
        accessoryBox.addItem(case_);

        // Create the main box containing everything
        Box mainBox = new Box("Main Package", 10.0);
        mainBox.addItem(phone);
        mainBox.addItem(accessoryBox);

        // Display the entire order structure
        System.out.println("=== Order Structure ===");
        mainBox.display();
        
        // Display the total price
        System.out.println("\n=== Order Summary ===");
        System.out.printf("Total Order Price: $%.2f%n", mainBox.calculatePrice());
        
        // Demonstrate adding/removing items
        System.out.println("\n=== Adding Extra Item ===");
        Product warranty = new Product("Extended Warranty", 89.99);
        mainBox.addItem(warranty);
        mainBox.display();
        System.out.printf("New Total Price: $%.2f%n", mainBox.calculatePrice());
    }
}