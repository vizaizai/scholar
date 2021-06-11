package com.github.vizaizai.scholar.infrastructure.market;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品项
 * @author liaochongwei
 * @date 2021/6/9 16:53
 */
public class Commodity {
    /**
     * ID
     */
    private final String id;
    /**
     * 商品数量
     */
    private final Integer quantity;
    /**
     * 售卖价
     */
    private final BigDecimal price;
    /**
     * 处理结果列表(按步骤)
     */
    private List<CommodityHandleResult> results;

    public Commodity(String id, Integer quantity, BigDecimal price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public List<CommodityHandleResult> getResults() {
        return results;
    }

    public void setResults(List<CommodityHandleResult> results) {
        this.results = results;
    }
    public void addResults(List<CommodityPrice> commodityPrices,Activity activity) {
       if (this.results == null) {
           this.results = new ArrayList<>();
       }
       CommodityHandleResult commodityHandleResult = new CommodityHandleResult();
       commodityHandleResult.setActivity(activity);
       commodityHandleResult.setCommodityPrices(commodityPrices);
       this.results.add(commodityHandleResult);
    }

    /**
     * 获取当前小计
     * @return 当前商品总价
     */
    public BigDecimal getCurrentSubTotal() {
        if (results != null && !results.isEmpty()) {
            // 获取最后一个活动后的处理详情
            CommodityHandleResult handleResult = results.get(results.size() - 1);
            return handleResult.getSubtotal();

        }
        // 单价 * 数量
        return this.price.multiply(BigDecimal.valueOf(this.quantity));
    }

    /**
     * 获取当前均价
     * @return 均价价
     */
    public BigDecimal getCurrentAvgPrice() {
        return this.getCurrentSubTotal().divide(BigDecimal.valueOf(this.quantity),4, RoundingMode.HALF_UP);
    }
}
