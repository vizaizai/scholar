package com.github.vizaizai.scholar.infrastructure.market2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动参与项
 * @author liaochongwei
 * @date 2022/10/24 16:49
 */
public class Item implements Cloneable{
    /**
     * 标识
     */
    private final String id;
    /**
     * 参与项单价
     */
    private final BigDecimal price;
    /**
     * 参与项数量
     */
    private final int num;
    /**
     * 附加价
     */
    private BigDecimal additionPrice = BigDecimal.ZERO;
    /**
     * 总价
     */
    private BigDecimal totalPrice = null;

    /**
     * 优惠明细列表
     */
    private final List<DiscountItem> discountDetails = new ArrayList<>();

    public Item(String id, BigDecimal price, int num) {
        this.id = id;
        this.price = price;
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getNum() {
        return num;
    }

    public String getId() {
        return id;
    }

    public void setAdditionPrice(BigDecimal additionPrice) {
        this.additionPrice = additionPrice;
    }

    public BigDecimal getTotalPrice() {
        if (this.totalPrice == null) {
            this.initTotalPrice();
        }
        return totalPrice;
    }

    public BigDecimal getAdditionPrice() {
        return additionPrice;
    }
    /**
     * 初始化总价
     */
    public void initTotalPrice() {
        this.totalPrice = this.price.multiply(BigDecimal.valueOf(this.num).add(this.additionPrice));
    }

    /**
     * 添加优惠明细
     * @param discountItem
     */
    public void addDiscountDetail(DiscountItem discountItem) {
        this.discountDetails.add(discountItem);
    }

    /**
     * 获取优惠明细列表
     * @return
     */
    public List<DiscountItem> getDiscountDetails() {
        return discountDetails;
    }

    /**
     * 获取最后一次优惠明细
     * @return
     */
    private DiscountItem getLastDiscountDetail() {
        int size = this.discountDetails.size();
        if (size > 0) {
            return this.discountDetails.get(size - 1);
        }
        return null;
    }

    /**
     * 获取优惠后金额
     * @return
     */
    public BigDecimal getRealPrice() {
        DiscountItem lastDiscountDetail = this.getLastDiscountDetail();
        if (lastDiscountDetail != null) {
            return lastDiscountDetail.getPostPrice();
        }
        return this.getTotalPrice();
    }


    @Override
    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
