package com.github.vizaizai.scholar.infrastructure.market;

import com.github.vizaizai.scholar.infrastructure.market.constants.LimitType;

/**
 * 限制设置
 * @author liaochongwei
 * @date 2021/6/10 14:26
 */
public class Limit {
    /**
     * 类型
     */
    private final LimitType type;
    /**
     * 限制数量
     */
    private int quantity;

    private Limit(LimitType type) {
        this.type = type;
    }

    private Limit(LimitType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    public static Limit createNone() {
        return new Limit(LimitType.NONE);
    }

    public static Limit create(LimitType type, int quantity) {
        return new Limit(type, quantity);
    }

    public LimitType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }
}
