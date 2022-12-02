package com.github.vizaizai.scholar.infrastructure.market2.context;

import com.github.vizaizai.scholar.infrastructure.market2.Item;
import com.github.vizaizai.scholar.infrastructure.market2.context.impl.FullReductionStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 满减活动
 * @author liaochongwei
 * @date 2022/10/25 10:56
 */
public class FullReductionActivity extends Activity{
    /**
     * 满减规则
     */
    private final List<Rule> rules;

    private FullReductionActivity(String id, List<Rule> rules) {
        super(id, new FullReductionStrategy());
        this.rules = rules;
    }

    @Override
    public Activity newActivity() {
        return new FullReductionActivity(this.getId(),this.getRules());
    }

    public static FullReductionActivity create(String id, BigDecimal meetPrice, BigDecimal discountPrice) {
        return FullReductionActivity.create(id).addRule(meetPrice, discountPrice);
    }

    public static FullReductionActivity create(String id) {
        return new FullReductionActivity(id, new ArrayList<>());
    }

    public FullReductionActivity addRule(BigDecimal meetPrice, BigDecimal discountPrice) {
        if (meetPrice == null || discountPrice == null
                || meetPrice.compareTo(BigDecimal.ZERO) < 0
                || discountPrice.compareTo(BigDecimal.ZERO) < 0
                || meetPrice.compareTo(discountPrice) < 0) {
            throw new IllegalArgumentException("满减活动参数错误~");
        }
        rules.add(new Rule(meetPrice, discountPrice));
        // 规则排序，优惠金额高->低，门槛高->低
        rules.sort(Comparator.comparing(Rule::getDiscountPrice,
                Comparator.reverseOrder()).thenComparing(Rule::getMeetPrice, Comparator.reverseOrder()));
        return this;
    }

    @Override
    public boolean check(List<Item> items) {
        if (!super.check(items)) {
            return false;
        }
        // 检查最大总金额是否满足最低门槛
        BigDecimal totalPrice = this.getActualItems(items).stream().map(Item::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 匹配任意一个满足门槛
        return rules.stream().anyMatch(e -> totalPrice.compareTo(e.getMeetPrice()) >= 0);
    }

    public List<Rule> getRules() {
        return rules;
    }

    /**
     * 满减规则
     */
    public static class Rule {
        /**
         * 门槛金额
         */
        private final BigDecimal meetPrice;
        /**
         * 优惠金额
         */
        private final BigDecimal discountPrice;

        public Rule(BigDecimal meetPrice, BigDecimal discountPrice) {
            this.meetPrice = meetPrice;
            this.discountPrice = discountPrice;
        }

        public BigDecimal getMeetPrice() {
            return meetPrice;
        }

        public BigDecimal getDiscountPrice() {
            return discountPrice;
        }
    }
}
