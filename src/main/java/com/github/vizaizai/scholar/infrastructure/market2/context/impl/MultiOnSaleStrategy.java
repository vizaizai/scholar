package com.github.vizaizai.scholar.infrastructure.market2.context.impl;


import com.github.vizaizai.scholar.infrastructure.market2.DiscountItem;
import com.github.vizaizai.scholar.infrastructure.market2.Item;
import com.github.vizaizai.scholar.infrastructure.market2.context.MarketStrategy;
import com.github.vizaizai.scholar.infrastructure.market2.context.MultiOnSaleActivity;
import com.github.vizaizai.scholar.infrastructure.market2.context.OnSaleActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 打折策略
 * @author liaochongwei
 * @date 2022/10/24 20:50
 */
public class MultiOnSaleStrategy implements MarketStrategy<MultiOnSaleActivity> {
    @Override
    public void doMarket(List<Item> items, MultiOnSaleActivity activity) {
        for (Item item : items) {
            List<OnSaleActivity> itemActivities = activity.getItemActivities(item);
            // 最佳优惠活动
            OnSaleActivity bestActivity = null;
            // 最佳优惠项
            DiscountItem bestDiscountItem = null;
            // 最大优惠金额
            BigDecimal maxDiscountAmount = BigDecimal.ZERO;
            for (OnSaleActivity itemActivity : itemActivities) {
                // 执行具体的活动策略
                List<DiscountItem> discountItems = itemActivity.getMarketStrategy().preDoMarket(Collections.singletonList(item), itemActivity);
                if (!discountItems.isEmpty()) {
                    DiscountItem discountItem = discountItems.get(0);
                    // 存在更大的优惠
                    if (discountItem.getDiscountAmount().compareTo(maxDiscountAmount) > 0) {
                        maxDiscountAmount = discountItem.getDiscountAmount();
                        bestActivity = itemActivity;
                        bestDiscountItem = discountItem;
                    }
                }
            }
            if (bestActivity != null) {
                // 添加活动的优惠项
                bestActivity.addDiscountItems(Collections.singletonList(bestDiscountItem));
                // 添加参与项的优惠项
                item.addDiscountDetail(bestDiscountItem);
            }
        }
    }
}
