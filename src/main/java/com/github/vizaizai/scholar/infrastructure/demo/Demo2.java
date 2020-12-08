package com.github.vizaizai.scholar.infrastructure.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author liaochongwei
 * @date 2020/8/12 10:54
 */
public class Demo2 {
    public static void main(String[] args){

        CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("开始工作");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenApply(e-> e + " world")
          .thenAccept(e-> System.out.println(e + " 完成"));

        while (!cf.isDone());
    }


}
