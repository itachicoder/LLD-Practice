package pu.com.ay.DesignPatterns.BehavioralPatterns.Memento;

// With Memento Pattern - Solution

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


// 1. Memento - Stores the internal state of the TextDocument
class DocumentMemento {
    private final String content;
    private final int cursorPosition;
    private final Map<Integer, String> formatting;
    private final LocalDateTime timestamp;

    // Constructor - only the Originator can access the full state
    DocumentMemento(String content, int cursorPosition, Map<Integer, String> formatting) {
        // Validate state
        if (cursorPosition < 0 || cursorPosition > content.length()) {
            throw new IllegalArgumentException("Invalid cursor position");
        }

        this.content = content;
        this.cursorPosition = cursorPosition;
        this.formatting = new HashMap<>(formatting);
        this.timestamp = LocalDateTime.now();
    }

    // Protected methods - only accessible to the originator
    String getContent() {
        return content;
    }

    int getCursorPosition() {
        return cursorPosition;
    }

    Map<Integer, String> getFormatting() {
        return new HashMap<>(formatting);
    }

    // Public method - anyone can get the timestamp
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

// 2. Originator - The object whose state needs to be saved
class TextDocument {
    private StringBuilder content;
    private int cursorPosition;
    private Map<Integer, String> formatting;

    public TextDocument() {
        this.content = new StringBuilder();
        this.cursorPosition = 0;
        this.formatting = new HashMap<>();
    }

    // Creates a memento containing the current state
    public DocumentMemento save() {
        return new DocumentMemento(
            content.toString(),
            cursorPosition,
            formatting
        );
    }

    // Restores state from a memento
    public void restore(DocumentMemento memento) {
        this.content = new StringBuilder(memento.getContent());
        this.cursorPosition = memento.getCursorPosition();
        this.formatting = memento.getFormatting();
    }

    public void addText(String text) {
        if (text == null) throw new IllegalArgumentException("Text cannot be null");
        
        content.insert(cursorPosition, text);
        cursorPosition += text.length();
    }

    public void addFormatting(int position, String format) {
        if (position < 0 || position >= content.length()) {
            throw new IllegalArgumentException("Invalid position");
        }
        formatting.put(position, format);
    }
}

// 3. Caretaker - Manages the history of mementos
class DocumentCaretaker {
    private final List<DocumentMemento> history;
    private final TextDocument document;
    private final int maxHistorySize;

    public DocumentCaretaker(TextDocument document, int maxHistorySize) {
        this.document = document;
        this.history = new ArrayList<>();
        this.maxHistorySize = maxHistorySize;
    }

    public void save() {
        if (history.size() >= maxHistorySize) {
            history.remove(0); // Remove oldest state
        }
        history.add(document.save());
    }

    public boolean undo() {
        if (history.isEmpty()) {
            return false;
        }

        try {
            DocumentMemento memento = history.remove(history.size() - 1);
            document.restore(memento);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to restore state: " + e.getMessage());
            return false;
        }
    }

    public List<LocalDateTime> getHistory() {
        return history.stream()
                     .map(DocumentMemento::getTimestamp)
                     .collect(Collectors.toList());
    }
}

// 4. Client code demonstrating proper usage
class TextEditor {
    private final TextDocument document;
    private final DocumentCaretaker caretaker;

    public TextEditor() {
        this.document = new TextDocument();
        this.caretaker = new DocumentCaretaker(document, 100);
    }

    public void processComplexOperation() {
        try {
            // Save state before operation
            caretaker.save();
            
            // Perform operations
            document.addText("Hello");
            document.addFormatting(0, "bold");
            
            if (someCondition()) {
                // Safe rollback
                caretaker.undo();
                return;
            }
            
            // Save state after successful operation
            caretaker.save();
            document.addText(" World");
            
        } catch (Exception e) {
            // Safe error recovery
            caretaker.undo();
            throw new RuntimeException("Operation failed", e);
        }
    }

    private boolean someCondition() {
        return false;
    }
}

// Example usage
public class MainWithMemento {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        
        try {
            editor.processComplexOperation();
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}