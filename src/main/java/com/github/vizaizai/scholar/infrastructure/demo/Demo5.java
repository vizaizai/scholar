package com.github.vizaizai.scholar.infrastructure.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author liaochongwei
 * @date 2020/8/13 13:55
 */
@Slf4j
public class Demo5 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<Void> comboText = CompletableFuture.supplyAsync(() -> {
            //可以注释掉做快速返回 start
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                throw new IllegalStateException(e);
//            }
            log.info("👍");
            //可以注释掉做快速返回 end
            return "赞";
        })
                .thenApplyAsync(first -> {
                    log.info("在看");
                    return first + ", 在看";
                })
                .thenApplyAsync(second -> {
                    log.info("111");
                    return  second + ", 转发";
                })
                .thenAccept(e-> log.info(e));

        log.info("三连有没有？");
//        /log.info(comboText.get());
        comboText.join();
    }
}
