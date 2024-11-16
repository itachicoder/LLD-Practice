package pu.com.ay.DesignPatterns.CreationalPatterns.Singleton;

// File: SingletonPatternDemo.java

// 1. Basic Singleton (Not Thread Safe)
class BasicSingleton {
    private static BasicSingleton instance;
    private String data;
    
    private BasicSingleton() {
        data = "Basic Singleton Data";
    }
    
    public static BasicSingleton getInstance() {
        if (instance == null) {
            instance = new BasicSingleton();
        }
        return instance;
    }
    
    public String getData() {
        return data;
    }
}

// 2. Thread Safe Singleton using synchronized
class ThreadSafeSingleton {
    private static ThreadSafeSingleton instance;
    private String data;
    
    private ThreadSafeSingleton() {
        data = "Thread Safe Singleton Data";
    }
    
    public static synchronized ThreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingleton();
        }
        return instance;
    }
    
    public String getData() {
        return data;
    }
}

// 3. Double Checked Locking Singleton
class DoubleCheckedSingleton {
    private static volatile DoubleCheckedSingleton instance;
    private String data;
    
    private DoubleCheckedSingleton() {
        data = "Double Checked Singleton Data";
    }
    
    public static DoubleCheckedSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckedSingleton();
                }
            }
        }
        return instance;
    }
    
    public String getData() {
        return data;
    }
}

// 4. Bill Pugh Singleton (Most Efficient)
class BillPughSingleton {
    private String data;
    
    private BillPughSingleton() {
        data = "Bill Pugh Singleton Data";
    }
    
    private static class SingletonHelper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }
    
    public static BillPughSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
    
    public String getData() {
        return data;
    }
}

// 5. Enum Singleton
enum EnumSingleton {
    INSTANCE("Enum Singleton Data");
    
    private String data;
    
    EnumSingleton(String data) {
        this.data = data;
    }
    
    public String getData() {
        return data;
    }
}

// 6. Eager Initialization Singleton
class EagerSingleton {
    private static final EagerSingleton instance = new EagerSingleton();
    private String data;
    
    private EagerSingleton() {
        data = "Eager Singleton Data";
    }
    
    public static EagerSingleton getInstance() {
        return instance;
    }
    
    public String getData() {
        return data;
    }
}

// Main class to test all singleton patterns
public class SingletonPatternDemo {
    public static void main(String[] args) {
        // 1. Test Basic Singleton
        System.out.println("\n=== Testing Basic Singleton ===");
        BasicSingleton basic1 = BasicSingleton.getInstance();
        BasicSingleton basic2 = BasicSingleton.getInstance();
        System.out.println("Basic Singleton Same Instance: " + (basic1 == basic2));
        System.out.println("Data: " + basic1.getData());
        
        // 2. Test Thread Safe Singleton
        System.out.println("\n=== Testing Thread Safe Singleton ===");
        ThreadSafeSingleton threadSafe1 = ThreadSafeSingleton.getInstance();
        ThreadSafeSingleton threadSafe2 = ThreadSafeSingleton.getInstance();
        System.out.println("Thread Safe Singleton Same Instance: " + (threadSafe1 == threadSafe2));
        System.out.println("Data: " + threadSafe1.getData());
        
        // 3. Test Double Checked Singleton
        System.out.println("\n=== Testing Double Checked Singleton ===");
        DoubleCheckedSingleton doubleChecked1 = DoubleCheckedSingleton.getInstance();
        DoubleCheckedSingleton doubleChecked2 = DoubleCheckedSingleton.getInstance();
        System.out.println("Double Checked Singleton Same Instance: " + (doubleChecked1 == doubleChecked2));
        System.out.println("Data: " + doubleChecked1.getData());
        
        // 4. Test Bill Pugh Singleton
        System.out.println("\n=== Testing Bill Pugh Singleton ===");
        BillPughSingleton billPugh1 = BillPughSingleton.getInstance();
        BillPughSingleton billPugh2 = BillPughSingleton.getInstance();
        System.out.println("Bill Pugh Singleton Same Instance: " + (billPugh1 == billPugh2));
        System.out.println("Data: " + billPugh1.getData());
        
        // 5. Test Enum Singleton
        System.out.println("\n=== Testing Enum Singleton ===");
        EnumSingleton enumSingleton1 = EnumSingleton.INSTANCE;
        EnumSingleton enumSingleton2 = EnumSingleton.INSTANCE;
        System.out.println("Enum Singleton Same Instance: " + (enumSingleton1 == enumSingleton2));
        System.out.println("Data: " + enumSingleton1.getData());
        
        // 6. Test Eager Singleton
        System.out.println("\n=== Testing Eager Singleton ===");
        EagerSingleton eager1 = EagerSingleton.getInstance();
        EagerSingleton eager2 = EagerSingleton.getInstance();
        System.out.println("Eager Singleton Same Instance: " + (eager1 == eager2));
        System.out.println("Data: " + eager1.getData());
        
        // 7. Test Thread Safety
        System.out.println("\n=== Testing Thread Safety ===");
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                DoubleCheckedSingleton instance = DoubleCheckedSingleton.getInstance();
                System.out.println("Thread " + Thread.currentThread().getId() + 
                                 " - Instance Hash: " + instance.hashCode());
            }).start();
        }
    }
}