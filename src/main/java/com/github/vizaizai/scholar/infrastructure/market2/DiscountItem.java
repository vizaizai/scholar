package com.github.vizaizai.scholar.infrastructure.market2;

import java.math.BigDecimal;

/**
 * 优惠项
 * @author liaochongwei
 * @date 2022/10/24 16:49
 */
public class DiscountItem {
    /**
     * 参与项pid
     */
    private String pid;
    /**
     * 参与项标识
     */
    private String id;
    /**
     * 活动标识
     */
    private String activityId;
    /**
     * 活动标签
     */
    private String activityTag;
    /**
     * 优惠前价格
     */
    private BigDecimal prePrice;
    /**
     * 优惠后价格
     */
    private BigDecimal postPrice;
    /**
     * 分摊优惠金额
     */
    private BigDecimal discountAmount;
    /**
     * 优惠项数量
     */
    private int discountNum;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityTag() {
        return activityTag;
    }

    public void setActivityTag(String activityTag) {
        this.activityTag = activityTag;
    }

    public BigDecimal getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(BigDecimal prePrice) {
        this.prePrice = prePrice;
    }

    public BigDecimal getPostPrice() {
        return postPrice;
    }

    public void setPostPrice(BigDecimal postPrice) {
        this.postPrice = postPrice;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getDiscountNum() {
        return discountNum;
    }

    public void setDiscountNum(int discountNum) {
        this.discountNum = discountNum;
    }
}
