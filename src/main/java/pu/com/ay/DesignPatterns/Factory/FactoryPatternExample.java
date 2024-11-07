package pu.com.ay.DesignPatterns.Factory;

    interface Button {
        void render();
    }

// Concrete Product classes
class WindowsButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering a Windows-styled button");
    }
}

class LinuxButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering a Linux-styled button");
    }
}

// The Creator class
abstract class Dialog {
    public void renderWindow() {
        // ... other window rendering code
        Button button = createButton();
        button.render();
    }

    protected abstract Button createButton();
}

// Concrete Creator classes
class WindowsDialog extends Dialog {
    @Override
    protected Button createButton() {
        return new WindowsButton();
    }
}

class LinuxDialog extends Dialog {
    @Override
    protected Button createButton() {
        return new LinuxButton();
    }
}

// Client code
public class FactoryPatternExample {
    public static void main(String[] args) {
        Dialog windowsDialog = new WindowsDialog();
        windowsDialog.renderWindow();

        Dialog linuxDialog = new LinuxDialog();
        linuxDialog.renderWindow();
    }
}