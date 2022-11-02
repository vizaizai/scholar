package com.github.vizaizai.scholar.infrastructure.market2.context.impl;

import com.github.vizaizai.scholar.infrastructure.market2.DiscountItem;
import com.github.vizaizai.scholar.infrastructure.market2.Item;
import com.github.vizaizai.scholar.infrastructure.market2.context.FullReductionActivity;
import com.github.vizaizai.scholar.infrastructure.market2.context.MarketStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 满减策略
 * @author liaochongwei
 * @date 2022/10/25 11:24
 */
public class FullReductionStrategy implements MarketStrategy<FullReductionActivity> {
    @Override
    public void doMarket(List<Item> items, FullReductionActivity activity) {
        List<Item> actualItems = activity.getActualItems(items);
        // 参与项总金额
        BigDecimal totalAmount = actualItems.stream().map(Item::getRealPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 总优惠
        BigDecimal totalDiscountAmount = BigDecimal.ZERO;
        // 计算最优满减金额
        for (FullReductionActivity.Rule rule : activity.getRules()) {
            // 找到符合的满减门槛
            if (totalAmount.compareTo(rule.getMeetPrice()) >= 0) {
                totalDiscountAmount = rule.getDiscountPrice();
                break;
            }
        }
        if (totalDiscountAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        // 优惠明细
        List<DiscountItem> discountItems = new ArrayList<>();

        // 最后一项优惠金额
        BigDecimal lastItemDiscountAmount = totalDiscountAmount;
        // 计算参与项的分摊优惠
        for (int i = 0; i < actualItems.size() - 1; i++) {
            Item item = actualItems.get(i);
            DiscountItem discountItem = new DiscountItem();
            discountItem.setId(item.getId());
            discountItem.setActivityId(activity.getId());
            // 计算分摊比例
            BigDecimal rate = item.getRealPrice().divide(totalAmount, 4, RoundingMode.HALF_UP);
            // 当前活动优惠前的价格
            BigDecimal prePrice = item.getRealPrice();
            discountItem.setPrePrice(prePrice);
            // 分摊优惠金额
            BigDecimal itemDiscountAmount = totalDiscountAmount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
            discountItem.setDiscountAmount(itemDiscountAmount);
            // 优惠后价格
            discountItem.setPostPrice(discountItem.getPrePrice().subtract(itemDiscountAmount));
            item.addDiscountDetail(discountItem);
            discountItems.add(discountItem);

            lastItemDiscountAmount = lastItemDiscountAmount.subtract(itemDiscountAmount);
            // 循环到倒数第二项
            int size = actualItems.size();
            if (size > 1 && i ==  size - 2) {
                Item lastItem = actualItems.get(size - 1);
                DiscountItem lastDiscountItem = new DiscountItem();
                lastDiscountItem.setId(lastItem.getId());
                lastDiscountItem.setActivityId(activity.getId());
                lastDiscountItem.setPrePrice(lastItem.getRealPrice());
                lastDiscountItem.setDiscountAmount(lastItemDiscountAmount);
                lastDiscountItem.setPostPrice(lastDiscountItem.getPrePrice().subtract(lastItemDiscountAmount));
                lastItem.addDiscountDetail(lastDiscountItem);
                discountItems.add(lastDiscountItem);
            }
        }

        activity.setDiscountItems(discountItems);
    }
}
