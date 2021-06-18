package com.github.vizaizai.scholar.infrastructure.market;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.vizaizai.scholar.infrastructure.market.constants.ItemType;
import com.github.vizaizai.scholar.infrastructure.market.constants.MutexType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 活动
 * @author liaochongwei
 * @date 2021/6/9 17:15
 */
public class Activity implements Comparable<Activity>{
    /**
     * 活动标识
     */
    private String id;
    /**
     * 互斥类型
     */
    @JSONField(serialize= false)
    private MutexType mutexType;
    /**
     * 活动参与项列表
     */
    @JSONField(serialize= false)
    private List<Item> items;
    /**
     * 活动参与项映射
     */
    @JSONField(serialize= false)
    private Map<String, Item> itemsMap = Collections.emptyMap();
    /**
     * 参与项类型
     */
    @JSONField(serialize= false)
    private ItemType itemType;
    /**
     * 顺序
     */
    @JSONField(serialize= false)
    private Integer order;


    public Activity() {
        this.mutexType = MutexType.DISABLED;
        this.setItems(Collections.singletonList(Item.createAll()));
    }

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

    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * 获取活动商品列表
     * @param commodities 原商品列表
     * @return 活动商品列表
     */
    public List<Commodity> getActivityCommodities(List<Commodity> commodities) {
        List<Commodity> commodityList = commodities
                .stream().filter(Commodity::isGtZero)
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
     * 判断该商品是否可参与该活动
     * @param commodity
     * @return true or false
     */
    public boolean isActivityCommodity(Commodity commodity) {
       if (!commodity.isGtZero()) {
           return false;
       }
        if (this.itemType.equals(ItemType.ALL)) {
            return true;
        }
        return itemsMap.containsKey(commodity.getId());
    }

    /**
     * 获取商品可参与的数量
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

    public Integer getOrder() {
        return order;
    }


    @Override
    public int compareTo(Activity o) {
        return this.order - o.order;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Activity activity = (Activity) object;
        return Objects.equals(id, activity.id) &&
                mutexType == activity.mutexType &&
                Objects.equals(items, activity.items) &&
                Objects.equals(itemsMap, activity.itemsMap) &&
                itemType == activity.itemType &&
                Objects.equals(order, activity.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mutexType, items, itemsMap, itemType, order);
    }

    /**
     * 两两是否能同享
     */
    public boolean shareTo(Set<Activity> activities) {
        Activity activity = activities.stream()
                .filter(e -> !e.mutexType.shareTo(this.mutexType))
                .findAny()
                .orElse(null);
        // 当存在一个不能同享的，则返回false
        return activity == null;
    }

}
