package pu.com.ay.DesignPatterns.BehavioralPatterns.Strategy;

// Point class to represent locations

import java.util.Arrays;
import java.util.List;

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

// The Strategy interface
interface RouteStrategy {
    List<Point> buildRoute(Point start, Point end);
}

// Concrete strategy for building a driving route
class DrivingStrategy implements RouteStrategy {
    @Override
    public List<Point> buildRoute(Point start, Point end) {
        System.out.println("Building driving route...");
        // Complex road routing algorithm would go here
        return Arrays.asList(
            start,
            new Point(40.7128, -74.0060), // Example waypoint
            new Point(40.7580, -73.9855), // Example waypoint
            end
        );
    }
}

// Concrete strategy for building a walking route
class WalkingStrategy implements RouteStrategy {
    @Override
    public List<Point> buildRoute(Point start, Point end) {
        System.out.println("Building walking route...");
        // Pedestrian path algorithm would go here
        return Arrays.asList(
            start,
            new Point(40.7135, -74.0070), // Different waypoint
            end
        );
    }
}

// Concrete strategy for public transport
class PublicTransportStrategy implements RouteStrategy {
    @Override
    public List<Point> buildRoute(Point start, Point end) {
        System.out.println("Building public transport route...");
        // Public transport algorithm would go here
        return Arrays.asList(
            start,
            new Point(40.7500, -73.9967), // Subway station
            new Point(40.7545, -73.9873), // Bus stop
            end
        );
    }
}

// Concrete strategy for bicycle routes
class BicycleStrategy implements RouteStrategy {
    @Override
    public List<Point> buildRoute(Point start, Point end) {
        System.out.println("Building bicycle route...");
        // Bicycle-friendly route algorithm would go here
        return Arrays.asList(
            start,
            new Point(40.7200, -74.0000), // Bike path waypoint
            new Point(40.7300, -73.9900), // Bike lane waypoint
            end
        );
    }
}

// The Context class
class Navigator {
    private RouteStrategy routeStrategy;
    
    public Navigator(RouteStrategy strategy) {
        this.routeStrategy = strategy;
    }
    
    public void setStrategy(RouteStrategy strategy) {
        this.routeStrategy = strategy;
    }
    
    public List<Point> buildRoute(Point start, Point end) {
        // Delegate the routing to the strategy object
         return routeStrategy.buildRoute(start, end);
    }
    
    // Method to render route on the map
    public void renderRoute(List<Point> route) {
        System.out.println("Rendering route:");
        for (int i = 0; i < route.size() - 1; i++) {
            System.out.println(route.get(i) + " → " + route.get(i + 1));
        }
    }
}

// Demo class to test the implementation
public class NavigationDemo {
    public static void main(String[] args) {
        // Create navigator with initial strategy
        Navigator navigator = new Navigator(new DrivingStrategy());
        
        // Create start and end points
        Point start = new Point(40.7128, -74.0060); // New York coordinates
        Point end = new Point(40.7614, -73.9776);   // Another location in New York
        
        // Build and render route using driving strategy
        System.out.println("\nDriving Route:");
        List<Point> drivingRoute = navigator.buildRoute(start, end);
        navigator.renderRoute(drivingRoute);
        
        // Switch to walking strategy
        System.out.println("\nSwitching to walking route:");
        navigator.setStrategy(new WalkingStrategy());
        List<Point> walkingRoute = navigator.buildRoute(start, end);
        navigator.renderRoute(walkingRoute);
        
        // Switch to public transport strategy
        System.out.println("\nSwitching to public transport route:");
        navigator.setStrategy(new PublicTransportStrategy());
        List<Point> publicTransportRoute = navigator.buildRoute(start, end);
        navigator.renderRoute(publicTransportRoute);
        
        // Switch to bicycle strategy
        System.out.println("\nSwitching to bicycle route:");
        navigator.setStrategy(new BicycleStrategy());
        List<Point> bicycleRoute = navigator.buildRoute(start, end);
        navigator.renderRoute(bicycleRoute);
    }
}