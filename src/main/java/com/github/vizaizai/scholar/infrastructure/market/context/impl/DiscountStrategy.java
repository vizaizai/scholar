package com.github.vizaizai.scholar.infrastructure.market.context.impl;

import com.github.vizaizai.scholar.infrastructure.market.Commodity;
import com.github.vizaizai.scholar.infrastructure.market.Discount;
import com.github.vizaizai.scholar.infrastructure.market.ItemResult;
import com.github.vizaizai.scholar.infrastructure.market.Limit;
import com.github.vizaizai.scholar.infrastructure.market.context.MarketStrategy;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liaochongwei
 * @date 2021/6/10 10:44
 */
public class DiscountStrategy implements MarketStrategy<Discount> {
    @Override
    public List<ItemResult> doHandle(List<Commodity> commodities, Discount activity) {
        List<Commodity> activityCommodities = activity.getActivityCommodities(commodities);

        for (Commodity activityCommodity : activityCommodities) {
            BigDecimal price = activityCommodity.getPrice();
            Limit limit = activity.getLimit(activityCommodity);
        }
        System.out.println(1);

        return null;
    }
}
