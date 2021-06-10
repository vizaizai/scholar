package com.github.vizaizai.scholar.infrastructure.market.constants;

/**
 * 互斥处理类型
 * @author liaochongwei
 * @date 2021/6/9 16:00
 */
public enum MutexResolveType {
    /**
     * 顺序优先
     */
    ORDER_PRIORITY,
    /**
     * 价格优先
     */
    PRICE_PRIORITY,
}
