package com.github.vizaizai.scholar.infrastructure.market;

import com.github.vizaizai.scholar.infrastructure.market.constants.ActivityType;
import com.github.vizaizai.scholar.infrastructure.market.context.MarketContext;
import com.github.vizaizai.scholar.infrastructure.market.context.impl.DiscountStrategy;
import com.github.vizaizai.scholar.infrastructure.market.context.impl.FullReductionStrategy;

import java.math.BigDecimal;
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
    }

    /**
     * 初始化活动组
     */
    private void initActivityGroup(List<Commodity> commodities) {
        if (Utils.isEmpty(activities)) {
            return;
        }
        // 设置执行顺序
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            if (activity.getOrder() == null) {
                activity.setOrder(i);
            }
        }
        this.commodities = commodities;

        for (Activity activity1 : activities) {
            // 活动组成员
            Set<Activity> activityGroup = new TreeSet<>();
            activityGroup.add(activity1);
            for (Activity activity2 : activities) {
                // 同一个活动则跳过
                if (activity1.equals(activity2)) {
                    continue;
                }
                // 当前活动和前面的所有活动都同享
                if (activity2.shareTo(activityGroup)) {
                    activityGroup.add(activity2);
                }else {
                    // 当前活动与前面活动互斥的，如果商品列表都没有交集，则也可以同享
                    // 与当前活动互斥的活动
                    Set<Activity> mutexActivities = activity2.getMutexActivities(activityGroup);
                    // 当前活动商品集合
                    Set<Commodity> activity2CommoditySet = new HashSet<>(activity2.getActivityCommodities(commodities));
                    if (Utils.isNotEmpty(activity2CommoditySet)) {
                        // 默认不存在交集
                        boolean intersect = false;
                        for (Activity mutexActivity : mutexActivities) {
                            Set<Commodity> mutexActivityCommoditySet =  new HashSet<>(mutexActivity.getActivityCommodities(commodities));
                            mutexActivityCommoditySet.retainAll(activity2CommoditySet);
                            // 存在交集
                            if (Utils.isNotEmpty(mutexActivityCommoditySet)) {
                                intersect = true;
                                break;
                            }
                        }
                        // 不存在交集，则可以同享
                        if (!intersect) {
                            activityGroup.add(activity2);
                        }
                    }

                }
            }
            // 添加到活动组列表中
            this.addGroup(activityGroup);

        }
    }


    private void addGroup(Set<Activity> group) {
        if (Utils.isEmpty(group)) {
            return;
        }
        Set<Activity> groupTemp = new TreeSet<>(group);
        // 默认不重复
        boolean repeat = false;
        // 判断重复的活动组
        for (Set<Activity> activityGroup : activityGroups) {
            groupTemp.addAll(activityGroup);
            // 如何取并集后的元素数量等于并集前的元素数量，则两个集合完全相同
            if (group.size() == groupTemp.size()) {
                repeat = true;
            }
        }
        if (!repeat) {
            this.activityGroups.add(group);
        }

    }


    /**
     * 执行营销运算
     * @param commodities
     */
    @SuppressWarnings(value = "all")
    public BigDecimal doMarketing(List<Commodity> commodities) {
        long s = System.currentTimeMillis();
        if (Utils.isEmpty(commodities)) {
            return BigDecimal.ZERO;
        }
        this.initActivityGroup(commodities);
        List<Commodity> handleCommodities = commodities.stream()
                .map(e -> new Commodity(e.getId(), e.getQuantity(), e.getPrice()))
                .collect(Collectors.toList());

        BigDecimal marketPrice = null;
        for (Set<Activity> activityGroup : activityGroups) {
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
                    List<CommodityHandleResult> results = commodity1.getResults();
                    if (Utils.isNotEmpty(results)) {
                        commodity2.setResults(new ArrayList<>(results));
                    }

                }
            }
            this.clearResults(handleCommodities);
        }
        // 无任何活动,返回原价总额
        if (marketPrice == null) {
            marketPrice = this.getOriginalPrice();
        }
        System.out.println("计算耗时：" + (System.currentTimeMillis() - s) +"ms");
        return marketPrice;
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


    /**
     * 原价总额
     * @return BigDecimal
     */
    public BigDecimal getOriginalPrice() {
        BigDecimal price = BigDecimal.ZERO;
        for (Commodity commodity : this.commodities) {
            price = price.add(commodity.getOriginalSubTotal());
        }
        return price;
    }

    public BigDecimal getReducePrice() {
        return this.getReducePrice(null);
    }

    /**
     * 获取优惠金额
     * @param activityType
     * @return BigDecimal
     */
    public BigDecimal getReducePrice(ActivityType activityType) {
        BigDecimal reducePrice = BigDecimal.ZERO;
        for (Commodity commodity : this.commodities) {
            List<CommodityHandleResult> results = commodity.getResults();
            if (Utils.isEmpty(results)) {
                continue;
            }
            // 不区分活动类型
            if (activityType == null) {
                reducePrice = reducePrice.add(commodity.getReducePrice());
            }else {
                for (CommodityHandleResult result : results) {
                    if (activityType.equals(result.getActivity().getType())) {
                        reducePrice = reducePrice.add(result.getReducePrice());
                    }
                }
            }
        }
        return reducePrice;
    }
}
