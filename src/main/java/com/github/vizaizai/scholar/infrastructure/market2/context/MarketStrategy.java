package com.github.vizaizai.scholar.infrastructure.market2.context;

import com.github.vizaizai.scholar.infrastructure.market2.Item;

import java.util.List;

/**
 * 活动策略
 * @author liaochongwei
 * @date 2021/6/9 16:59
 */
public interface MarketStrategy<T extends Activity> {
    void doMarket(List<Item> items, T activity);
}
