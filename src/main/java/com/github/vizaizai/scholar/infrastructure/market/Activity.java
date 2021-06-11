package com.github.vizaizai.scholar.infrastructure.market;

import com.github.vizaizai.scholar.infrastructure.market.constants.ItemType;
import com.github.vizaizai.scholar.infrastructure.market.constants.MutexType;

import java.math.BigDecimal;
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
     * 活动标识
     */
    private String id;
    /**
     * 互斥类型
     */
    private MutexType mutexType;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MutexType getMutexType() {
        return mutexType;
    }

    public void setMutexType(MutexType mutexType) {
        this.mutexType = mutexType;
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
        List<Commodity> commodityList = commodities
                .stream().filter(e -> e.getQuantity() != 0 && e.getPrice().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());
        if (this.itemType.equals(ItemType.ALL)) {
            return commodityList;
        }
        List<Commodity> list = new ArrayList<>();
        for (Commodity commodity : commodityList) {
            Item item = itemsMap.get(commodity.getId());
            if (item != null) {
                list.add(commodity);
            }
        }
        return list;


    }

    /**
     * 获取商品可参与的最大数量
     * @param commodity commodity
     * @return Limit
     */
    public Integer getMaxQuantity(Commodity commodity) {
        if (this.itemType.equals(ItemType.ALL)) {
            return this.items.get(0).getMaxQuantity();
        }
        Item item = itemsMap.get(commodity.getId());
        if (item != null) {
            return item.getMaxQuantity();
        }
        return 0;
    }

    public ItemType getItemType() {
        return itemType;
    }

}
