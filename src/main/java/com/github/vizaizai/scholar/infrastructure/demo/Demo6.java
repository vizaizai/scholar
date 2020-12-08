package com.github.vizaizai.scholar.infrastructure.demo;

import java.util.concurrent.CompletableFuture;

/**
 * @author liaochongwei
 * @date 2020/8/13 14:15
 */
public class Demo6 {
    public static void main(String[] args) {

        CompletableFuture<String> userIdFuture = getUserId("5466685299237");
        CompletableFuture<String> deptFuture = userIdFuture.thenCompose(Demo6::getDept);
        System.out.println(deptFuture.join()); // 研发部
    }


    // 获取用户ID
    static CompletableFuture<String> getUserId(String code) {
        return CompletableFuture.supplyAsync(() -> "10086");
    }

    // 获取用户所在部门信息
    static CompletableFuture<String> getDept(String userId) {
        return CompletableFuture.supplyAsync(() -> "研发部");
    }


}
