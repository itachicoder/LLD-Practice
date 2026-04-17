package pu.com.ay.HeadFirstImplementation.Observer;

interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}   