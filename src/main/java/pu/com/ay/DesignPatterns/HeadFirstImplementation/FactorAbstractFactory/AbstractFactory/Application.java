package pu.com.ay.HeadFirstImplementation.FactorAbstractFactory.AbstractFactory;

class Application {
    private Button button;
    private CheckBox checkbox;
    
    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }
    
    public void render() {
        button.render();
        checkbox.render();
    }
}