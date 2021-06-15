package com.github.vizaizai.scholar.infrastructure.market.demo;

import com.alibaba.fastjson.JSON;
import com.github.vizaizai.scholar.infrastructure.market.Commodity;
import com.github.vizaizai.scholar.infrastructure.market.Discount;
import com.github.vizaizai.scholar.infrastructure.market.Item;
import com.github.vizaizai.scholar.infrastructure.market.constants.MutexType;
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
        items.add(Item.createOne("123123",2));
        items.add(Item.createOne("111223",1));
        items.add(Item.createOne("111111",2));

        Discount discount = Discount.create(BigDecimal.valueOf(0.75));
        discount.setId("dis-1");
        discount.setMutexType(MutexType.NONE);
        discount.setItems(items);



        // 构造商品列表
        List<Commodity> commodities = new ArrayList<>();
        commodities.add(new Commodity("111223",3, BigDecimal.valueOf(23.70)));
        commodities.add(new Commodity("111111", 4, BigDecimal.valueOf(22.05)));

        marketContext.doHandle(commodities,discount);

        System.out.println(JSON.toJSONString(commodities));

    }
}
