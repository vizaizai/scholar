package com.github.vizaizai.scholar.infrastructure.market.context;

import com.github.vizaizai.scholar.infrastructure.market.Activity;
import com.github.vizaizai.scholar.infrastructure.market.Commodity;
import com.github.vizaizai.scholar.infrastructure.market.ItemResult;

import java.util.List;

/**
 * 活动策略
 * @author liaochongwei
 * @date 2021/6/9 16:59
 */
public interface MarketStrategy<T extends Activity> {

    List<ItemResult> doHandle(List<Commodity> commodities, T activity);
}
