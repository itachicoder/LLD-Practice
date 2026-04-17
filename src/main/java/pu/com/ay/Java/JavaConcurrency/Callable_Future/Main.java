package com.lld.JavaConcurrency.Callable_Future;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {

        ExecutorService service = Executors.newFixedThreadPool(5);
        List<Future<Integer>> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Future<Integer> temp = service.submit(new Tasks());
            result.add(temp);
        }

        for (Future<Integer> future : result) {
            System.out.println(future.get());
        }

        service.shutdown();
    }
    static class Tasks implements Callable<Integer> {

        public Integer call() throws InterruptedException{
            Thread.sleep(300);
            return new Random().nextInt();
        }


    }
    
}
