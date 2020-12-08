package com.github.vizaizai.scholar.application.service;

import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.OrderItemDo;

import java.util.List;

/**
 * @author liaochongwei
 * @date 2020/12/4 16:37
 */
public interface OrderAppService {
    String create(String buyId, List<OrderItemDo> items);

    String create(String buyId);
}
