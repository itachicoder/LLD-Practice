package pu.com.ay.HeadFirstImplementation.FactorAbstractFactory.AbstractFactory;

class MacButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering Mac style button");
    }
}
