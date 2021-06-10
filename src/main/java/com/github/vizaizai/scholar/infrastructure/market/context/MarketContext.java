package com.github.vizaizai.scholar.infrastructure.market.context;

import com.github.vizaizai.scholar.infrastructure.market.Activity;
import com.github.vizaizai.scholar.infrastructure.market.Commodity;
import com.github.vizaizai.scholar.infrastructure.market.ItemResult;

import java.util.List;

/**
 * @author liaochongwei
 * @date 2021/6/10 10:30
 */
public class MarketContext<T extends Activity> {

    private final MarketStrategy<T> marketStrategy;

    public MarketContext(MarketStrategy<T> marketStrategy) {
        this.marketStrategy = marketStrategy;
    }

    public List<ItemResult> doHandle(List<Commodity> commodities, T activity) {
       return marketStrategy.doHandle(commodities,activity);
    }
}
