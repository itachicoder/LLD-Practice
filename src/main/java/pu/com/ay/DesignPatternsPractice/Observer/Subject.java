package pu.com.ay.DesignPatternsPractice.Observer;

import java.util.List;

public interface Subject {
    void registerObserver(List<Observer> observer);
    void degisterObserver(List<Observer> observer);
    void notifyUsers();

}
