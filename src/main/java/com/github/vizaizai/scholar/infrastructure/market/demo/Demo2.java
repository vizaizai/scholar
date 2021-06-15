package com.github.vizaizai.scholar.infrastructure.market.demo;

import com.alibaba.fastjson.JSON;
import com.github.vizaizai.scholar.infrastructure.market.Commodity;
import com.github.vizaizai.scholar.infrastructure.market.Discount;
import com.github.vizaizai.scholar.infrastructure.market.FullReduction;
import com.github.vizaizai.scholar.infrastructure.market.Item;
import com.github.vizaizai.scholar.infrastructure.market.context.MarketContext;
import com.github.vizaizai.scholar.infrastructure.market.context.impl.DiscountStrategy;
import com.github.vizaizai.scholar.infrastructure.market.context.impl.FullReductionStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liaochongwei
 * @date 2021/6/9 16:39
 */
public class Demo2 {
    public static void main(String[] args) {


        /**
         * 构造活动
         */
        List<Item> items = new ArrayList<>();
        items.add(Item.createOne("123123",2));
        items.add(Item.createOne("111223",1));
        items.add(Item.createOne("111111",2));

        FullReduction fullReduction = FullReduction.create();
        fullReduction.addLevel(BigDecimal.valueOf(30), BigDecimal.valueOf(10));
        fullReduction.addLevel(BigDecimal.valueOf(20), BigDecimal.valueOf(5));
        fullReduction.addLevel(BigDecimal.valueOf(50), BigDecimal.valueOf(15));
        //fullReduction.setId("dis-1");
        //fullReduction.setMutexType(MutexType.NONE);
        //fullReduction.setItems(items);



        // 构造商品列表
        List<Commodity> commodities = new ArrayList<>();
        commodities.add(new Commodity("111223",3, BigDecimal.valueOf(23.70)));
        commodities.add(new Commodity("111111", 4, BigDecimal.valueOf(22.05)));

        MarketContext<Discount> marketContext1 = new MarketContext<>(new DiscountStrategy());
        Discount discount = Discount.create(BigDecimal.valueOf(0.75));
        marketContext1.doHandle(commodities,discount);

        MarketContext<FullReduction> marketContext2 = new MarketContext<>(new FullReductionStrategy());
        marketContext2.doHandle(commodities,fullReduction);

        System.out.println(JSON.toJSONString(commodities));

    }
}
