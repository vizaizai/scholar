package com.github.vizaizai.scholar.infrastructure.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author liaochongwei
 * @date 2020/8/13 14:52
 */
public class Demo8 {
    public static void main(String[] args) {

        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            sleep(3);
            System.out.println("task1-完成");
            return "task1";
        });
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            sleep(1);
            System.out.println("task2-完成");
            return "task3";
        });
        CompletableFuture<String> task3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("task3-完成");
            return "task3";
        });

        CompletableFuture.allOf(task1, task2, task3)
                         .thenAccept(e-> System.out.println("全部完成"))
                         .join();
    }


    private static void sleep(int s) {
        try {
            TimeUnit.SECONDS.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
