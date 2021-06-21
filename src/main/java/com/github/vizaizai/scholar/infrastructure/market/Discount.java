package com.github.vizaizai.scholar.infrastructure.market;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.*;

/**
 * 折扣参数
 * @author liaochongwei
 * @date 2021/6/9 14:30
 */
public class Discount extends Activity{
    /**
     * 折扣，如7折，8折
     */
    @JSONField(serialize= false)
    private BigDecimal value;
    /**
     * 折扣组，同组择优选择
     */
    @JSONField(serialize= false)
    private List<Discount> group;


    private Discount() {
    }

    public static Discount create(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("折扣值不能为空");
        }
        if (value.compareTo(BigDecimal.ZERO) <= 0 || value.compareTo(BigDecimal.TEN) == 0) {
            throw new IllegalArgumentException("折扣值区间(0,10]");
        }
        Discount discount = new Discount();
        discount.value = value;
        return discount;
    }

    public static Discount create(List<Discount> group) {
        if (group == null) {
            throw new IllegalArgumentException("折扣选项不能为空");
        }
        Discount discount = new Discount();
        discount.group = group;
        return discount;
    }

    public BigDecimal getValue() {
        return value;
    }

    public List<Discount> getGroup() {
        return group;
    }

    /**
     * 获取最低折扣活动
     * @param commodity commodity
     * @return Discount
     */
    public Discount getLowestDiscount(Commodity commodity) {
        if (this.value != null && this.isActivityCommodity(commodity)) {
            return this;
        }
        // 从折扣组中选择
        if (this.group != null) {
            // 返回最小折扣
            return this.group.stream()
                        .filter(e->e.isActivityCommodity(commodity))
                        .min(Comparator.comparing(e->e.value)).orElse(null);
        }
        return null;
    }

    @Override
    public List<Commodity> getActivityCommodities(List<Commodity> commodities) {
        if (this.value != null) {
            return super.getActivityCommodities(commodities);
        }
        Set<Commodity> commodityAllSet = new HashSet<>();
        // 返回折扣组的商品列表
        for (Discount discount : group) {
            commodityAllSet.addAll(new HashSet<>(discount.getActivityCommodities(commodities)));
        }
        return new ArrayList<>(commodityAllSet);
    }
}
