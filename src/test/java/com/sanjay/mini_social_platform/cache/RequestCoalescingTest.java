package com.sanjay.mini_social_platform.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestCoalescingTest {
    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for(int i=0; i< 100; i++){
            executorService.submit(()-> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        executorService.shutdown();
    }
}
