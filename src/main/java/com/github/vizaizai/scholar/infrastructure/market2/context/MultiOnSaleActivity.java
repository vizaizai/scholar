package com.github.vizaizai.scholar.infrastructure.market2.context;

import com.github.vizaizai.scholar.infrastructure.market2.Item;
import com.github.vizaizai.scholar.infrastructure.market2.context.impl.MultiOnSaleStrategy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 多打折活动（选取局部最佳）
 * @author liaochongwei
 * @date 2022/12/2 11:45
 */
public class MultiOnSaleActivity extends Activity{
    /**
     * 折扣活动列表
     */
    private final List<OnSaleActivity> activities;

    private MultiOnSaleActivity(String id, List<OnSaleActivity> activities) {
        super(id, new MultiOnSaleStrategy());
        this.activities = activities;
    }

    public static MultiOnSaleActivity create(List<OnSaleActivity> activities) {
        if (activities == null || activities.isEmpty()) {
            throw new IllegalArgumentException("多打折活动参数错误~");
        }
        String id = activities.stream().map(Activity::getId).reduce((e1, e2) -> e1.concat("+" + e2)).orElse("multi");
        return new MultiOnSaleActivity(id, activities);
    }

    @Override
    public Activity newActivity() {
        return new MultiOnSaleActivity(this.getId(), this.activities.stream().map(e -> (OnSaleActivity)e.cloneActivity()).collect(Collectors.toList()));
    }

    @Override
    public List<Activity> actualActivities() {
        return activities.stream().map(e->(Activity)e).collect(Collectors.toList());
    }

    public List<OnSaleActivity> getActivities() {
        return activities;
    }

    /**
     * 获取指定参与项的打折折活动列表
     * @param item
     * @return
     */
    public List<OnSaleActivity> getItemActivities(Item item) {
        return activities.stream()
                .filter(e -> e.check(Collections.singletonList(item)))
                .collect(Collectors.toList());
    }
}
