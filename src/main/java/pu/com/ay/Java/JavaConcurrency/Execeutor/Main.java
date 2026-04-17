package com.lld.JavaConcurrency.Execeutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String []args){



    ExecutorService service = Executors.newFixedThreadPool(4);

    for(int i =0;i<100;i++){
        service.execute(new Task());
    }
}

public static class Task implements Runnable{
    public void run(){
        System.out.println("Thread Name: " + Thread.currentThread().getName());
    }
}
    
}
