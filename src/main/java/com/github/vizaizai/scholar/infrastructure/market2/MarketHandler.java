package com.github.vizaizai.scholar.infrastructure.market2;


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
     * 活动参与项列表
     */
    private final List<Item> items;
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

    public MarketHandler(List<Activity> activities, List<Item> items, DiscountOption discountOption) {
        this.activities = activities;
        this.discountOption = discountOption;
        this.items = items;
        init();
    }

    private void init() {
        if (activities == null || activities.size() == 1) {
            return;
        }
        // 预处理活动列表，排除不满足条件的
        this.activities.removeIf(activity -> !activity.check(this.items));

        // 初始组内化排序值
        for (int i = 0; i < this.activities.size(); i++) {
            this.activities.get(i).setSort(i);
        }

        // 活动全排列
        for (int i = 0; i < this.activities.size(); i++) {
            Activity activity = this.activities.get(i);
            // 两两同享的活动列表
            List<Activity>  shareActivities = this.activities.stream().filter(e -> e.shareTo(activity)).collect(Collectors.toList());
            // 组合所有情况
            for (int j = 1; j <= shareActivities.size(); j++) {
                List<List<Activity>> combinations = combination(shareActivities, j);
                if (combinations != null) {
                    for (List<Activity> combination : combinations) {
                        Set<Activity> tempActivities = new HashSet<>();
                        // 当前组合是否全部两两同享
                        boolean flag = true;
                        for (Activity temp : combination) {
                            if (!tempActivities.isEmpty() && !temp.allShareTo(tempActivities)) {
                                flag = false;
                                break;
                            }
                            tempActivities.add(temp);
                        }
                        if (flag) {
                            tempActivities.add(activity);
                            this.addPlan(tempActivities);
                        }
                    }
                }

            }
        }

        System.out.println("====================活动规划-开始=============");
        for (int i = 0; i < activityPlans.size(); i++) {
            Set<Activity> activitySet = activityPlans.get(i);
            List<Activity> sortedActivities = activitySet
                    .stream()
                    .sorted(comparator1.thenComparing(comparator2))
                    .collect(Collectors.toList());
            System.out.print("执行规划" +(i + 1) + ":");
            for (Activity activity : sortedActivities) {
                System.out.print(activity.getId() + "--->");
            }
            System.out.println("");
        }
        System.out.println("====================活动规划-结束=============");
    }

    /**
     * 添加活动规划
     * @param activityPlan
     */
    private void addPlan(Set<Activity> activityPlan) {
        // 判断是否包含必要活动
        List<String> must = this.discountOption.getMust();
        if (must != null && !must.isEmpty()) {
            Set<String> ids = activityPlan.stream().map(Activity::getId).collect(Collectors.toSet());
            if (!ids.containsAll(must)) {
                return;
            }
        }

        boolean flag = false;
        Iterator<Set<Activity>> iterator = activityPlans.iterator();
        while (iterator.hasNext()) {
            Set<Activity> item = iterator.next();
            // 当前方案已存在
            if (item.containsAll(activityPlan)) {
                flag = true;
                break;
            }
            // 当前方案包含已存在的方案，删除已存在的
            if (activityPlan.containsAll(item)) {
                iterator.remove();
            }
        }
        if (!flag) {
            activityPlans.add(activityPlan);
        }
    }

    public ComputeResult execute() {
        ComputeResult result = new ComputeResult();
        if (items == null || items.isEmpty()) {
            return result;
        }
        // 总价
        BigDecimal totalPrice = items.stream().map(Item::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        if (this.activityPlans.isEmpty()) {
            result.setTotalPrice(totalPrice);
            result.setFinalTotalPrice(totalPrice);
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
     * @param activityPlan 执行规划
     * @param items 活动参与项
     * @param totalPrice 总价
     * @return 计算结果
     */
    private ComputeResult execute(Set<Activity> activityPlan, List<Item> items, BigDecimal totalPrice) {
        ComputeResult computeResult = new ComputeResult();
        computeResult.setTotalPrice(totalPrice);
        // 复制活动并且排序
        List<Activity> sortedActivities = activityPlan
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
        List<Activity> actualActivities = sortedActivities.stream().flatMap(e -> e.actualActivities().stream()).collect(Collectors.toList());
        // 总优惠
        BigDecimal discountAmount = activityItems.stream().map(Item::getDiscountAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        computeResult.setTotalDiscountAmount(discountAmount);
        computeResult.setFinalTotalPrice(totalPrice.subtract(discountAmount));
        computeResult.setItems(activityItems);
        computeResult.setActivities(actualActivities);

        return computeResult;
    }

    /**
     * 组合算法
     * @param input 输入列表
     * @param k 组合元素数
     * @param <E>
     * @return
     */
    public static<E> List<List<E>> combination(List<E> input,int k){
        if(input.size() < k) return null;
        List<List<E>> result=new ArrayList<>();
        combination(input,result,new ArrayList<>(),0, k, k );
        return result;
    }
    public static<E> void combination(List<E> input, List<List<E>> result, List<E> temp, int start,int end,int k){
        if(temp.size()==k) {
            result.add(temp);
            return;
        }
        for(int i = start;i < input.size() - end + 1; i++){
            List<E> copyTemp = new ArrayList<>(temp);
            copyTemp.add(input.get(i));
            combination(input,result,copyTemp,i+1,end-1,k);
        }
    }

}
