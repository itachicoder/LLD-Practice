package pu.com.ay.DesignPatterns.BehavioralPatterns.Strategy;

/*
 * Strategy Pattern Implementation for Navigation System
 * 
 * The Strategy pattern is useful because it:
 * 1. Eliminates large conditional statements (if-else chains)
 * 2. Makes it easy to add new routing algorithms without modifying existing code
 * 3. Allows runtime switching between different algorithms
 * 4. Promotes code reusability and maintainability
 * 5. Follows the Open-Closed Principle (open for extension, closed for modification)
 */

import java.util.Arrays;
import java.util.List;

/**
 * Point class to represent geographic locations with latitude and longitude coordinates.
 * This is a simple data class that encapsulates location information.
 */
class Point {
    private double latitude;
    private double longitude;
    
    public Point(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }
    
    @Override
    public String toString() {
        return "(" + latitude + ", " + longitude + ")";
    }
}

/**
 * Strategy Interface - defines the contract for all routing algorithms
 * 
 * Why this is useful:
 * - Provides a common interface for all routing strategies
 * - Enables polymorphism - client can work with any strategy through this interface
 * - Makes it easy to add new routing algorithms by simply implementing this interface
 */
interface RouteStrategy {
    /**
     * Builds a route between start and end points
     * @param start Starting point of the route
     * @param end Ending point of the route
     * @return List of points representing the route path
     */
    List<Point> buildRoute(Point start, Point end);
}

/**
 * Concrete Strategy for driving routes
 * 
 * Benefits of separating this logic:
 * - Driving routes consider highways, traffic patterns, and road conditions
 * - Can be modified independently without affecting other routing strategies
 * - Easy to test in isolation
 */
class DrivingStrategy implements RouteStrategy {
    @Override
    public List<Point> buildRoute(Point start, Point end) {
        System.out.println("Building driving route...");
        
        // In a real implementation, this would:
        // - Query road networks and traffic data
        // - Calculate fastest/shortest route considering traffic
        // - Avoid pedestrian-only areas
        // - Prioritize highways for longer distances
        
        return Arrays.asList(
            start,
            new Point(40.7128, -74.0060), // Highway entrance waypoint
            new Point(40.7580, -73.9855), // Major intersection waypoint
            end
        );
    }
}

/**
 * Concrete Strategy for walking routes
 * 
 * Why separate from driving:
 * - Walking routes can use pedestrian paths, stairs, and shortcuts
 * - Different optimization criteria (shorter distance vs. scenic routes)
 * - Can avoid busy roads and prioritize sidewalks
 */
class WalkingStrategy implements RouteStrategy {
    @Override
    public List<Point> buildRoute(Point start, Point end) {
        System.out.println("Building walking route...");
        
        // In a real implementation, this would:
        // - Use pedestrian pathways and sidewalks
        // - Consider stairs, elevators, and accessibility
        // - Potentially choose scenic or safer routes
        // - Account for walking speed and terrain difficulty
        
        return Arrays.asList(
            start,
            new Point(40.7135, -74.0070), // Pedestrian pathway waypoint
            end
        );
    }
}

/**
 * Concrete Strategy for public transport routes
 * 
 * Unique considerations:
 * - Must connect different transport modes (bus, subway, train)
 * - Considers schedules and waiting times
 * - Optimizes for cost and travel time including transfers
 */
class PublicTransportStrategy implements RouteStrategy {
    @Override
    public List<Point> buildRoute(Point start, Point end) {
        System.out.println("Building public transport route...");
        
        // In a real implementation, this would:
        // - Query public transport APIs for schedules
        // - Calculate optimal transfer points
        // - Consider real-time delays and service disruptions
        // - Balance travel time vs. number of transfers
        
        return Arrays.asList(
            start,
            new Point(40.7500, -73.9967), // Subway station transfer point
            new Point(40.7545, -73.9873), // Bus stop connection
            end
        );
    }
}

/**
 * Concrete Strategy for bicycle routes
 * 
 * Specialized requirements:
 * - Prioritizes bike lanes and bike-friendly roads
 * - Avoids steep hills where possible
 * - Considers bike parking availability at destination
 */
class BicycleStrategy implements RouteStrategy {
    @Override
    public List<Point> buildRoute(Point start, Point end) {
        System.out.println("Building bicycle route...");
        
        // In a real implementation, this would:
        // - Prioritize dedicated bike lanes and paths
        // - Avoid steep inclines and busy intersections
        // - Consider weather conditions and road surface quality
        // - Include bike parking locations
        
        return Arrays.asList(
            start,
            new Point(40.7200, -74.0000), // Bike path waypoint
            new Point(40.7300, -73.9900), // Protected bike lane waypoint
            end
        );
    }
}

/**
 * Context Class - The Navigator
 * 
 * This class demonstrates the key benefits of Strategy pattern:
 * 1. Single Responsibility: Only handles route building and rendering
 * 2. Flexibility: Can switch strategies at runtime
 * 3. Extensibility: New strategies can be added without modifying this class
 * 4. Testability: Easy to test with mock strategies
 */
class Navigator {
    // Strategy object - can be changed at runtime
    private RouteStrategy routeStrategy;
    
    /**
     * Constructor injection of strategy
     * Benefits: Ensures Navigator always has a valid strategy
     */
    public Navigator(RouteStrategy strategy) {
        this.routeStrategy = strategy;
    }
    
    /**
     * Allows changing the strategy at runtime
     * This is where the Strategy pattern really shines - dynamic behavior switching
     */
    public void setStrategy(RouteStrategy strategy) {
        this.routeStrategy = strategy;
    }
    
    /**
     * Delegates route building to the current strategy
     * 
     * Key benefit: Navigator doesn't need to know HOW routes are built,
     * only that they CAN be built. This separation of concerns makes
     * the code more maintainable and flexible.
     */
    public List<Point> buildRoute(Point start, Point end) {
        // Delegation to strategy - this is the core of Strategy pattern
        return routeStrategy.buildRoute(start, end);
    }
    
    /**
     * Utility method to display the route
     * This functionality is separate from strategy since rendering
     * is the same regardless of how the route was calculated
     */
    public void renderRoute(List<Point> route) {
        System.out.println("Rendering route:");
        for (int i = 0; i < route.size() - 1; i++) {
            System.out.println(route.get(i) + " → " + route.get(i + 1));
        }
    }
}

/**
 * Demo class showing the Strategy pattern in action
 * 
 * This demonstrates:
 * - How easy it is to switch between different algorithms
 * - How the client code remains clean and simple
 * - How new strategies can be added without breaking existing code
 */
public class NavigationDemo {
    public static void main(String[] args) {
        /*
         * Strategy Pattern Benefits Demonstrated Below:
         * 
         * 1. No complex if-else statements needed
         * 2. Easy to switch algorithms at runtime
         * 3. Each algorithm is encapsulated and can be tested independently
         * 4. Adding new routing methods requires no changes to existing code
         * 5. Client code is clean and focused on business logic
         */
        
        // Create navigator with initial strategy (Dependency Injection)
        Navigator navigator = new Navigator(new DrivingStrategy());
        
        // Create start and end points for our journey
        Point start = new Point(40.7128, -74.0060); // New York coordinates
        Point end = new Point(40.7614, -73.9776);   // Another NYC location
        
        // Demonstrate switching between different routing strategies
        // Notice how the Navigator's interface remains the same,
        // but the behavior changes based on the strategy
        
        System.out.println("\n=== DRIVING ROUTE ===");
        List<Point> drivingRoute = navigator.buildRoute(start, end);
        navigator.renderRoute(drivingRoute);
        
        // Runtime strategy switching - no need to create new Navigator
        System.out.println("\n=== WALKING ROUTE ===");
        navigator.setStrategy(new WalkingStrategy());
        List<Point> walkingRoute = navigator.buildRoute(start, end);
        navigator.renderRoute(walkingRoute);
        
        System.out.println("\n=== PUBLIC TRANSPORT ROUTE ===");
        navigator.setStrategy(new PublicTransportStrategy());
        List<Point> publicTransportRoute = navigator.buildRoute(start, end);
        navigator.renderRoute(publicTransportRoute);
        
        System.out.println("\n=== BICYCLE ROUTE ===");
        navigator.setStrategy(new BicycleStrategy());
        List<Point> bicycleRoute = navigator.buildRoute(start, end);
        navigator.renderRoute(bicycleRoute);
        
        /*
         * What would happen WITHOUT Strategy pattern:
         * 
         * The Navigator class would need a large switch/if-else statement:
         * 
         * public List<Point> buildRoute(Point start, Point end, String type) {
         *     if (type.equals("driving")) {
         *         // driving logic here
         *     } else if (type.equals("walking")) {
         *         // walking logic here
         *     } else if (type.equals("public_transport")) {
         *         // public transport logic here
         *     } // ... and so on
         * }
         * 
         * Problems with this approach:
         * 1. Violates Single Responsibility Principle
         * 2. Hard to add new routing types (must modify Navigator)
         * 3. Difficult to test individual routing algorithms
         * 4. Code becomes messy and hard to maintain
         * 5. Violates Open-Closed Principle
         */
    }
}

/*
 * SUMMARY: Why Strategy Pattern is Useful
 * 
 * 1. FLEXIBILITY: Easy to switch algorithms at runtime
 * 2. MAINTAINABILITY: Each algorithm is isolated and can be modified independently
 * 3. EXTENSIBILITY: New algorithms can be added without changing existing code
 * 4. TESTABILITY: Each strategy can be unit tested in isolation
 * 5. CLEAN CODE: Eliminates complex conditional statements
 * 6. SOLID PRINCIPLES: Follows Single Responsibility and Open-Closed principles
 * 7. REUSABILITY: Strategies can be reused in different contexts
 * 8. POLYMORPHISM: Client works with strategies through common interface
 * 
 * Real-world applications:
 * - Payment processing (Credit Card, PayPal, Bank Transfer)
 * - Sorting algorithms (QuickSort, MergeSort, BubbleSort)
 * - Compression algorithms (ZIP, RAR, 7Z)
 * - Authentication methods (OAuth, LDAP, Database)
 * - Pricing strategies (Regular, Premium, Discount)
 */