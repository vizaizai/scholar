package com.github.vizaizai.scholar.infrastructure.market2.context.impl;


import com.github.vizaizai.scholar.infrastructure.market2.DiscountItem;
import com.github.vizaizai.scholar.infrastructure.market2.Item;
import com.github.vizaizai.scholar.infrastructure.market2.context.MarketStrategy;
import com.github.vizaizai.scholar.infrastructure.market2.context.OnSaleActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 打折策略
 * @author liaochongwei
 * @date 2022/10/24 20:50
 */
public class OnSaleStrategy implements MarketStrategy<OnSaleActivity> {
    @Override
    public void doMarket(List<Item> items, OnSaleActivity activity) {
        List<DiscountItem> discountItems  = new ArrayList<>();
        for (Item item : activity.getActualItems(items)) {
            // 折扣值
            BigDecimal value = activity.getRatio();
            // 优惠前价格
            BigDecimal prePrice = item.getRealPrice();
            // 最大优惠数量限制
            int maxLimit = activity.getMaxLimit(item.getId());
            // 待计算价格
            BigDecimal calPrice = prePrice;
            // 重设待计算价格
            if (maxLimit != -1 && maxLimit < item.getNum()) {
                calPrice  = prePrice.divide(BigDecimal.valueOf(item.getNum()), 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(maxLimit)).setScale(2, RoundingMode.HALF_UP);
            }
            // 优惠金额
            BigDecimal discountAmount = calPrice.subtract(calPrice.multiply(value).setScale(2, RoundingMode.HALF_UP));
            // 优惠后价格
            BigDecimal postPrice = prePrice.subtract(discountAmount);

            DiscountItem discountItem = new DiscountItem();
            discountItem.setId(item.getId());
            discountItem.setActivityTag(activity.getTag());
            discountItem.setActivityId(activity.getId());
            discountItem.setPrePrice(prePrice);
            discountItem.setPostPrice(postPrice);
            discountItem.setDiscountAmount(discountAmount);

            discountItems.add(discountItem);
            item.addDiscountDetail(discountItem);

        }
        activity.setDiscountItems(discountItems);
    }
}
