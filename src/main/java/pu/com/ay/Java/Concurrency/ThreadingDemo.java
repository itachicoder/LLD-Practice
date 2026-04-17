package pu.com.ay.Java.Concurrency;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class ThreadingDemo {

    // =========================================================
    // CONCEPT 1 — Race Condition
    // count++ is NOT atomic → read → increment → write (3 steps)
    // Two threads can interleave these steps → wrong result
    // =========================================================
    static int unsafeCount = 0;
    static AtomicInteger atomicCount = new AtomicInteger(0);
    static int synchronizedCount = 0;

    static synchronized void incrementSynchronized() {
        synchronizedCount++;
    }

    // =========================================================
    // CONCEPT 2 — Creating Threads: 3 ways
    // =========================================================
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread class: " + Thread.currentThread().getName());
        }
    }

    // =========================================================
    // CONCEPT 3 — Producer Consumer Problem
    // Classic interview problem — shows wait/notify understanding
    // =========================================================
    static class ProducerConsumer {
        private final int[] buffer;
        private int count = 0;

        ProducerConsumer(int size) {
            buffer = new int[size];
        }

        // Producer — adds item, waits if buffer full
        synchronized void produce(int item) throws InterruptedException {
            while (count == buffer.length) {
                System.out.println("Buffer full, producer waiting...");
                wait(); // releases lock, waits for consumer to notify
            }
            buffer[count++] = item;
            System.out.println("Produced: " + item + " | Buffer size: " + count);
            notify(); // wake up consumer
        }

        // Consumer — takes item, waits if buffer empty
        synchronized int consume() throws InterruptedException {
            while (count == 0) {
                System.out.println("Buffer empty, consumer waiting...");
                wait(); // releases lock, waits for producer to notify
            }
            int item = buffer[--count];
            System.out.println("Consumed: " + item + " | Buffer size: " + count);
            notify(); // wake up producer
            return item;
        }
    }

    // =========================================================
    // CONCEPT 4 — Deadlock Demo
    // Thread1: locks A then tries B
    // Thread2: locks B then tries A → DEADLOCK
    // =========================================================
    static final Object lockA = new Object();
    static final Object lockB = new Object();

    static void deadlockThread1() {
        synchronized (lockA) {
            System.out.println("Thread1 acquired A, waiting for B...");
            try { Thread.sleep(50); } catch (InterruptedException e) {}
            synchronized (lockB) { // Thread2 already holds B → waits forever
                System.out.println("Thread1 acquired B");
            }
        }
    }

    static void deadlockThread2() {
        synchronized (lockB) {
            System.out.println("Thread2 acquired B, waiting for A...");
            try { Thread.sleep(50); } catch (InterruptedException e) {}
            synchronized (lockA) { // Thread1 already holds A → waits forever
                System.out.println("Thread2 acquired A");
            }
        }
    }

    // DEADLOCK FIX — always acquire locks in same order
    static void safeThread1() {
        synchronized (lockA) {        // both threads: A first
            synchronized (lockB) {    // both threads: B second → no deadlock
                System.out.println("SafeThread1 acquired A then B");
            }
        }
    }

    static void safeThread2() {
        synchronized (lockA) {        // same order as safeThread1
            synchronized (lockB) {
                System.out.println("SafeThread2 acquired A then B");
            }
        }
    }

    // =========================================================
    // CONCEPT 5 — ReentrantLock vs synchronized
    // =========================================================
    static class BankAccount {
        private double balance;
        private final ReentrantLock lock = new ReentrantLock();

        BankAccount(double balance) { this.balance = balance; }

        // tryLock — doesn't block forever, gives up after timeout
        boolean transfer(BankAccount target, double amount) {
            try {
                // try to acquire both locks within 1 second
                if (lock.tryLock(1, TimeUnit.SECONDS)) {
                    try {
                        if (target.lock.tryLock(1, TimeUnit.SECONDS)) {
                            try {
                                if (balance < amount) return false;
                                balance -= amount;
                                target.balance += amount;
                                System.out.println("Transferred: " + amount);
                                return true;
                            } finally {
                                target.lock.unlock(); // always in finally!
                            }
                        }
                    } finally {
                        lock.unlock(); // always in finally!
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Transfer failed — could not acquire locks");
            return false;
        }

        double getBalance() { return balance; }
    }

    // =========================================================
    // CONCEPT 6 — volatile keyword
    // Without volatile: thread may read stale value from CPU cache
    // With volatile: always reads from main memory
    // =========================================================
    static volatile boolean running = true; // volatile — changes visible across threads

    static void volatileDemo() throws InterruptedException {
        Thread worker = new Thread(() -> {
            int count = 0;
            while (running) { // reads fresh value every iteration
                count++;
            }
            System.out.println("Worker stopped. Count: " + count);
        });

        worker.start();
        Thread.sleep(10);
        running = false; // main thread writes — worker thread sees it immediately
        worker.join();
        // Without volatile, worker might never see running=false (CPU cache)
    }

    // =========================================================
    // CONCEPT 7 — CountDownLatch
    // Main thread waits for N tasks to complete
    // Like a "start gun" — fire when all workers ready
    // =========================================================
    static void countDownLatchDemo() throws InterruptedException {
        int workerCount = 3;
        CountDownLatch latch = new CountDownLatch(workerCount);
        ExecutorService executor = Executors.newFixedThreadPool(workerCount);

        for (int i = 1; i <= workerCount; i++) {
            final int workerId = i;
            executor.submit(() -> {
                try {
                    Thread.sleep(workerId * 100L); // simulate work
                    System.out.println("Worker " + workerId + " done");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown(); // decrement count
                }
            });
        }

        System.out.println("Main thread waiting for all workers...");
        latch.await(); // blocks until count reaches 0
        System.out.println("All workers done! Main thread continues.");
        executor.shutdown();
    }

    // =========================================================
    // CONCEPT 8 — ExecutorService & Future
    // Don't create raw threads — use thread pools
    // =========================================================
    static void executorDemo() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Callable returns a result, Runnable does not
        Callable<Integer> task = () -> {
            Thread.sleep(100);
            return 42;
        };

        Future<Integer> future = executor.submit(task);

        // Do other work here while task runs in background...
        System.out.println("Task submitted, doing other work...");

        Integer result = future.get(); // blocks until result ready
        System.out.println("Task result: " + result);

        executor.shutdown(); // always shutdown!
    }

    // =========================================================
    // CONCEPT 9 — wait() vs sleep() — most asked difference
    // =========================================================
    // wait()  → releases the lock, must be in synchronized block
    //           woken by notify() / notifyAll() or timeout
    //
    // sleep() → does NOT release the lock
    //           woken by timeout or interrupt
    //           can be called anywhere (no sync needed)
    //
    // Both throw InterruptedException


    public static void main(String[] args) throws Exception {

        System.out.println("=======================================================");
        System.out.println("CONCEPT 1: Race Condition — count++ is NOT atomic");
        System.out.println("=======================================================");
        int threadCount = 1000;
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                unsafeCount++;              // NOT thread-safe
                atomicCount.incrementAndGet(); // thread-safe
                incrementSynchronized();        // thread-safe
            });
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join(); // wait for all threads

        System.out.println("Expected         : " + threadCount);
        System.out.println("unsafeCount      : " + unsafeCount);       // likely < 1000 (WRONG)
        System.out.println("atomicCount      : " + atomicCount.get()); // always 1000 (CORRECT)
        System.out.println("synchronizedCount: " + synchronizedCount); // always 1000 (CORRECT)


        System.out.println("\n=======================================================");
        System.out.println("CONCEPT 2: 3 ways to create threads");
        System.out.println("=======================================================");

        // Way 1: extend Thread
        new MyThread().start();

        // Way 2: implement Runnable (preferred — no single inheritance wasted)
        Thread t2 = new Thread(() ->
            System.out.println("Runnable lambda: " + Thread.currentThread().getName())
        );
        t2.start();

        // Way 3: ExecutorService (best for production)
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.submit(() -> System.out.println("ExecutorService: " + Thread.currentThread().getName()));
        exec.shutdown();


        System.out.println("\n=======================================================");
        System.out.println("CONCEPT 3: Producer-Consumer with wait/notify");
        System.out.println("=======================================================");
        ProducerConsumer pc = new ProducerConsumer(3);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) pc.produce(i);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    Thread.sleep(150); // consume slower than produce
                    pc.consume();
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();


        System.out.println("\n=======================================================");
        System.out.println("CONCEPT 4: Deadlock Prevention");
        System.out.println("=======================================================");
        System.out.println("(Skipping deadlock demo to avoid hanging — same order fix shown)");
        Thread s1 = new Thread(ThreadingDemo::safeThread1);
        Thread s2 = new Thread(ThreadingDemo::safeThread2);
        s1.start(); s2.start();
        s1.join();  s2.join();


        System.out.println("\n=======================================================");
        System.out.println("CONCEPT 5: ReentrantLock with tryLock");
        System.out.println("=======================================================");
        BankAccount acc1 = new BankAccount(1000);
        BankAccount acc2 = new BankAccount(500);
        acc1.transfer(acc2, 200);
        System.out.println("acc1 balance: " + acc1.getBalance()); // 800
        System.out.println("acc2 balance: " + acc2.getBalance()); // 700


        System.out.println("\n=======================================================");
        System.out.println("CONCEPT 6: volatile keyword");
        System.out.println("=======================================================");
        running = true; // reset for demo
        volatileDemo();


        System.out.println("\n=======================================================");
        System.out.println("CONCEPT 7: CountDownLatch");
        System.out.println("=======================================================");
        countDownLatchDemo();


        System.out.println("\n=======================================================");
        System.out.println("CONCEPT 8: ExecutorService & Future");
        System.out.println("=======================================================");
        executorDemo();


        System.out.println("\n=======================================================");
        System.out.println("CONCEPT 9: wait() vs sleep() — SUMMARY");
        System.out.println("=======================================================");
        System.out.println("wait()  → releases lock | needs synchronized | woken by notify()");
        System.out.println("sleep() → holds lock    | anywhere           | woken by timeout");
    }
}
