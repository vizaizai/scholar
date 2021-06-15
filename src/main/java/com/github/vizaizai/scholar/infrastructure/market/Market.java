package com.github.vizaizai.scholar.infrastructure.market;

import com.github.vizaizai.scholar.infrastructure.market.constants.MutexType;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    private final List<List<Activity>> activityGroups = new ArrayList<>();

    public Market(List<Activity> activities) {
        this.activities = activities;
        this.initGroups();
    }

    /**
     * 初始化活动组
     */
    private void initGroups() {
        // 设置执行顺序
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            if (activity.getOrder() == null) {
                activity.setOrder(i);
            }
        }
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

        // 组合情况1 禁 + 同 + 与互斥互斥
        for (Activity withMutexActivity : withMutexActivities) {
            List<Activity> group = new ArrayList<>(disActivities);
            group.addAll(shareActivities);
            group.add(withMutexActivity);
            this.addGroup(group);
        }
        // 组合情况1 禁 + 全互斥
        for (Activity allMutexActivity : allMutexActivities) {
            List<Activity> group = new ArrayList<>(disActivities);
            group.add(allMutexActivity);
            this.addGroup(group);
        }
    }

    private void addGroup(List<Activity> group) {
        if (CollectionUtils.isEmpty(group)) {
            return;
        }
        // 后排序，order由小到大
        group.sort(Comparator.comparing(Activity::getOrder));
        this.activityGroups.add(group);
    }


    /**
     * 执行营销运算
     * @param commodities
     */
    public void doMarketing(List<Commodity> commodities) {


    }
    public List<Activity> getActivities() {
        return activities;
    }

    public List<List<Activity>> getActivityGroups() {
        return activityGroups;
    }
}
