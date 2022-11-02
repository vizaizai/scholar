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
     * 优惠选择
     */
    private final DiscountOption discountOption;
    /**
     * 活动规划列表
     */
    List<Set<Activity>> activityPlans = new ArrayList<>();
    /**
     * 排序器
     */
    Comparator<Activity> comparator1 = Comparator.comparing(e-> e.getGroup().getSort());
    Comparator<Activity> comparator2 = Comparator.comparing(Activity::getSort);

    public MarketHandler(List<Activity> activities, DiscountOption discountOption) {
        this.activities = activities;
        this.discountOption = discountOption;
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

        // 组合执行方案列表
        for (int i = 0; i < this.activities.size(); i++) {

            Set<Activity> activityPlan = new HashSet<>();
            Activity activity1 = this.activities.get(i);
            activityPlan.add(activity1);

            for (Activity activity2 : this.activities) {
                // 同一个活动跳过
                if (activity1.equals(activity2)) {
                    continue;
                }
                // 同一组活动可以同享
                if (activity1.getGroup().equals(activity2.getGroup())) {
                    activityPlan.add(activity2);
                } else {
                    // 和前面的活动集合都可同享
                    if (activity2.shareTo(activityPlan)) {
                        activityPlan.add(activity2);
                    }
                }
            }
            this.addPlan(activityPlan);
        }
    }

    /**
     * 添加活动规划
     * @param activityPlan
     */
    private void addPlan(Set<Activity> activityPlan) {
        // 判断是否包含必要活动
        List<String> must = this.discountOption.getMust();
        if (must != null && !must.isEmpty()) {
            List<String> ids = activityPlan.stream().map(Activity::getId).collect(Collectors.toList());
            if (!ids.containsAll(must)) {
                return;
            }
        }

        // 判断当前方案是否存在，若存在则过滤
        boolean flag = false;
        for (Set<Activity>  item : activityPlans) {
            if (item.size() == activityPlan.size() && item.containsAll(activityPlan)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            activityPlans.add(activityPlan);
        }
    }

    public ComputeResult execute(List<Item> items) {
        ComputeResult result = new ComputeResult();
        if (items == null || items.isEmpty()) {
            return result;
        }
        // 总价
        BigDecimal totalPrice = items.stream().map(Item::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        if (this.activityPlans.isEmpty()) {
            result.setTotalPrice(totalPrice);
            result.setFinalTotalPrice(totalPrice);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>活动为空");
            return result;
        }

        // 判断优惠模式
        if (this.discountOption.getMode() == DiscountOption.MODE_FIRST) {
            return this.execute(this.activityPlans.get(0),items, totalPrice);
        }else if (this.discountOption.getMode() == DiscountOption.MODE_LAST) {
            return this.execute(this.activityPlans.get(this.activityPlans.size() - 1),items, totalPrice);
        }else {
            // 计算最佳优惠
            List<ComputeResult> results = new ArrayList<>();
            for (Set<Activity> activityPlan : this.activityPlans) {
                results.add(this.execute(activityPlan,items, totalPrice));
            }
           return results.stream().max(Comparator.comparing(ComputeResult::getTotalDiscountAmount)).orElse(result);
        }
    }

    /**
     * 执行计算营销
     * @param activitySolution 方案
     * @param items 活动参与项
     * @param totalPrice 总价
     * @return 计算结果
     */
    private ComputeResult execute(Set<Activity> activitySolution, List<Item> items, BigDecimal totalPrice) {
        ComputeResult computeResult = new ComputeResult();
        computeResult.setTotalPrice(totalPrice);
        // 复制活动并且排序
        List<Activity> sortedActivities = activitySolution
                .stream()
                .map(Activity::cloneActivity)
                .sorted(comparator1.thenComparing(comparator2))
                .collect(Collectors.toList());
        // 克隆待计算列表，并且清除上一次的计算信息
        List<Item> activityItems = items.stream().map(Item::cloneItem).collect(Collectors.toList());
        // 依次计算
        for (Activity activity : sortedActivities) {
            MarketContext<Activity> marketContext = new MarketContext<>(activity.getMarketStrategy());
            marketContext.doHandle(activityItems, activity);
        }
        // 总优惠
        BigDecimal discountAmount = sortedActivities.stream().map(Activity::getDiscountAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        computeResult.setTotalDiscountAmount(discountAmount);
        computeResult.setFinalTotalPrice(totalPrice.subtract(discountAmount));
        computeResult.setItems(activityItems);
        computeResult.setActivities(sortedActivities);
        System.out.println("方案：" + JSON.toJSONString(computeResult, SerializerFeature.DisableCircularReferenceDetect));

        return computeResult;
    }

}
