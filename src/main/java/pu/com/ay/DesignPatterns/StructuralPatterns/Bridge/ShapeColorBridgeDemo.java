package pu.com.ay.DesignPatterns.StructuralPatterns.Bridge;

public class ShapeColorBridgeDemo {

    // Color interface (Implementation)
    interface Color {
        String fill();
    }

    // Concrete Implementations of Color
    static class Red implements Color {
        @Override
        public String fill() {
            return "Color is Red";
        }
    }

    static class Blue implements Color {
        @Override
        public String fill() {
            return "Color is Blue";
        }
    }

    // Shape abstract class (Abstraction)
    abstract static class Shape {
        protected Color color;

        public Shape(Color color) {
            this.color = color;
        }

        abstract String draw();
    }

    // Concrete Abstractions
    static class Circle extends Shape {
        public Circle(Color color) {
            super(color);
        }

        @Override
        public String draw() {
            return "Drawing Circle. " + color.fill();
        }
    }

    static class Square extends Shape {
        public Square(Color color) {
            super(color);
        }

        @Override
        public String draw() {
            return "Drawing Square. " + color.fill();
        }
    }

    // Main method to demonstrate the Bridge Pattern
    public static void main(String[] args) {
        // Creating red-colored shapes
        Shape redCircle = new Circle(new Red());
        Shape redSquare = new Square(new Red());

        // Creating blue-colored shapes
        Shape blueCircle = new Circle(new Blue());
        Shape blueSquare = new Square(new Blue());

        // Drawing shapes with colors
        System.out.println(redCircle.draw());
        System.out.println(redSquare.draw());
        System.out.println(blueCircle.draw());
        System.out.println(blueSquare.draw());
    }
}
