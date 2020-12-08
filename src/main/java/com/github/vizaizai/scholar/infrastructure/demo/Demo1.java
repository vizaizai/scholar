package com.github.vizaizai.scholar.infrastructure.demo;

import java.util.concurrent.*;

/**
 * @author liaochongwei
 * @date 2020/8/12 9:36
 */
public class Demo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
        completionService.submit(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        });

        completionService.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });

        for (int i = 0; i < 2; i++) {
            System.out.println(completionService.take().get());
        }
        executorService.shutdown();

    }
}
