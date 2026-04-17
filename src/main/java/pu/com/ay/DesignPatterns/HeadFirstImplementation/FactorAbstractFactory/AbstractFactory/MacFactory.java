package pu.com.ay.HeadFirstImplementation.FactorAbstractFactory.AbstractFactory;

class MacFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }
    
    @Override
    public CheckBox createCheckbox() {
        return new MacCheckbox();
    }
}