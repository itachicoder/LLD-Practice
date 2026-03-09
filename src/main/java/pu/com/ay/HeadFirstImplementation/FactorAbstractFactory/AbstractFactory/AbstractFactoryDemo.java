package pu.com.ay.HeadFirstImplementation.FactorAbstractFactory.AbstractFactory;

public class AbstractFactoryDemo {
    public static void main(String[] args) {
        // Detect OS and choose factory
        String os = "Windows"; // or "Mac"
        
        GUIFactory factory;
        
        if (os.equals("Windows")) {
            factory = new WindowsFactory();
        } else {
            factory = new MacFactory();
        }
        
        Application app = new Application(factory);
        app.render();
    }
}
