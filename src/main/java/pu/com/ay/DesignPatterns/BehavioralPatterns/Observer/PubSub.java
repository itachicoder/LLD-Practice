package pu.com.ay.DesignPatterns.BehavioralPatterns.Observer;

// The EventManager handles subscription mechanics

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EventManager {
    private Map<String, List<EventListener>> listeners = new HashMap<>();
    
    public EventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }
    
    public void subscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        users.add(listener);
    }
    
    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        users.remove(listener);
    }
    
    public void notify(String eventType, String data) {
        List<EventListener> users = listeners.get(eventType);
        for (EventListener listener : users) {
            listener.update(eventType, data);
        }
    }
}

// The EventListener interface that all subscribers must implement
interface EventListener {
    void update(String eventType, String data);
}

// Publisher (Subject)
class Editor {
    public EventManager events;
    private String content;
    
    public Editor() {
        this.events = new EventManager("open", "save", "edit");
    }
    
    public void openFile(String path) {
        this.content = readFile(path);
        events.notify("open", path);
    }
    
    public void saveFile() {
        events.notify("save", this.content);
    }
    
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

// Concrete Subscribers
class LoggingListener implements EventListener {
    private String logFile;
    
    public LoggingListener(String logFile) {
        this.logFile = logFile;
    }
    
    @Override
    public void update(String eventType, String data) {
        System.out.println("Logging to " + logFile + ": Event " + eventType + 
                         " with data: " + data);
    }
}

class EmailNotificationListener implements EventListener {
    private String email;
    
    public EmailNotificationListener(String email) {
        this.email = email;
    }
    
    @Override
    public void update(String eventType, String data) {
        System.out.println("Email to " + email + ": Event " + eventType + 
                         " with data: " + data);
    }
}

// Example usage
public class PubSub {
    public static void main(String[] args) {
        Editor editor = new Editor();
        
        // Create subscribers
        LoggingListener logger = new LoggingListener("/path/to/log.txt");
        EmailNotificationListener emailAlert = 
            new EmailNotificationListener("admin@example.com");
        
        // Subscribe to events
        editor.events.subscribe("open", logger);
        editor.events.subscribe("save", emailAlert);
        editor.events.subscribe("edit", logger);
        editor.events.subscribe("edit", emailAlert);
        
        // Simulate some editor operations
        editor.openFile("test.txt");
        editor.editContent("New content");
        editor.saveFile();
        
        // Unsubscribe from events
        editor.events.unsubscribe("edit", emailAlert);
        
        // This edit won't notify the email listener
        editor.editContent("Another update");
    }
}