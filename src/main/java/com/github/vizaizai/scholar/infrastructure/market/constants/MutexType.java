package com.github.vizaizai.scholar.infrastructure.market.constants;

/**
 * 互斥类型
 * @author liaochongwei
 * @date 2021/6/9 16:00
 */
public enum MutexType {
    /**
     * 不互斥
     */
    NONE,
    /**
     * 全部互斥
     */
    ALL,
    /**
     * 与互斥活动互斥
     */
    WITH_MUTEX;
}
