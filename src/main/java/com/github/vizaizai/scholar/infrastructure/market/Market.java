package com.github.vizaizai.scholar.infrastructure.market;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.vizaizai.scholar.infrastructure.market.constants.MutexType;
import com.github.vizaizai.scholar.infrastructure.market.context.MarketContext;
import com.github.vizaizai.scholar.infrastructure.market.context.impl.DiscountStrategy;
import com.github.vizaizai.scholar.infrastructure.market.context.impl.FullReductionStrategy;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 营销对象
 * @author liaochongwei
 * @date 2021/6/11 11:30
 */
public class Market {
    /**
     * 活动列表
     */
    private final List<Activity> activities;
    /**
     * 活动列表组
     */
    private final List<Set<Activity>> activityGroups = new ArrayList<>();
    /**
     * 参与计算的商品列表
     */
    private List<Commodity> commodities;

    public Market(List<Activity> activities) {
        this.activities = activities;
        this.initGroups();
    }

    /**
     * 这只商品活动列表
     * @param commodities
     */
    private void setCommodityActivities(List<Commodity> commodities) {
        for (Commodity commodity : commodities) {
            List<Activity> commodityActivities = new ArrayList<>();
            for (Activity activity : activities) {
                if (activity.isActivityCommodity(commodity)) {
                    commodityActivities.add(activity);
                }
            }
        }
    }

    /**
     * 先假设所有商品参加所有活动
     * 初始化活动组
     */
    private void initGroups() {
        if (CollectionUtils.isEmpty(activities)) {
            return;
        }
        // 设置执行顺序
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            if (activity.getOrder() == null) {
                activity.setOrder(i);
            }
        }
        List<Set<Activity>> activityGroups1 = new ArrayList<>();

        for (Activity activity1 : activities) {
            Set<Activity> groupItem = new TreeSet<>();
            groupItem.add(activity1);
            for (Activity activity2 : activities) {
                // 同一个活动则跳过
                if (activity1.equals(activity2)) {
                    continue;
                }
                // 此活动和前面的所有活动都同享
                if (activity2.shareTo(groupItem)) {
                    groupItem.add(activity2);
                }else {
                    // 此活动与前面活动互斥的，如果商品列表都没有交集，则也可以同享
                }
            }
            // 判断重复的活动组

            activityGroups1.add(groupItem);
        }

        System.out.println(JSON.toJSONString(activityGroups1, SerializerFeature.DisableCircularReferenceDetect));

        // 筛选禁用互斥属性的活动
        List<Activity> disActivities = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.getMutexType().equals(MutexType.DISABLED)) {
                disActivities.add(activity);
            }
        }
        // 筛选同享活动
        List<Activity> shareActivities = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.getMutexType().equals(MutexType.NONE)) {
                shareActivities.add(activity);
            }
        }

        // 筛选全部互斥
        List<Activity> allMutexActivities = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.getMutexType().equals(MutexType.ALL)) {
                allMutexActivities.add(activity);
            }
        }

        // 筛选与互斥活动互斥
        List<Activity> withMutexActivities = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.getMutexType().equals(MutexType.WITH_MUTEX)) {
                withMutexActivities.add(activity);
            }
        }


        /*==================================================开始组合========================================*/
        // 同享(0-空) 与互斥活动互斥(0-空) 全互斥(0-空)
        // 0 0 0
        if (shareActivities.isEmpty() && withMutexActivities.isEmpty() && allMutexActivities.isEmpty()) {
            this.addGroup(new TreeSet<>(disActivities));
        }
        // 0 0 1
        if (shareActivities.isEmpty() && withMutexActivities.isEmpty() && !allMutexActivities.isEmpty()) {
            Set<Activity> group = new TreeSet<>(disActivities);
            group.addAll(allMutexActivities);
            this.addGroup(group);
        }
        // 0 1 0
        if (shareActivities.isEmpty() && !withMutexActivities.isEmpty() && allMutexActivities.isEmpty()) {
            for (Activity activity : withMutexActivities) {
                Set<Activity> group = new TreeSet<>(disActivities);
                group.add(activity);
                this.addGroup(group);
            }
        }
        // 0 1 1
        if (shareActivities.isEmpty() && !withMutexActivities.isEmpty() && !allMutexActivities.isEmpty()) {
            for (Activity activity : withMutexActivities) {
                Set<Activity> group = new TreeSet<>(disActivities);
                group.add(activity);
                this.addGroup(group);
            }
            for (Activity activity : allMutexActivities) {
                Set<Activity> group = new TreeSet<>(disActivities);
                group.add(activity);
                this.addGroup(group);
            }
        }
        // 1 0 0
        if (!shareActivities.isEmpty() && withMutexActivities.isEmpty() && allMutexActivities.isEmpty()) {
            Set<Activity> group = new TreeSet<>(disActivities);
            group.addAll(shareActivities);
            this.addGroup(group);
        }
        // 1 0 1
        if (!shareActivities.isEmpty() && withMutexActivities.isEmpty() && !allMutexActivities.isEmpty()) {
            Set<Activity> group1 = new TreeSet<>(disActivities);
            group1.addAll(allMutexActivities);
            this.addGroup(group1);

            for (Activity activity : allMutexActivities) {
                Set<Activity> group2 = new TreeSet<>(disActivities);
                group2.add(activity);
                this.addGroup(group2);
            }
        }
        // 1 1 0
        if (!shareActivities.isEmpty() && !withMutexActivities.isEmpty() && allMutexActivities.isEmpty()) {
            for (Activity activity : withMutexActivities) {
                Set<Activity> group = new TreeSet<>(disActivities);
                group.addAll(shareActivities);
                group.add(activity);
                this.addGroup(group);
            }
        }
        // 1 1 1
        if (!shareActivities.isEmpty() && !withMutexActivities.isEmpty() && !allMutexActivities.isEmpty()) {
            for (Activity activity : withMutexActivities) {
                Set<Activity> group = new TreeSet<>(disActivities);
                group.addAll(shareActivities);
                group.add(activity);
                this.addGroup(group);
            }
            for (Activity activity : allMutexActivities) {
                Set<Activity> group = new TreeSet<>(disActivities);
                group.add(activity);
                this.addGroup(group);
            }

        }


    }

    private void addGroup(Set<Activity> group) {
        if (CollectionUtils.isEmpty(group)) {
            return;
        }
        this.activityGroups.add(group);
    }


    /**
     * 执行营销运算
     * @param commodities
     */
    @SuppressWarnings(value = "all")
    public BigDecimal doMarketing(List<Commodity> commodities) {
        if (CollectionUtils.isEmpty(commodities)) {
            return BigDecimal.ZERO;
        }

        this.commodities = commodities;
        List<Commodity> handleCommodities = commodities.stream()
                .map(e -> new Commodity(e.getId(), e.getQuantity(), e.getPrice()))
                .collect(Collectors.toList());

        BigDecimal marketPrice = null;
        for (Set<Activity> activityGroup : activityGroups) {

            // 结合实际商品列表添加营销活动
//            for (Activity activity : activities) {
//
//            }

            // 顺序执行活动
            for (Activity activity : activityGroup) {
               this.getMarketContext(activity).doHandle(handleCommodities,activity);
            }
            // 汇总金额
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (Commodity commodity : handleCommodities) {
                totalPrice = totalPrice.add(commodity.getSubTotal());
            }
            // 求最佳组合
            if (marketPrice == null || totalPrice.compareTo(marketPrice) < 0) {
                marketPrice = totalPrice;
                for (int i = 0; i < handleCommodities.size(); i++) {
                    Commodity commodity1 = handleCommodities.get(i);
                    Commodity commodity2 = this.commodities.get(i);
                    commodity2.setResults(new ArrayList<>(commodity1.getResults()));
                }
            }
            this.clearResults(handleCommodities);
        }
        // 无任何活动
        if (marketPrice == null) {
            marketPrice = BigDecimal.ZERO;
            for (Commodity commodity : handleCommodities) {
                marketPrice = marketPrice.add(commodity.getSubTotal());
            }
        }
        return marketPrice;
    }

    private void resetActivityGroup(List<Commodity> commodities) {

        Set<Activity> activitySet = new HashSet<>(activities);
        for (Set<Activity> activityGroup : activityGroups) {
            // 差集（未在活动组中）
            activitySet.removeAll(activityGroup);

            for (Activity activity : activitySet) {
                List<Commodity> activityCommodities = activity.getActivityCommodities(commodities);
                for (Commodity commodity : commodities) {

                }
            }
        }
    }
    public List<Activity> getActivities() {
        return activities;
    }

    public List<Set<Activity>> getActivityGroups() {
        return activityGroups;
    }

    public List<Commodity> getCommodities() {
        return commodities;
    }

    @SuppressWarnings(value = "all")
    private MarketContext getMarketContext(Activity activity) {
        if (activity instanceof Discount) {
            return new MarketContext(new DiscountStrategy());
        }else if (activity instanceof FullReduction) {
            return new MarketContext(new FullReductionStrategy());
        }else {
            throw new IllegalArgumentException("活动类型不支持");
        }
    }

    private void clearResults(List<Commodity> commodities) {
        commodities.forEach(Commodity::clearResults);
    }
}
