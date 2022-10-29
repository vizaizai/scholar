package com.github.vizaizai.scholar.infrastructure.market2.context;



import com.github.vizaizai.scholar.infrastructure.market2.Item;

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

    public void doHandle(List<Item> items, T activity) {
        marketStrategy.doMarket(items, activity);
    }
}
