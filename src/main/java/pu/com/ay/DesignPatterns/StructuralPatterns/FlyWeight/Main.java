package pu.com.ay.DesignPatterns.StructuralPatterns.FlyWeight;

// Flyweight class storing intrinsic state

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Create a separate file called Main.java with all classes

// Flyweight class storing intrinsic state
class ParticleFlyweight {
    private final String sprite;
    private final String color;
    
    public ParticleFlyweight(String sprite, String color) {
        this.sprite = sprite;
        this.color = color;
    }
    
    public void render(double x, double y, Vector movement, double speed) {
        System.out.println("Drawing " + color + " particle at (" + x + "," + y + 
                          ") with speed " + speed + " using sprite: " + sprite);
    }
}

// Context class storing extrinsic state
class Particle {
    private ParticleFlyweight flyweight;
    private double x, y;
    private Vector movement;
    private double speed;
    
    public Particle(ParticleFlyweight flyweight, double x, double y, 
                   Vector movement, double speed) {
        this.flyweight = flyweight;
        this.x = x;
        this.y = y;
        this.movement = movement;
        this.speed = speed;
    }
    
    public void render() {
        flyweight.render(x, y, movement, speed);
    }
    
    public void move() {
        x += movement.getX() * speed;
        y += movement.getY() * speed;
    }
}

class Vector {
    private double x, y;
    
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() { return x; }
    public double getY() { return y; }
}

class ParticleFactory {
    private Map<String, ParticleFlyweight> flyweights = new HashMap<>();
    
    public ParticleFlyweight getParticleFlyweight(String sprite, String color) {
        String key = sprite + color;
        if (!flyweights.containsKey(key)) {
            flyweights.put(key, new ParticleFlyweight(sprite, color));
        }
        return flyweights.get(key);
    }
}

class Game {
    private List<Particle> particles = new ArrayList<>();
    private ParticleFactory factory = new ParticleFactory();
    
    public void spawnBullet(double x, double y, Vector direction) {
        ParticleFlyweight bulletFlyweight = 
            factory.getParticleFlyweight("bullet.png", "yellow");
        
        Particle bullet = new Particle(bulletFlyweight, x, y, direction, 5.0);
        particles.add(bullet);
    }
    
    public void spawnMissile(double x, double y, Vector direction) {
        ParticleFlyweight missileFlyweight = 
            factory.getParticleFlyweight("missile.png", "red");
        
        Particle missile = new Particle(missileFlyweight, x, y, direction, 2.0);
        particles.add(missile);
    }
    
    public void gameLoop() {
        for (Particle particle : particles) {
            particle.move();
            particle.render();
        }
    }
}

// Main class with the entry point
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        
        // Spawn some particles
        game.spawnBullet(100, 100, new Vector(1, 0));
        game.spawnBullet(150, 150, new Vector(1, 1));
        game.spawnMissile(200, 200, new Vector(0, 1));
        
        // Run one frame of the game loop
        game.gameLoop();
    }
}