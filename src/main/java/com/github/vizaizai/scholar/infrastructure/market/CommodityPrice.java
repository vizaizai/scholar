package com.github.vizaizai.scholar.infrastructure.market;

import java.math.BigDecimal;

/**
 * 商品价格
 * @author liaochongwei
 * @date 2021/6/9 16:53
 */
public class CommodityPrice {
    /**
     * ID
     */
    private String itemId;
    /**
     * 上一步价格
     */
    private BigDecimal prePrice;
    /**
     * 当前价格
     */
    private BigDecimal currentPrice;
    /**
     * 活动
     */
    private Activity activity;


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(BigDecimal prePrice) {
        this.prePrice = prePrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
