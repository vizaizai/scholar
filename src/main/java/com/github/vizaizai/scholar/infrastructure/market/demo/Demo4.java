package com.github.vizaizai.scholar.infrastructure.market.demo;

import com.github.vizaizai.scholar.infrastructure.market.*;
import com.github.vizaizai.scholar.infrastructure.market.constants.MutexType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liaochongwei
 * @date 2021/6/16 11:29
 */
public class Demo4 {
    public static void main(String[] args) {


        FullReduction fullReduction1 = FullReduction.create(BigDecimal.valueOf(30), BigDecimal.valueOf(5));
        fullReduction1.setId("fullReduction1");
        fullReduction1.setMutexType(MutexType.WITH_MUTEX);
        List<Item> items1 = new ArrayList<>();
        items1.add(Item.createOne("1"));
        fullReduction1.setItems(items1);


        FullReduction fullReduction2 = FullReduction.create(BigDecimal.valueOf(30), BigDecimal.valueOf(10));
        fullReduction2.setId("fullReduction2");
        fullReduction2.setMutexType(MutexType.WITH_MUTEX);
        List<Item> items2 = new ArrayList<>();
        items2.add(Item.createOne("2"));
        items2.add(Item.createOne("3"));
        items2.add(Item.createOne("4"));


        fullReduction2.setItems(items2);



        Discount discount = Discount.create(BigDecimal.valueOf(7));
        discount.setOrder(-1);
        discount.setMutexType(MutexType.DISABLED);
        List<Item> items3 = new ArrayList<>();
        items3.add(Item.createOne("1"));
        items3.add(Item.createOne("2"));
        items3.add(Item.createOne("3"));
        discount.setItems(items3);

        Discount discount1 = Discount.create(BigDecimal.valueOf(5));
        discount1.setOrder(-1);
        discount1.setMutexType(MutexType.DISABLED);
        List<Item> items5 = new ArrayList<>();
        items5.add(Item.createAll(5));
        discount1.setItems(items5);


        Discount discountGroup = Discount.create(Arrays.asList(discount));
        discountGroup.setId("discountGroup");
        discountGroup.setOrder(-1);

        List<Activity> activities = new ArrayList<>();
        activities.add(fullReduction1);
        activities.add(fullReduction2);
        //activities.add(discount);
        activities.add(discountGroup);

        List<Commodity> commodities = new ArrayList<>();
        commodities.add(new Commodity("1", 4, BigDecimal.valueOf(3)));
        commodities.add(new Commodity("1", 4, BigDecimal.valueOf(4)));
        commodities.add(new Commodity("2", 4, BigDecimal.valueOf(4)));
        commodities.add(new Commodity("3", 4, BigDecimal.valueOf(3)));
        commodities.add(new Commodity("2", 1, BigDecimal.valueOf(3)));
        commodities.add(new Commodity("4", 1, BigDecimal.valueOf(2)));


        Market market = new Market(activities);
        BigDecimal marketPrice = market.doMarketing(commodities);
        System.out.println("计算结果：" + marketPrice.setScale(2, RoundingMode.HALF_UP));

    }

}
