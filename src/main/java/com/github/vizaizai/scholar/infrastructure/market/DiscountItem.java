package com.github.vizaizai.scholar.infrastructure.market;

import com.github.vizaizai.scholar.infrastructure.market.constants.ItemType;

/**
 * 折扣参与项
 * @author liaochongwei
 * @date 2021/6/9 14:32
 */
public class DiscountItem extends Item{
    /**
     * 最大可参与数量
     */
    private Integer maxQuantity;

    public static DiscountItem create(ItemType type, String id, Integer maxQuantity) {
        DiscountItem item = new DiscountItem();
        item.setId(id);
        item.setType(type);
        item.maxQuantity = maxQuantity;
        return item;
    }
    public static DiscountItem createOne(String id, Integer maxQuantity) {
        return create(ItemType.PORTION,id, maxQuantity);
    }
    public static DiscountItem createOne(String id) {
        return create(ItemType.PORTION,id, -1);
    }

    public static DiscountItem createAll() {
        return create(ItemType.ALL,null, -1);
    }
    public static DiscountItem createAll(Integer maxQuantity) {
        return create(ItemType.ALL,null, maxQuantity);
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }
}
