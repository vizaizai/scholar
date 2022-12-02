package com.github.vizaizai.scholar.infrastructure.market2.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.vizaizai.scholar.infrastructure.market2.*;
import com.github.vizaizai.scholar.infrastructure.market2.context.Activity;
import com.github.vizaizai.scholar.infrastructure.market2.context.FullReductionActivity;
import com.github.vizaizai.scholar.infrastructure.market2.context.MultiOnSaleActivity;
import com.github.vizaizai.scholar.infrastructure.market2.context.OnSaleActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liaochongwei
 * @date 2022/10/24 17:46
 */
public class Demo1 {
    public static void main(String[] args) {

        Activity.Group shop_zhekou = new Activity.Group("门店折扣", 1,true);
        Activity.Group shop_multi_zhekou = new Activity.Group("门店折扣 + 会员价", 1,true);
        Activity.Group shop_manjian = new Activity.Group("门店满减", 2,true);
        Activity.Group shop_youhuiquan = new Activity.Group("门店优惠券", 3,false);
        Activity.Group platform_youhuiquan = new Activity.Group("平台优惠券", 4,false);
        Activity.Group platform_zhuanti_youhuiquan = new Activity.Group("平台优惠券", 4,false);
        Activity.Group platform_peisongfei = new Activity.Group("平台配送费优惠", 4,false);
        Activity.Group platform_jifen = new Activity.Group("平台积分抵扣", 6,false);
        Activity.Group platform_lijian = new Activity.Group("平台立减", 7,false);

        Item item1 = new Item("item1",new BigDecimal("32"),3);
        Item item2 = new Item("item2",new BigDecimal("21"),2);
        Item item3 = new Commodity("item3",new BigDecimal("7"),4, 1, new BigDecimal("1"));
        // 配送费
        Item deliveryMoney = new Item("deliveryMoney",new BigDecimal("7"),1);

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(deliveryMoney);

        List<Activity> activities = new ArrayList<>();

        OnSaleActivity a_shop_zhekou = OnSaleActivity.create("a_shop_zhekou",BigDecimal.valueOf(0.7));
        a_shop_zhekou.setItemIds(Arrays.asList("item1","item3"));
        a_shop_zhekou.setTag("门店折扣");
        a_shop_zhekou.setGroup(shop_zhekou);
        a_shop_zhekou.setShareType(ShareType.SHARE);
        a_shop_zhekou.setMaxLimitGetter(itemId-> {
            return -1;
        });
        activities.add(a_shop_zhekou);

        Activity a_shop_manjian = FullReductionActivity.create("a_shop_manjian1",new BigDecimal("40"),new BigDecimal("5"));
        a_shop_manjian.setGroup(shop_manjian);
        a_shop_manjian.setTag("门店满减");
        a_shop_manjian.setItemIds(Arrays.asList("item2","item3"));
        a_shop_manjian.setShareType(ShareType.SHARE);
        activities.add(a_shop_manjian);

        Activity a_shop_manjian2 = FullReductionActivity.create("a_shop_manjian2",new BigDecimal("50"),new BigDecimal("30"));
        a_shop_manjian2.setGroup(shop_manjian);
        a_shop_manjian2.setTag("门店满减2");
        a_shop_manjian2.setItemIds(Arrays.asList("item1"));
        a_shop_manjian2.setShareType(ShareType.ONLY_SHARE);
        activities.add(a_shop_manjian2);


        Activity a_shop_youhuiquan = FullReductionActivity.create("a_shop_youhuiquan",new BigDecimal("30"),new BigDecimal("6"));
        a_shop_youhuiquan.setGroup(shop_youhuiquan);
        a_shop_youhuiquan.setTag("门店优惠券");
        a_shop_youhuiquan.setShareType(ShareType.ONLY_SHARE);
        activities.add(a_shop_youhuiquan);

        Activity a_platform_lijian1 = FullReductionActivity.create("a_platform_lijian1",new BigDecimal("20"),new BigDecimal("5"));
        a_platform_lijian1.setGroup(platform_lijian);
        a_platform_lijian1.setTag("平台立减1");
        a_platform_lijian1.setItemIds(Arrays.asList("11111111"));
        activities.add(a_platform_lijian1);

        Activity a_platform_lijian2 = FullReductionActivity.create("a_platform_lijian2",new BigDecimal("30"),new BigDecimal("8"));
        a_platform_lijian2.setGroup(platform_lijian);
        a_platform_lijian2.setTag("平台立减2");
        activities.add(a_platform_lijian2);

        Activity a_platform_lijian3 = FullReductionActivity.create("a_platform_lijian3",new BigDecimal("200"),new BigDecimal("12"));
        a_platform_lijian3.setGroup(platform_lijian);
        a_platform_lijian3.setTag("平台立减3");
        activities.add(a_platform_lijian3);

        Activity a_platform_lijian4 = FullReductionActivity.create("a_platform_lijian4",new BigDecimal("300"),new BigDecimal("20"));
        a_platform_lijian4.setGroup(platform_lijian);
        a_platform_lijian4.setTag("平台立减5");
        activities.add(a_platform_lijian4);

        Activity a_platform_lijian5 = FullReductionActivity.create("a_platform_lijian5",new BigDecimal("700"),new BigDecimal("33"));
        a_platform_lijian5.setGroup(platform_lijian);
        a_platform_lijian5.setTag("平台立减5");
        activities.add(a_platform_lijian5);



        OnSaleActivity a_shop_zhekou_1 = OnSaleActivity.create("a_shop_zhekou_1",BigDecimal.valueOf(0.7));
        a_shop_zhekou_1.setTag("门店折扣1");

        OnSaleActivity a_shop_zhekou_2 = OnSaleActivity.create("a_shop_zhekou_2",BigDecimal.valueOf(0.6));
        a_shop_zhekou_2.setTag("门店会员折扣2");

        // 多打折活动
        MultiOnSaleActivity multiOnSaleActivity = MultiOnSaleActivity.create(Arrays.asList(a_shop_zhekou_1, a_shop_zhekou_2));
        multiOnSaleActivity.setGroup(shop_multi_zhekou);
        activities.add(multiOnSaleActivity);



        for (int i = 0; i < 100; i++) {
            long s = System.currentTimeMillis();
            MarketHandler marketHandler = new MarketHandler(activities, items, DiscountOption.best().must(Arrays.asList("a_shop_youhuiquan")));
            ComputeResult result = marketHandler.execute();
            //System.out.println("最终计算结果：" + JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect));
            System.out.println("耗时：" + (System.currentTimeMillis() - s) + "ms");
         }
    }
}
