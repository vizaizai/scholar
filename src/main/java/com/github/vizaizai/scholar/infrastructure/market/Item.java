package com.github.vizaizai.scholar.infrastructure.market;

import com.github.vizaizai.scholar.infrastructure.market.constants.ItemType;

/**
 * 活动参与项
 * @author liaochongwei
 * @date 2021/6/9 14:32
 */
public class Item {
    /**
     * 参与项类型
     */
    private ItemType type;
    /**
     * item标识
     */
    private String id;
    /**
     * 最大数量限制(针对折扣)
     */
    private Integer maxQuantity;


    public static Item create(ItemType type, String id, Integer maxQuantity) {
        Item item = new Item();
        item.id = id;
        item.type = type;
        item.maxQuantity = maxQuantity;
        return item;
    }
    public static Item createOne(String id, Integer maxQuantity) {
        return create(ItemType.PORTION,id, maxQuantity);
    }
    public static Item createOne(String id) {
        return create(ItemType.PORTION,id, -1);
    }

    public static Item createAll() {
        return create(ItemType.ALL,null, -1);
    }
    public static Item createAll(Integer maxQuantity) {
        return create(ItemType.ALL,null, maxQuantity);
    }

    public ItemType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }
}
