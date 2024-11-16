package pu.com.ay.DesignPatterns.CreationalPatterns.Builder;
// 1. Product Class
class Pizza {
    private String dough;
    private String sauce;
    private String topping;
    
    // Private constructor - can only be built using builder
    private Pizza(PizzaBuilder builder) {
        this.dough = builder.dough;
        this.sauce = builder.sauce;
        this.topping = builder.topping;
    }
    
    // Builder Class
    public static class PizzaBuilder {
        private String dough;
        private String sauce;
        private String topping;
        
        // Builder methods
        public PizzaBuilder dough(String dough) {
            this.dough = dough;
            return this;
        }
        
        public PizzaBuilder sauce(String sauce) {
            this.sauce = sauce;
            return this;
        }
        
        public PizzaBuilder topping(String topping) {
            this.topping = topping;
            return this;
        }
        
        // Final build method
        public Pizza build() {
            return new Pizza(this);
        }
    }
}

// 2. Director Class (optional) - knows how to build specific types
class PizzaChef {
    public void makeMargherita(Pizza.PizzaBuilder builder) {
        builder.dough("Thin Crust")
               .sauce("Tomato")
               .topping("Mozzarella");
    }
    
    public void makePepperoni(Pizza.PizzaBuilder builder) {
        builder.dough("Thick Crust")
               .sauce("Tomato")
               .topping("Pepperoni");
    }
}

// 3. Usage
public class PizzaDemo {
    public static void main(String[] args) {
        // Using Director
        PizzaChef chef = new PizzaChef();
        Pizza.PizzaBuilder builder = new Pizza.PizzaBuilder();
        
        chef.makeMargherita(builder);
        Pizza margherita = builder.build();
        
        // Building custom pizza without Director
        Pizza customPizza = new Pizza.PizzaBuilder()
            .dough("Gluten Free")
            .sauce("Pesto")
            .topping("Chicken")
            .build();
    }
}