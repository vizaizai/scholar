package com.github.vizaizai.scholar.infrastructure.market;

import com.github.vizaizai.scholar.infrastructure.market.constants.ActivityType;
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
    private MutexType mutexType;
    /**
     * 活动参与项列表
     */
    private List<Item> items;
    /**
     * 参与项映射
     */
    private Map<String, Item> itemsMap = Collections.emptyMap();
    /**
     * 参与项类型
     */
    private ItemType itemType;
    /**
     * 顺序
     */
    private Integer order;
    /**
     * 活动类型
     */
    private ActivityType type;

    public Activity() {
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
        for (Item item : items) {
            this.preAddItem(item);
        }
        this.itemType = this.items.get(0).getType();

        if (this.itemType.equals(ItemType.PORTION)) {
            this.itemsMap = this.items.stream().collect(Collectors.toMap(Item::getId, e -> e));
        }

    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    protected void preAddItem(Item item){
        // empty
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

    public ItemType getItemType() {
        return itemType;
    }

    public Integer getOrder() {
        return order;
    }

    public Map<String, Item> getItemsMap() {
        return itemsMap;
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

    /**
     * 获取互斥列表
     * @param activities
     * @return 互斥活动
     */
    public Set<Activity> getMutexActivities(Set<Activity> activities) {
        return activities.stream()
                .filter(e -> !e.mutexType.shareTo(this.mutexType))
                .collect(Collectors.toSet());
    }

}
