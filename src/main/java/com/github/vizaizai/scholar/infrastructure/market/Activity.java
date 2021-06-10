package com.github.vizaizai.scholar.infrastructure.market;

import com.github.vizaizai.scholar.infrastructure.market.constants.ItemType;
import com.github.vizaizai.scholar.infrastructure.market.constants.LimitType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 活动
 * @author liaochongwei
 * @date 2021/6/9 17:15
 */
public class Activity {
    /**
     * 互斥属性
     */
    private Mutex mutex;
    /**
     * 活动参与项列表
     */
    private List<Item> items;
    /**
     * 活动参与项映射
     */
    private Map<String, Item> itemsMap = Collections.emptyMap();
    /**
     * 参与项类型
     */
    private ItemType itemType;

    public Mutex getMutex() {
        return mutex;
    }

    public void setMutex(Mutex mutex) {
        this.mutex = mutex;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        if (this.items == null || this.items.isEmpty()) {
            this.itemType = ItemType.PORTION;
            return;
        }
        this.itemType = this.items.get(0).getType();

        if (this.itemType.equals(ItemType.PORTION)) {
            this.itemsMap = this.items.stream().collect(Collectors.toMap(Item::getId, e -> e));
        }

    }

    /**
     * 获取活动商品列表
     * @param commodities 原商品列表
     * @return 活动商品列表
     */
    public List<Commodity> getActivityCommodities(List<Commodity> commodities) {
        if (this.itemType.equals(ItemType.ALL)) {
            return new ArrayList<>(commodities);
        }
        List<Commodity> list = new ArrayList<>();
        for (Commodity commodity : commodities) {
            Item item = itemsMap.get(commodity.getId());
            if (item != null) {
                list.add(commodity);
            }
        }
        return list;


    }

    /**
     * 获取商品限制
     * @param commodity commodity
     * @return Limit
     */
    public Limit getLimit(Commodity commodity) {
        if (this.itemType.equals(ItemType.ALL)) {
            return this.items.get(0).getLimit();
        }
        Item item = itemsMap.get(commodity.getId());
        if (item != null) {
            return item.getLimit();
        }
        return Limit.create(LimitType.ORDER,0);
    }

    public ItemType getItemType() {
        return itemType;
    }

}
