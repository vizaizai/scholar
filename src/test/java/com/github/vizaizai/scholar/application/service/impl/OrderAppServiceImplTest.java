package com.github.vizaizai.scholar.application.service.impl;

import com.github.vizaizai.scholar.application.service.OrderAppService;
import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.OrderItemDo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class OrderAppServiceImplTest {

    @Autowired
    private OrderAppService orderAppService;

    @Test
    void create() {

        List<OrderItemDo> items = new ArrayList<>();

        OrderItemDo itemDo1 = new OrderItemDo();
        itemDo1.setNum(1);
        itemDo1.setPrice(BigDecimal.valueOf(11.5D));

        OrderItemDo itemDo2 = new OrderItemDo();
        itemDo2.setNum(1);
        itemDo2.setPrice(BigDecimal.valueOf(13D));

        items.add(itemDo1);
        items.add(itemDo2);
        String orderId = orderAppService.create("1335758536377384962", items);

        System.out.println(orderId);
    }

    @Test
    void create1() {

        for (int i = 0; i < 100; i++) {
            String orderId = orderAppService.create("1335758536377384962");
            System.out.println(orderId);
        }

    }

}