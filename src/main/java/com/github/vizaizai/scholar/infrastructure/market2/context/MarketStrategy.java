package com.github.vizaizai.scholar.infrastructure.market2.context;

import com.github.vizaizai.scholar.infrastructure.market2.DiscountItem;
import com.github.vizaizai.scholar.infrastructure.market2.Item;

import java.util.Collections;
import java.util.List;

/**
 * 活动策略
 * @author liaochongwei
 * @date 2021/6/9 16:59
 */
public interface MarketStrategy<T extends Activity> {
    /**
     * 计算且添加优惠项
     * @param items 参与项列表
     * @param activity 活动信息
     */
    void doMarket(List<Item> items, T activity);

    /**
     * 仅计算
     * @param items 参与项列表
     * @param activity 活动信息
     * @return 优惠项列表
     */
    default List<DiscountItem> preDoMarket(List<Item> items, T activity) {
        return Collections.emptyList();
    }
}
