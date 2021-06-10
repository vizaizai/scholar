package com.github.vizaizai.scholar.infrastructure.market;

import java.math.BigDecimal;
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
    private String id;
    /**
     * 商品数量
     */
    private Integer quantity = 0;
    /**
     * 售卖价
     */
    private BigDecimal price = BigDecimal.ZERO;
    /**
     * 已购数量
     */
    private Integer bought = 0;
    /**
     * 计算结果
     */
    private List<ItemResult> results;

    public Commodity(String id, Integer quantity, BigDecimal price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public void setResults(List<ItemResult> results) {
        this.results = results;
    }

    public void setBought(int bought) {
        this.bought = bought;
    }

    public int getBought() {
        return bought;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<ItemResult> getResults() {
        return results;
    }
}
