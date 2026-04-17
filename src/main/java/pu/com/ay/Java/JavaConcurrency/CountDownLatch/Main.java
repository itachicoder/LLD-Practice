package com.lld.JavaConcurrency.CountDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String []args) throws InterruptedException{
        ExecutorService execed = Executors.newFixedThreadPool(5);
        CountDownLatch count = new CountDownLatch(3);

        execed.submit(new Task(count));
        execed.submit(new Task(count));
        execed.submit(new Task(count));

        count.await();

        System.out.println("completed ");






    }
    public static class Task implements Runnable{
        private CountDownLatch latch;
        public Task(CountDownLatch latch){
            this.latch = latch;
        }
        @Override
        public void run(){

            latch.countDown();
        }
    }
    
}
