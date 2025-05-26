package pu.com.ay.DesignPatterns.BehavioralPatterns.Observer;

/**
 * OBSERVER PATTERN (PUBLISH-SUBSCRIBE MODEL)
 * 
 * The Observer pattern defines a one-to-many dependency between objects where
 * when one object (the Subject/Publisher) changes state, all its dependents
 * (Observers/Subscribers) are notified and updated automatically.
 * 
 * Core Components:
 * 1. Subject (Publisher) - Maintains a list of observers and notifies them of state changes
 * 2. Observer (Subscriber) - Interface that defines the update method for notifications
 * 3. ConcreteSubject - Implements the Subject interface
 * 4. ConcreteObserver - Implements the Observer interface
 * 
 * Key Relationships:
 * - HAS-A: The Subject HAS-A list of Observer objects
 * - IS-A: ConcreteObservers IS-A Observer (implementation of interface)
 * 
 * This implementation uses a Pub-Sub variation where:
 * - EventManager acts as an event dispatcher that decouples publishers from subscribers
 * - Publishers (Editor) don't directly know about subscribers
 * - Subscribers register for specific event types
 */

// The EventManager handles subscription mechanics and event distribution
// This is a mediator between publishers and subscribers
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EventManager {
    // HAS-A relationship: EventManager HAS-A map of event types to listeners
    private Map<String, List<EventListener>> listeners = new HashMap<>();
    
    public EventManager(String... operations) {
        // Initialize the event types that can be subscribed to
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }
    
    // Adds a subscriber for a specific event type
    public void subscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        users.add(listener);
    }
    
    // Removes a subscriber from a specific event type
    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        users.remove(listener);
    }
    
    // Notifies all subscribers of a specific event type
    // This is the core of the Observer pattern - broadcasting updates
    public void notify(String eventType, String data) {
        List<EventListener> users = listeners.get(eventType);
        for (EventListener listener : users) {
            listener.update(eventType, data);
        }
    }
}

// The EventListener interface that all subscribers must implement
// This is the Observer interface in the classic Observer pattern
interface EventListener {
    // Method called when an event occurs that the listener has subscribed to
    void update(String eventType, String data);
}

// Publisher (Subject)
// Editor is the actual Publisher/Subject that generates events
class Editor {
    // HAS-A relationship: Editor HAS-A EventManager to handle subscriptions
    public EventManager events;
    private String content;
    
    public Editor() {
        // Initialize with supported event types
        this.events = new EventManager("open", "save", "edit");
    }
    
    // When a file is opened, notify all subscribers of "open" event
    public void openFile(String path) {
        this.content = readFile(path);
        events.notify("open", path);
    }
    
    // When a file is saved, notify all subscribers of "save" event
    public void saveFile() {
        events.notify("save", this.content);
    }
    
    // When content is edited, notify all subscribers of "edit" event
    public void editContent(String newContent) {
        this.content = newContent;
        events.notify("edit", this.content);
    }
    
    private String readFile(String path) {
        // This is a simplified version. In a real application,
        // you would actually read from a file.
        return "Content of " + path;
    }
}

// Concrete Subscribers (Concrete Observers)
// LoggingListener IS-A EventListener that logs events to a file
class LoggingListener implements EventListener {
    private String logFile;
    
    public LoggingListener(String logFile) {
        this.logFile = logFile;
    }
    
    // IS-A relationship: LoggingListener IS-A EventListener through interface implementation
    @Override
    public void update(String eventType, String data) {
        System.out.println("Logging to " + logFile + ": Event " + eventType + 
                         " with data: " + data);
    }
}

// EmailNotificationListener IS-A EventListener that sends email notifications
class EmailNotificationListener implements EventListener {
    private String email;
    
    public EmailNotificationListener(String email) {
        this.email = email;
    }
    
    // IS-A relationship: EmailNotificationListener IS-A EventListener through interface implementation
    @Override
    public void update(String eventType, String data) {
        System.out.println("Email to " + email + ": Event " + eventType + 
                         " with data: " + data);
    }
}

/**
 * Main class that demonstrates the Observer pattern in action
 * 
 * Key benefits of this pattern:
 * 1. Loose coupling: Publishers don't need to know about subscribers
 * 2. Dynamic relationships: Subscribers can be added/removed at runtime
 * 3. One-to-many notifications: One event can trigger multiple actions
 * 4. Type-based filtering: Subscribers only receive events they care about
 * 
 * Real-world applications:
 * - Event handling systems in GUIs
 * - Message brokers in distributed systems
 * - Notification systems
 * - MVC architecture (Model notifies Views)
 */
public class PubSub {
    public static void main(String[] args) {
        // Create the publisher
        Editor editor = new Editor();
        
        // Create subscribers
        LoggingListener logger = new LoggingListener("/path/to/log.txt");
        EmailNotificationListener emailAlert = 
            new EmailNotificationListener("admin@example.com");
        
        // Subscribe to events
        // Logger subscribes to "open" and "edit" events
        editor.events.subscribe("open", logger);
        editor.events.subscribe("edit", logger);
        
        // Email alert subscribes to "save" and "edit" events
        editor.events.subscribe("save", emailAlert);
        editor.events.subscribe("edit", emailAlert);
        
        // Simulate some editor operations
        // This will trigger "open" event - only logger will be notified
        editor.openFile("test.txt");
        
        // This will trigger "edit" event - both logger and email will be notified
        editor.editContent("New content");
        
        // This will trigger "save" event - only email will be notified
        editor.saveFile();
        
        // Unsubscribe from events - demonstrating dynamic subscription management
        editor.events.unsubscribe("edit", emailAlert);
        
        // This edit won't notify the email listener anymore
        editor.editContent("Another update");
    }
}