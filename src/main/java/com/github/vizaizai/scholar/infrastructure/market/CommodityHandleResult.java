package com.github.vizaizai.scholar.infrastructure.market;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品处理结果
 * @author liaochongwei
 * @date 2021/6/11 17:05
 */
public class CommodityHandleResult {
    /**
     * 活动
     */
    private Activity activity;
    /**
     * 商品价格列表
     */
    private List<CommodityPrice> commodityPrices;
    /**
     * 商品小计
     */
    private BigDecimal subtotal;
    /**
     * 已减金额
     */
    private BigDecimal reducePrice;

    public CommodityHandleResult(Activity activity, BigDecimal subtotal, BigDecimal reducePrice) {
        this.activity = activity;
        this.subtotal = subtotal;
        this.reducePrice = reducePrice;
    }

    public CommodityHandleResult(Activity activity, List<CommodityPrice> commodityPrices) {
        this.activity = activity;
        this.setCommodityPrices(commodityPrices);
    }

    public Activity getActivity() {
        return activity;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<CommodityPrice> getCommodityPrices() {
        return commodityPrices;
    }

    public void setCommodityPrices(List<CommodityPrice> commodityPrices) {
        this.commodityPrices = commodityPrices;
        if (commodityPrices != null) {
            this.subtotal = commodityPrices.stream().map(CommodityPrice::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            this.reducePrice = commodityPrices.stream().map(e-> e.getPrePrice().subtract(e.getPrice())).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        }
    }

    public BigDecimal getSubtotal() {
        return subtotal == null ? BigDecimal.ZERO : subtotal;
    }

    public BigDecimal getReducePrice() {
        return reducePrice == null ? BigDecimal.ZERO : reducePrice;
    }
}
