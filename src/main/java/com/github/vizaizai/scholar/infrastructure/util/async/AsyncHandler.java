package com.github.vizaizai.scholar.infrastructure.util.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * 异步任务处理器
 * @author liaochongwei
 * @date 2020/12/2 15:51
 */
@Slf4j
public class AsyncHandler<T> {
    private int maxThreadSize  = 10; // 最大线程数
    private Executor executor; // 线程池
    private final List<T> data; // 列表数据


    public AsyncHandler(List<T> data) {
        this.data = data;
    }

    public static<T> AsyncHandler<T> ofData(List<T> data) {
        return new AsyncHandler<>(data);
    }

    @SuppressWarnings("all")
    public void execute(Consumer<T> handler) {
        if (handler == null || CollectionUtils.isEmpty(this.data)) {
            return;
        }
        int taskCount = this.data.size();
        // 平均每个线程执行的任务数
        int threadTaskCount = taskCount / this.maxThreadSize;
        // 剩余任务数
        int remainTaskCount = taskCount % this.maxThreadSize;

        List<TaskHandler<T>> handlers = new ArrayList<>();

        // 分解任务
        for (int i = 0; i < this.maxThreadSize; i++) {
            List<T> childTaskData = new ArrayList<>();
            for (int j = 0; j < threadTaskCount; j++) {
                childTaskData.add(this.data.get(i + j));
            }
            handlers.add(new TaskHandler<>(childTaskData, handler));
        }
        // 添加剩余任务(默认非给前几个线程)
        for (int i = 0; i < remainTaskCount; i++) {
            handlers.get(i).getData().add(this.data.get(threadTaskCount * maxThreadSize + i));
        }

        CompletableFuture<Void>[] futureList = handlers.stream().filter(run -> CollectionUtils.isNotEmpty(run.getData()))
                .map(run -> this.executor == null ? CompletableFuture.runAsync(run) :  CompletableFuture.runAsync(run, executor))
                .toArray(CompletableFuture[]::new);

        log.info("开始执行分解任务,每个线程执行【{}】个任务，前【{}】个线程多执行1个任务",threadTaskCount, remainTaskCount);
        long start = System.currentTimeMillis();
        CompletableFuture.allOf(futureList)
                .thenAccept(e->{
                    log.info("任务全部执行完毕,耗时{}ms",System.currentTimeMillis() - start);
                })
                .exceptionally(ex->{
                    log.error("执行异常:{}",ex.getMessage());
                    return null;
                })
                .join();

    }

    public AsyncHandler<T> setMaxThreadSize(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
        return this;
    }

    public AsyncHandler<T> setExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    public static class TaskHandler<E> implements Runnable {
        private final List<E> data;
        private final Consumer<E> handler;
        public TaskHandler(List<E> data, Consumer<E> handler) {
            this.data = data;
            this.handler = handler;
        }

        @Override
        public void run() {
            if (CollectionUtils.isEmpty(data)) {
                return;
            }
            for (E datum : data) {
                handler.accept(datum);
            }
        }

        public List<E> getData() {
            return data;
        }
    }
}
