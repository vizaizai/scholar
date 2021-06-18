package com.github.vizaizai.scholar.infrastructure.market.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.vizaizai.scholar.infrastructure.market.Activity;
import com.github.vizaizai.scholar.infrastructure.market.Discount;
import com.github.vizaizai.scholar.infrastructure.market.Market;
import com.github.vizaizai.scholar.infrastructure.market.constants.MutexType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liaochongwei
 * @date 2021/6/15 17:35
 */
public class Demo3 {
    public static void main(String[] args) {

        Discount discount1 = Discount.create(BigDecimal.valueOf(0.7));
        discount1.setId("1");
        discount1.setMutexType(MutexType.NONE);

        Discount discount2 = Discount.create(BigDecimal.valueOf(0.7));
        discount2.setId("2");
        discount2.setMutexType(MutexType.DISABLED);
        discount2.setOrder(-1);

        Discount discount3 = Discount.create(BigDecimal.valueOf(0.7));
        discount3.setId("3");
        discount3.setMutexType(MutexType.ALL);

        Discount discount4 = Discount.create(BigDecimal.valueOf(0.7));
        discount4.setId("4");
        discount4.setMutexType(MutexType.WITH_MUTEX);

        Discount discount5 = Discount.create(BigDecimal.valueOf(0.7));
        discount5.setId("5");
        discount5.setMutexType(MutexType.WITH_MUTEX);
        discount5.setOrder(-2);

        Discount discount6 = Discount.create(BigDecimal.valueOf(0.7));
        discount6.setId("6");
        discount6.setMutexType(MutexType.ALL);

        Discount discount7 = Discount.create(BigDecimal.valueOf(0.7));
        discount7.setId("7");
        discount7.setMutexType(MutexType.NONE);

        Discount discount8 = Discount.create(BigDecimal.valueOf(0.7));
        discount8.setId("8");
        discount8.setMutexType(MutexType.WITH_MUTEX);


        List<Activity> activities = new ArrayList<>();
        activities.add(discount1);
        activities.add(discount2);
        activities.add(discount3);
        activities.add(discount4);
        activities.add(discount5);
        activities.add(discount6);
        activities.add(discount7);
        activities.add(discount8);
        Market market = new Market(activities);
        System.out.println(JSON.toJSONString(market.getActivityGroups(), SerializerFeature.DisableCircularReferenceDetect));
    }
}
