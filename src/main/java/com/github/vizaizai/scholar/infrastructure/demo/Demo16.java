package com.github.vizaizai.scholar.infrastructure.demo;

import java.util.concurrent.*;

/**
 * @author liaochongwei
 * @date 2022/2/17 15:52
 */
public class Demo16 {

    public static class Task1 implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            Thread.sleep(30000);
            return 12;
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> submit = executorService.submit(new Task1());

        Integer integer = submit.get();

        System.out.println("返回" + integer);




    }
}
