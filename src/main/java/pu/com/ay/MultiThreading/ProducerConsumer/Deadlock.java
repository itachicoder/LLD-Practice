package pu.com.ay.MultiThreading.ProducerConsumer;

public class Deadlock {

    public static void main(String[] args) {
        // Use Objects instead of String literals for cleaner locking
        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1: Holding lock 1...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                
                System.out.println("Thread 1: Waiting for lock 2...");
                synchronized (lock2) {
                    System.out.println("Thread 1: Acquired lock 2!");
                }
            }
        }, "thread1");

        Thread thread2 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 2: Holding lock 2...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                
                System.out.println("Thread 2: Waiting for lock 1...");
                synchronized (lock2) {
                    System.out.println("Thread 2: Acquired lock 1!");
                }
            }
        }, "thread2");

        thread1.start();
        thread2.start();
    }
}