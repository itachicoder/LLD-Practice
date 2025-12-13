package pu.com.ay.DesignPatterns.BehavioralPatterns.Observer.HeadFIrst;

public interface Subject {

    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
    
}
