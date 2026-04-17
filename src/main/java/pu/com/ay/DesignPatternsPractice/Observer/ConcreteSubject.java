package pu.com.ay.DesignPatternsPractice.Observer;

import java.util.ArrayList;
import java.util.List;

public class ConcreteSubject implements Subject {

    private List<Observer> observers = new ArrayList<>();
    private int value;

    @Override
    public void registerObserver(List<Observer> observerList) {
        observers.addAll(observerList);
    }

    @Override
    public void degisterObserver(List<Observer> observerList) {
        observers.removeAll(observerList);
    }

    @Override
    public void notifyUsers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void setValue(int value) {
        this.value = value;
        notifyUsers();
    }

    public int getValue() {
        return value;
    }
}
