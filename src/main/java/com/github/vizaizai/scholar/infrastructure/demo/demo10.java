package com.github.vizaizai.scholar.infrastructure.demo;

import java.util.concurrent.CompletableFuture;

/**
 * @author liaochongwei
 * @date 2020/8/13 15:16
 */
public class demo10 {
    public static void main(String[] args) {

        int i = 1;
        CompletableFuture<String> exceptionally = CompletableFuture.supplyAsync(() -> {
            if (i == 1)
                throw new RuntimeException("异常啦~");
            return "hello";

        }).thenApply(e -> e.toUpperCase())
          .handle((res, ex)-> {
              return res;
          });

        System.out.println(exceptionally.join()); // null
    }
}
