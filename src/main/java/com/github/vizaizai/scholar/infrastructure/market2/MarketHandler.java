package com.github.vizaizai.scholar.infrastructure.market2;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.vizaizai.scholar.infrastructure.market2.context.Activity;
import com.github.vizaizai.scholar.infrastructure.market2.context.MarketContext;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 营销处理器
 * @author liaochongwei
 * @date 2022/10/27 17:02
 */
@Slf4j
public class MarketHandler {
    /**
     * 活动列表
     */
    private final List<Activity> activities;
    /**
     * 活动组
     */
    private Map<Activity.Group,List<Activity>> activityGroup;
    /**
     * 活动方案列表
     */
    List<Set<Activity>> activityCombines = new ArrayList<>();
    /**
     * 排序器
     */
    Comparator<Activity> comparator1 = Comparator.comparing(e-> e.getGroup().getSort());
    Comparator<Activity> comparator2 = Comparator.comparing(Activity::getSort);

    public MarketHandler(List<Activity> activities) {
        this.activities = activities;
        init();
    }

    private void init() {
        if (activities == null || activities.size() == 1) {
            return;
        }
        // 初始组内化排序值
        for (int i = 0; i < this.activities.size(); i++) {
            this.activities.get(i).setSort(i);
        }
        // 活动分组
        this.activityGroup = activities.stream().collect(Collectors.groupingBy(Activity::getGroup));

        // 计算所有活动方案
        for (int i = 0; i < this.activities.size(); i++) {

            Set<Activity> activityCombine = new HashSet<>();
            Activity activity1 = this.activities.get(i);
            activityCombine.add(activity1);

            for (Activity activity2 : this.activities) {
                // 同一个活动跳过
                if (activity1.equals(activity2)) {
                    continue;
                }
                // 同一组活动可以同享
                if (activity1.getGroup().equals(activity2.getGroup())) {
                    activityCombine.add(activity2);
                } else {
                    // 和前面的活动集合都可同享
                    if (activity2.shareTo(activityCombine)) {
                        activityCombine.add(activity2);
                    }
                }
            }
            this.addCombine(activityCombine);
        }
    }

    /**
     * 添加执行方案
     * @param activityCombine
     */
    private void addCombine(Set<Activity> activityCombine) {
        // 判断当前方案是否存在，若存在则过滤
        boolean flag = false;
        for (Set<Activity>  item : activityCombines) {
            if (item.size() == activityCombine.size() && item.containsAll(activityCombine)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            activityCombines.add(activityCombine);
        }
    }

    public ComputeResult execute(List<Item> items) {
        ComputeResult result = new ComputeResult();
        if (items == null || items.isEmpty()) {
            return result;
        }
        // 总价
        BigDecimal totalPrice = items.stream().map(Item::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        result.setTotalPrice(totalPrice);

        // 计算所有的组合方案
        for (Set<Activity> activityCombine : this.activityCombines) {
            // 复制活动并且排序
            List<Activity> sortedActivities = activityCombine
                    .stream()
                    .map(Activity::cloneActivity)
                    .sorted(comparator1.thenComparing(comparator2))
                    .collect(Collectors.toList());
            // 克隆待计算列表，并且清除上一次的计算信息
            List<Item> tempItems = items.stream().map(e->{
                e.getDiscountDetails().clear();
                return e.clone();
            }).collect(Collectors.toList());
            // 依次计算
            for (Activity activity : sortedActivities) {
                MarketContext<Activity> marketContext = new MarketContext<>(activity.getMarketStrategy());
                marketContext.doHandle(tempItems, activity);
            }
            // 总优惠
            BigDecimal discountAmount = sortedActivities.stream().map(Activity::getDiscountAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            result.setTotalDiscountAmount(discountAmount);
            result.setFinalTotalPrice(totalPrice.subtract(discountAmount));

            Map<String, List<DiscountItem>> itemDiscountMapping = tempItems.stream()
                    .flatMap(e -> e.getDiscountDetails().stream())
                    .collect(Collectors.groupingBy(DiscountItem::getId));
            result.setItemDiscountMapping(itemDiscountMapping);

            Map<String, List<DiscountItem>> activityDiscountMapping = sortedActivities.stream()
                    .flatMap(e -> e.getDiscountItems().stream())
                    .collect(Collectors.groupingBy(DiscountItem::getActivityId));
            result.setActivityDiscountMapping(activityDiscountMapping);

            System.out.println("方案：" + JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect));

        }

        return result;
    }
}
