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
     * 限制属性(折扣)
     */
    private Limit limit;

    public static Item create(ItemType type, String id, Limit limit) {
        Item item = new Item();
        item.id = id;
        item.limit = limit;
        item.type = type;
        return item;
    }
    public static Item createForPortion(String id, Limit limit) {
        return create(ItemType.PORTION,id,limit);
    }

    public static Item createForAll(Limit limit) {
        return create(ItemType.ALL,null, limit);
    }

    public ItemType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Limit getLimit() {
        return limit;
    }
}
