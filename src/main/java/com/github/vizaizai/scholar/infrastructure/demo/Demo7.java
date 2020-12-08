package com.github.vizaizai.scholar.infrastructure.demo;

import java.util.concurrent.CompletableFuture;

/**
 * @author liaochongwei
 * @date 2020/8/13 14:42
 */
public class Demo7 {
    public static void main(String[] args) {

        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(() -> "hello");
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(() -> "world");

        helloFuture.thenCombine(worldFuture, (e1, e2)-> e1.toUpperCase() +" "+ e2.toUpperCase() + "!")
                   .thenAccept(System.out::println)
                   .join(); //HELLO WORLD!


    }
}
