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


    public static Item create(ItemType type, String id) {
        Item item = new Item();
        item.id = id;
        item.type = type;
        return item;
    }
    public static Item createOne(String id) {
        return create(ItemType.PORTION,id);
    }

    public static Item createAll() {
        return create(ItemType.ALL,null);
    }

    public ItemType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }
}
