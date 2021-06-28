package com.github.vizaizai.scholar.infrastructure.market.context.impl;

import com.github.vizaizai.scholar.infrastructure.market.Commodity;
import com.github.vizaizai.scholar.infrastructure.market.CommodityPrice;
import com.github.vizaizai.scholar.infrastructure.market.Discount;
import com.github.vizaizai.scholar.infrastructure.market.context.MarketStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liaochongwei
 * @date 2021/6/10 10:44
 */
public class DiscountStrategy implements MarketStrategy<Discount> {
    @Override
    public void doHandle(List<Commodity> commodities, Discount activity) {

        for (Commodity commodity : commodities) {
            // 获取最低折扣
            Discount discountActivity = activity.getLowestDiscount(commodity);
            // 没有参与折扣活动
            if (discountActivity == null) {
                continue;
            }
            List<CommodityPrice> commodityPrices = new ArrayList<>();
            // 可参与数量
            Integer quantity = discountActivity.getQuantity(commodity);
            // 商品数量拆分
            for (int i = 0; i < commodity.getQuantity(); i++) {
                BigDecimal price = commodity.getPrice(i);
                CommodityPrice commodityPrice = new CommodityPrice();
                commodityPrice.setItemId(commodity.getId());
                commodityPrice.setPrePrice(price);
                // 可参与数量不限制或未达最大值，则享受则扣
                if (quantity == -1 || i < quantity) {
                    // 折扣价
                    commodityPrice.setPrice(price.multiply(discountActivity.getValue().divide(BigDecimal.valueOf(10),4, RoundingMode.HALF_UP)));
                    // 可参与数量-1
                    discountActivity.decreaseQuantity(commodity);
                }else {
                    // 原价
                    commodityPrice.setPrice(price);
                }

                commodityPrices.add(commodityPrice);

            }
            // 添加计算结果
            commodity.addResult(commodityPrices, activity);
        }
    }
}
