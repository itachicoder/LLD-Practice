package pu.com.ay.HeadFirstImplementation.FactorAbstractFactory.Factory;

public class shapeFactory {

    public shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new rectangle();
        } 
        return null;
    }
    
}
