package pu.com.ay.DesignPatterns.CreationalPatterns.AbstractFactory;


// Abstract Products
interface Chair {
    void sitOn();
}

interface Sofa {
    void lieOn();
}

interface CoffeeTable {
    void putOn();
}

// Concrete Products - Modern Style
class ModernChair implements Chair {
    @Override
    public void sitOn() {
        System.out.println("Sitting on a modern chair");
    }
}

class ModernSofa implements Sofa {
    @Override
    public void lieOn() {
        System.out.println("Lying on a modern sofa");
    }
}

class ModernCoffeeTable implements CoffeeTable {
    @Override
    public void putOn() {
        System.out.println("Putting items on a modern coffee table");
    }
}

// Concrete Products - Victorian Style
class VictorianChair implements Chair {
    @Override
    public void sitOn() {
        System.out.println("Sitting on a Victorian chair");
    }
}

class VictorianSofa implements Sofa {
    @Override
    public void lieOn() {
        System.out.println("Lying on a Victorian sofa");
    }
}

class VictorianCoffeeTable implements CoffeeTable {
    @Override
    public void putOn() {
        System.out.println("Putting items on a Victorian coffee table");
    }
}

// Abstract Factory
interface FurnitureFactory {
    Chair createChair();
    Sofa createSofa();
    CoffeeTable createCoffeeTable();
}

// Concrete Factories
class ModernFurnitureFactory implements FurnitureFactory {
    @Override
    public Chair createChair() {
        return new ModernChair();
    }

    @Override
    public Sofa createSofa() {
        return new ModernSofa();
    }

    @Override
    public CoffeeTable createCoffeeTable() {
        return new ModernCoffeeTable();
    }
}

class VictorianFurnitureFactory implements FurnitureFactory {
    @Override
    public Chair createChair() {
        return new VictorianChair();
    }

    @Override
    public Sofa createSofa() {
        return new VictorianSofa();
    }

    @Override
    public CoffeeTable createCoffeeTable() {
        return new VictorianCoffeeTable();
    }
}

// Client code
class FurnitureShop {
    private final FurnitureFactory factory;

    FurnitureShop(FurnitureFactory factory) {
        this.factory = factory;
    }

    void orderFurnitureSet() {
        Chair chair = factory.createChair();
        Sofa sofa = factory.createSofa();
        CoffeeTable coffeeTable = factory.createCoffeeTable();

        System.out.println("Ordered a matching furniture set:");
        chair.sitOn();
        sofa.lieOn();
        coffeeTable.putOn();
    }
}

// Main class to demonstrate usage
class Main {
    public static void main(String[] args) {
        // Create a shop with Modern furniture factory
        FurnitureShop modernShop = new FurnitureShop(new ModernFurnitureFactory());
        System.out.println("Modern Furniture Shop Order:");
        modernShop.orderFurnitureSet();

        System.out.println("\n-------------------\n");

        // Create a shop with Victorian furniture factory
        FurnitureShop victorianShop = new FurnitureShop(new VictorianFurnitureFactory());
        System.out.println("Victorian Furniture Shop Order:");
        victorianShop.orderFurnitureSet();
    }
}