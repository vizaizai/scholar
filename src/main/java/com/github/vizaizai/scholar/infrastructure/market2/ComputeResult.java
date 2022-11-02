package com.github.vizaizai.scholar.infrastructure.market2;

import com.github.vizaizai.scholar.infrastructure.market2.context.Activity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 计算结果
 * @author liaochongwei
 * @date 2022/10/24 19:10
 */
@Data
public class ComputeResult {

    /**
     * 总价
     */
    private BigDecimal totalPrice = BigDecimal.ZERO;
    /**
     * 总优惠
     */
    private BigDecimal totalDiscountAmount = BigDecimal.ZERO;
    /**
     * 最终总价
     */
    private BigDecimal finalTotalPrice = BigDecimal.ZERO;
    /**
     * 活动信息
     */
    private List<Activity> activities;
    /**
     * 参与项信息
     */
    private List<Item> items;




}
