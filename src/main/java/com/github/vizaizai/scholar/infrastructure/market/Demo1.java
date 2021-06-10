package com.github.vizaizai.scholar.infrastructure.market;

import com.alibaba.fastjson.JSON;
import com.github.vizaizai.scholar.infrastructure.market.constants.LimitType;
import com.github.vizaizai.scholar.infrastructure.market.context.MarketContext;
import com.github.vizaizai.scholar.infrastructure.market.context.impl.DiscountStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liaochongwei
 * @date 2021/6/9 16:39
 */
public class Demo1 {
    public static void main(String[] args) {
        MarketContext<Discount> marketContext = new MarketContext<>(new DiscountStrategy());

        /**
         * 构造活动
         */
        List<Item> items = new ArrayList<>();
        items.add(Item.createForPortion("123123", Limit.createNone()));
        items.add(Item.createForPortion("111223", Limit.createNone()));
        items.add(Item.createForPortion("111111", Limit.createNone()));

        Discount discount = Discount.create(BigDecimal.valueOf(0.75));
        discount.setMutex(Mutex.createNone());
        discount.setItems(items);


        // 构造商品列表
        List<Commodity> commodities = new ArrayList<>();
        commodities.add(new Commodity("111223",2, BigDecimal.valueOf(23.70)));
        commodities.add(new Commodity("111111", 3, BigDecimal.valueOf(22.05)));

        List<ItemResult> itemResults = marketContext.doHandle(commodities,discount);

        System.out.println(JSON.toJSONString(itemResults));


    }
}
