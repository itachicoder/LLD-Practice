package pu.com.ay.HeadFirstImplementation.FactorAbstractFactory.AbstractFactory;

class WindowsFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }
    
    @Override
    public CheckBox createCheckbox() {
        return new WindowsCheckbox();
    }
}