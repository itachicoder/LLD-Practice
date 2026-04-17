package pu.com.ay.DesignPatternsPractice.Observer;

public class ObserverExtend implements Observer {

    private int x = 20;
    int y = 30;
    private String name;
    private ConcreteSubject subject;

    public ObserverExtend(String name, ConcreteSubject subject) {
        this.name = name;
        this.subject = subject;
    }

    @Override
    public void update() {
        this.x = subject.getValue();
        System.out.println(name + " received update: value = " + x);
    }
}
