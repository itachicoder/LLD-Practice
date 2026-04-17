package com.lld.JavaConcurrency.TypesOfPools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewCached {

    public static void main(String []args){



    ExecutorService service = Executors.newCachedThreadPool();

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
