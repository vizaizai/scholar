package com.github.vizaizai.scholar.infrastructure.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author liaochongwei
 * @date 2020/8/12 16:41
 */
public class Demo3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.complete("hello");
        System.out.println(completableFuture.get());


    }
}
