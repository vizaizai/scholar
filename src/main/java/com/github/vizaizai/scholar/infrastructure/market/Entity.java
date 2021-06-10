package com.github.vizaizai.scholar.infrastructure.market;

import java.util.ArrayList;
import java.util.List;

/**
 * 实体
 * @author liaochongwei
 * @date 2021/6/10 15:43
 */
public class Entity {
    /**
     * 商品列表
     */
    private List<Commodity> commodities;


    public void addCommodity(Commodity commodity) {
        if (commodities == null) {
            commodities = new ArrayList<>();
        }
        commodities.add(commodity);
    }
}
