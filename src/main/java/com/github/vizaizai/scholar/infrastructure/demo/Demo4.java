package com.github.vizaizai.scholar.infrastructure.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author liaochongwei
 * @date 2020/8/12 16:49
 */
public class Demo4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture<Void> hello = CompletableFuture.runAsync(() -> System.out.println("运行在一个单独的线程当中"));
//        hello.get();

//        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "hello");
//        System.out.println(completableFuture.get());

//          CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> "hello")
//                                                                       .thenAccept(System.out::println);
//          System.out.println(completableFuture.get());

        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
                                                                             sleep3s();
                                                                             return "hello";
                                                                          })
                                                                        .thenApply(e -> e + " world")
                                                                        .thenAccept(System.out::println);

        completableFuture.join();

    }

    private static void sleep3s() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
