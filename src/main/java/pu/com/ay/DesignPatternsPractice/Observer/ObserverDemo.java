package pu.com.ay.DesignPatternsPractice.Observer;

import java.util.List;

public class ObserverDemo {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();

        ObserverExtend obs1 = new ObserverExtend("Observer-1", subject);
        ObserverExtend obs2 = new ObserverExtend("Observer-2", subject);

        // Register both observers at once (as per Subject interface design)
        subject.registerObserver(List.of(obs1, obs2));

        System.out.println("Setting value to 100:");
        subject.setValue(100);

        System.out.println("\nSetting value to 200:");
        subject.setValue(200);

        // Deregister obs1 and update again
        subject.degisterObserver(List.of(obs1));
        System.out.println("\nAfter deregistering Observer-1, setting value to 300:");
        subject.setValue(300);
    }
}
