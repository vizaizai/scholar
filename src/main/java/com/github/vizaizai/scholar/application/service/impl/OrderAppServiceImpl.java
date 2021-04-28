package com.github.vizaizai.scholar.application.service.impl;

import com.baomidou.mybatisplus.core.override.MybatisMapperProxy;
import com.github.vizaizai.scholar.application.service.OrderAppService;
import com.github.vizaizai.scholar.infrastructure.persistence.database.OrderItemMapper;
import com.github.vizaizai.scholar.infrastructure.persistence.database.OrderMapper;
import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.OrderDo;
import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.OrderItemDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liaochongwei
 * @date 2020/12/7 14:35
 */
@Service
public class OrderAppServiceImpl implements OrderAppService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;


    @Transactional
    @Override
    public String create(String buyId, List<OrderItemDo> items) {

        OrderDo orderDo = new OrderDo();
        orderDo.setBuyerId(buyId);
        orderDo.setTotalPrice(BigDecimal.valueOf(items.stream().mapToDouble(e->e.getPrice().doubleValue() * e.getNum()).sum()));

        orderMapper.insert(orderDo);

        //MybatisMapperProxy
        for (OrderItemDo item : items) {
            item.setOrderId(orderDo.getId());
            orderItemMapper.insert(item);
        }
        return orderDo.getId();
    }

    @Override
    public String create(String buyId) {
        OrderDo orderDo = new OrderDo();
        orderDo.setBuyerId(buyId);
        orderDo.setTotalPrice(BigDecimal.ZERO);
        orderMapper.insert(orderDo);
        return orderDo.getId();
    }
}
