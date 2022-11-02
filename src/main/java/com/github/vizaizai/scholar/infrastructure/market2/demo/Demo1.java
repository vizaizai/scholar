package com.github.vizaizai.scholar.infrastructure.market2.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.vizaizai.scholar.infrastructure.market2.*;
import com.github.vizaizai.scholar.infrastructure.market2.context.Activity;
import com.github.vizaizai.scholar.infrastructure.market2.context.FullReductionActivity;
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


        Activity.Group shop_zhekou = new Activity.Group("门店折扣", 1);
        Activity.Group shop_manjian = new Activity.Group("门店满减", 2);
        Activity.Group shop_youhuiquan = new Activity.Group("门店优惠券", 3);
        Activity.Group platform_youhuiquan = new Activity.Group("平台优惠券", 4);
        Activity.Group platform_peisongfei = new Activity.Group("平台配送费优惠", 4);
        Activity.Group platform_jifen = new Activity.Group("平台积分抵扣", 6);
        Activity.Group platform_lijian = new Activity.Group("平台立减", 7);

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

        Activity a_shop_manjian = FullReductionActivity.create("a_shop_manjian",new BigDecimal("80"),new BigDecimal("80"));
        a_shop_manjian.setGroup(shop_manjian);
        a_shop_manjian.setTag("门店满减");
        a_shop_manjian.setItemIds(Arrays.asList("item2","item3"));
        a_shop_manjian.setShareType(ShareType.SHARE);
        activities.add(a_shop_manjian);

        Activity a_shop_youhuiquan = FullReductionActivity.create("a_shop_youhuiquan",new BigDecimal("30"),new BigDecimal("6"));
        a_shop_youhuiquan.setGroup(shop_youhuiquan);
        a_shop_youhuiquan.setTag("门店优惠券");
        a_shop_youhuiquan.setShareType(ShareType.NON_SHARE);
        activities.add(a_shop_youhuiquan);

        MarketHandler marketHandler = new MarketHandler(activities, DiscountOption.best());
        ComputeResult result = marketHandler.execute(items);
        System.out.println("最终计算结果：" + JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect));
    }
}
