package com.github.vizaizai.scholar.infrastructure.market;

import com.github.vizaizai.scholar.infrastructure.market.constants.ActivityType;

import java.math.BigDecimal;

/**
 * 结果项
 * @author liaochongwei
 * @date 2021/6/9 16:53
 */
public class ItemResult {
    /**
     * ID
     */
    private String id;
    /**
     * 上次价格
     */
    private BigDecimal lastPrice;
    /**
     * 本次价格
     */
    private BigDecimal thisPrice;
    /**
     * 活动类型
     */
    private ActivityType activityType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getThisPrice() {
        return thisPrice;
    }

    public void setThisPrice(BigDecimal thisPrice) {
        this.thisPrice = thisPrice;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }
}
