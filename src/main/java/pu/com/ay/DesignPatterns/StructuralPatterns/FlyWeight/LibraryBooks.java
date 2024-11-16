package pu.com.ay.DesignPatterns.StructuralPatterns.FlyWeight;

// 1. Flyweight class - stores shared data

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CharacterFlyweight {
    private final char symbol;            // The actual character
    private final String fontFamily;      // Font family
    private final int fontSize;           // Font size
    
    public CharacterFlyweight(char symbol, String fontFamily, int fontSize) {
        this.symbol = symbol;
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
    }
    
    public void display(int x, int y, String color) {
        System.out.printf("Displaying %c at (%d,%d) in %s color using %s font, size %d%n", 
                         symbol, x, y, color, fontFamily, fontSize);
    }
}

// 2. Flyweight Factory - manages flyweight objects
class CharacterFactory {
    private Map<Character, CharacterFlyweight> characters = new HashMap<>();
    
    public CharacterFlyweight getCharacter(char symbol) {
        // If character doesn't exist, create it
        if (!characters.containsKey(symbol)) {
            System.out.println(symbol + "Creating new character object for: ");
            characters.put(symbol, new CharacterFlyweight(symbol, "Arial", 12));
        } else {
            System.out.println("Reusing existing character object for: " + symbol);
        }
        return characters.get(symbol);
    }
}

// 3. Context class - stores unique data for each character instance
class FormattedCharacter {
    private CharacterFlyweight flyweight;  // Reference to shared data
    private int x, y;                      // Position - unique for each instance
    private String color;                  // Color - unique for each instance
    
    public FormattedCharacter(CharacterFlyweight flyweight, int x, int y, String color) {
        this.flyweight = flyweight;
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    public void display() {
        flyweight.display(x, y, color);
    }
}

// 4. Client code - Text Editor
class TextEditor {
    private List<FormattedCharacter> characters = new ArrayList<>();
    private CharacterFactory factory = new CharacterFactory();
    
    public void addCharacter(char symbol, int x, int y, String color) {
        // Get flyweight from factory
        CharacterFlyweight flyweight = factory.getCharacter(symbol);
        
        // Create new character with unique position and color
        FormattedCharacter character = new FormattedCharacter(flyweight, x, y, color);
        characters.add(character);
    }
    
    public void displayText() {
        for (FormattedCharacter character : characters) {
            character.display();
        }
    }
}

// Main class to demonstrate
public class LibraryBooks {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        
        // Adding the text "HELLO"
        editor.addCharacter('H', 0, 0, "blue");
        editor.addCharacter('E', 10, 0, "red");
        editor.addCharacter('L', 20, 0, "green");
        editor.addCharacter('L', 30, 0, "black");  // Reuses 'L' flyweight
        editor.addCharacter('O', 40, 0, "purple");
        
        editor.displayText();
    }
}
