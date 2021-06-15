package com.github.vizaizai.scholar.infrastructure.market.context.impl;

import com.github.vizaizai.scholar.infrastructure.market.Commodity;
import com.github.vizaizai.scholar.infrastructure.market.FullReduction;
import com.github.vizaizai.scholar.infrastructure.market.context.MarketStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author liaochongwei
 * @date 2021/6/10 10:47
 */
public class FullReductionStrategy implements MarketStrategy<FullReduction> {
    @Override
    public void doHandle(List<Commodity> commodities, FullReduction activity) {
        List<Commodity> activityCommodities = activity.getActivityCommodities(commodities);

        // 计算参与满减商品总金额
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Commodity commodity : activityCommodities) {
            totalPrice = totalPrice.add(commodity.getSubTotal());
        }
        // 满减档次,默认档次排序 —》》》 高->底
        List<FullReduction.Level> levels = activity.getLevels();

        // 活动后总金额
        BigDecimal activityPrice = totalPrice;
        for (FullReduction.Level level : levels) {
            // 商品金额大于等于门槛
            if (totalPrice.compareTo(level.getMeet()) >= 0) {
                activityPrice = totalPrice.subtract(level.getReduce());
                break;
            }
        }
        BigDecimal z = BigDecimal.ZERO;
        // 计算满减优惠小计
        for (Commodity commodity : activityCommodities) {
            BigDecimal subTotal = commodity.getSubTotal();
            // 满减小计
            BigDecimal fullReductionSubTotal = subTotal.divide(totalPrice,4, RoundingMode.HALF_UP).multiply(activityPrice);
            z = z.add(fullReductionSubTotal);
            commodity.addResult(fullReductionSubTotal,activity);
        }
    }
}
