package com.github.vizaizai.scholar.infrastructure.market.context.impl;

import com.github.vizaizai.scholar.infrastructure.market.Commodity;
import com.github.vizaizai.scholar.infrastructure.market.Discount;
import com.github.vizaizai.scholar.infrastructure.market.CommodityPrice;

import com.github.vizaizai.scholar.infrastructure.market.context.MarketStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liaochongwei
 * @date 2021/6/10 10:44
 */
public class DiscountStrategy implements MarketStrategy<Discount> {
    @Override
    public void doHandle(List<Commodity> commodities, Discount activity) {
        List<Commodity> activityCommodities = activity.getActivityCommodities(commodities);

        for (Commodity commodity : activityCommodities) {
            List<CommodityPrice> commodityPrices = new ArrayList<>();
            // 可参与数量
            Integer maxQuantity = activity.getMaxQuantity(commodity);
            // 商品数量拆分
            for (int i = 0; i < commodity.getQuantity(); i++) {
                BigDecimal price = commodity.getPrice(i);
                CommodityPrice commodityPrice = new CommodityPrice();
                commodityPrice.setItemId(commodity.getId());
                commodityPrice.setPrePrice(price);
                // 可参与数量不限制或未达最大值，则享受则扣
                if (maxQuantity == -1 || i < maxQuantity) {
                    // 折扣价
                    commodityPrice.setPrice(price.multiply(activity.getRatio()));
                }else {
                    // 原价
                    commodityPrice.setPrice(price);
                }

                commodityPrices.add(commodityPrice);

            }

            commodity.addResult(commodityPrices,activity);
        }
    }
}
