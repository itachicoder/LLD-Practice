package pu.com.ay.DesignPatterns.BehavioralPatterns.Mediator;

// Mediator interface

import java.util.HashMap;
import java.util.Map;

interface DialogMediator {
    void notify(Component sender, String event);
    void registerComponent(Component component);
}

// Abstract Component
abstract class Component {
    protected DialogMediator mediator;
    
    public Component(DialogMediator mediator) {
        this.mediator = mediator;
    }
}

// Concrete Components
class Button extends Component {
    private String name;
    
    public Button(String name, DialogMediator mediator) {
        super(mediator);
        this.name = name;
        mediator.registerComponent(this);
    }
    
    public void click() {
        mediator.notify(this, "click");
    }
    
    public String getName() {
        return name;
    }
}

class TextBox extends Component {
    private String value = "";
    private String name;
    
    public TextBox(String name, DialogMediator mediator) {
        super(mediator);
        this.name = name;
        mediator.registerComponent(this);
    }
    
    public void setValue(String value) {
        this.value = value;
        mediator.notify(this, "textChanged");
    }
    
    public String getValue() {
        return value;
    }
    
    public String getName() {
        return name;
    }
}

class Checkbox extends Component {
    private boolean checked = false;
    private String name;
    
    public Checkbox(String name, DialogMediator mediator) {
        super(mediator);
        this.name = name;
        mediator.registerComponent(this);
    }
    
    public void setChecked(boolean checked) {
        this.checked = checked;
        mediator.notify(this, "checkboxChanged");
    }
    
    public boolean isChecked() {
        return checked;
    }
    
    public String getName() {
        return name;
    }
}

// Concrete Mediator
class AuthenticationDialog implements DialogMediator {
    private Map<String, Component> components = new HashMap<>();
    
    @Override
    public void registerComponent(Component component) {
        if (component instanceof Button) {
            components.put(((Button) component).getName(), component);
        } else if (component instanceof TextBox) {
            components.put(((TextBox) component).getName(), component);
        } else if (component instanceof Checkbox) {
            components.put(((Checkbox) component).getName(), component);
        }
    }
    
    @Override
    public void notify(Component sender, String event) {
        if (sender instanceof Button && ((Button) sender).getName().equals("login")) {
            if (event.equals("click")) {
                handleLogin();
            }
        } else if (sender instanceof Checkbox && ((Checkbox) sender).getName().equals("rememberMe")) {
            if (event.equals("checkboxChanged")) {
                handleRememberMe();
            }
        } else if (sender instanceof TextBox) {
            if (event.equals("textChanged")) {
                validateForm();
            }
        }
    }
    
    private void handleLogin() {
        TextBox username = (TextBox) components.get("username");
        TextBox password = (TextBox) components.get("password");
        Checkbox remember = (Checkbox) components.get("rememberMe");
        
        System.out.println("Logging in with:");
        System.out.println("Username: " + username.getValue());
        System.out.println("Password: " + password.getValue());
        System.out.println("Remember me: " + remember.isChecked());
    }
    
    private void handleRememberMe() {
        Checkbox remember = (Checkbox) components.get("rememberMe");
        System.out.println("Remember me option changed to: " + remember.isChecked());
    }
    
    private void validateForm() {
        TextBox username = (TextBox) components.get("username");
        TextBox password = (TextBox) components.get("password");
        Button loginButton = (Button) components.get("login");
        
        System.out.println("Validating form...");
        // Simple validation logic
        boolean isValid = !username.getValue().isEmpty() && !password.getValue().isEmpty();
        System.out.println("Form is " + (isValid ? "valid" : "invalid"));
    }
}

// Client code
public class Main {
    public static void main(String[] args) {
        AuthenticationDialog dialog = new AuthenticationDialog();
        
        Button loginButton = new Button("login", dialog);
        TextBox username = new TextBox("username", dialog);
        TextBox password = new TextBox("password", dialog);
        Checkbox rememberMe = new Checkbox("rememberMe", dialog);
        
        // Simulate user interaction
        username.setValue("john_doe");
        password.setValue("secret123"); 
        rememberMe.setChecked(true);
        loginButton.click();
    }
}