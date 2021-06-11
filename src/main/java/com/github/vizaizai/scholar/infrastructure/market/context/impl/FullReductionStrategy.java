package com.github.vizaizai.scholar.infrastructure.market.context.impl;

import com.github.vizaizai.scholar.infrastructure.market.Commodity;
import com.github.vizaizai.scholar.infrastructure.market.FullReduction;
import com.github.vizaizai.scholar.infrastructure.market.context.MarketStrategy;

import java.util.List;

/**
 * @author liaochongwei
 * @date 2021/6/10 10:47
 */
public class FullReductionStrategy implements MarketStrategy<FullReduction> {
    @Override
    public void doHandle(List<Commodity> commodities, FullReduction activity) {
        System.out.println("满减");
    }
}
