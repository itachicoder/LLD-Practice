package pu.com.ay.DesignPatterns.CreationalPatterns.Factory;

// The Product interface
interface Transport {
    void deliver();
}

// Concrete Product classes
class Truck implements Transport {
    @Override
    public void deliver() {
        System.out.println("Delivering cargo by land");
    }
}

class Ship implements Transport {
    @Override
    public void deliver() {
        System.out.println("Delivering cargo by sea");
    }
}

// The Factory class
abstract class Logistics {
    public void processDelivery() {
        // ... other logistics processing code
        Transport transport = createTransport();
        transport.deliver();
    }

    protected abstract Transport createTransport();
}

// Concrete Creator classes
class RoadLogistics extends Logistics {
    @Override
    protected Transport createTransport() {
        return new Truck();
    }
}

class SeaLogistics extends Logistics {
    @Override
    protected Transport createTransport() {
        return new Ship();
    }
}

// Client code
public class FactoryPatternExample2 {
    public static void main(String[] args) {
        Logistics roadLogistics = new RoadLogistics();
        roadLogistics.processDelivery();

        Logistics seaLogistics = new SeaLogistics();
        seaLogistics.processDelivery();
    }
}