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
    public void addResult(List<CommodityPrice> commodityPrices, Activity activity) {
       if (this.results == null) {
           this.results = new ArrayList<>();
       }
       this.results.add(new CommodityHandleResult(activity,commodityPrices));
    }

    public void addResult(BigDecimal subTotal,BigDecimal reducePrice, Activity activity) {
        if (this.results == null) {
            this.results = new ArrayList<>();
        }
        this.results.add(new CommodityHandleResult(activity,subTotal,reducePrice));
    }

    public void clearResults() {
        if (Utils.isNotEmpty(this.results)) {
            this.results.clear();
        }
    }

    // 原始小计
    public BigDecimal getOriginalSubTotal() {
        // 单价 * 数量
        return this.price.multiply(BigDecimal.valueOf(this.quantity));
    }

    /**
     * 获取小计
     * @return 当前商品小计
     */
    public BigDecimal getSubTotal() {
        if (Utils.isNotEmpty(this.results)) {
            // 获取最后一个活动后的处理详情
            CommodityHandleResult handleResult = results.get(results.size() - 1);
            return handleResult.getSubtotal();

        }
        // 原始小计
        return this.getOriginalSubTotal();
    }

    /**
     * 获取原始小计
     * @return 原始小计
     */
    public BigDecimal getReducePrice () {
        BigDecimal reduce = this.price.multiply(BigDecimal.valueOf(this.quantity)).subtract(this.getSubTotal());
        return reduce.compareTo(BigDecimal.ZERO) >= 0 ? reduce : BigDecimal.ZERO;
    }

    /**
     * 商品价格
     * @return 商品价格
     */
    public BigDecimal getCommodityPrice() {
        return this.getSubTotal().divide(BigDecimal.valueOf(this.quantity),4, RoundingMode.HALF_UP);
    }

    /**
     * 获取单商品价格
     * @param index 指定商品下标
     * @return 单价
     */
    public BigDecimal getPrice(Integer index) {
        if (Utils.isNotEmpty(this.results)) {
            // 获取最后一个活动后的处理详情
            CommodityHandleResult handleResult = results.get(results.size() - 1);
            List<CommodityPrice> commodityPrices = handleResult.getCommodityPrices();
            if (Utils.isNotEmpty(commodityPrices)) {
                return commodityPrices.get(index).getPrice();
            }
            return handleResult.getSubtotal().divide(BigDecimal.valueOf(this.quantity),4,RoundingMode.HALF_UP);

        }
        // 单价
        return this.price;
    }

    /**
     * 判断数量和单价大于0
     * @return boolean
     */
    public boolean isGtZero() {
        return this.getQuantity() > 0 && this.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }

}
