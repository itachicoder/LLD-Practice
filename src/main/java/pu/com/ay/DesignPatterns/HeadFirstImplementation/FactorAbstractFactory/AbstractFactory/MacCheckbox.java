package pu.com.ay.HeadFirstImplementation.FactorAbstractFactory.AbstractFactory;

class MacCheckbox implements CheckBox {
    
    @Override
    public void render() {
        System.out.println("Rendering Mac style checkbox");
    }
}