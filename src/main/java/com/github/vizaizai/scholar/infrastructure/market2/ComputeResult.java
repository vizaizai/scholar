package com.github.vizaizai.scholar.infrastructure.market2;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
     * 参与项优惠映射
     */
    private Map<String,List<DiscountItem>> itemDiscountMapping;
    /**
     * 活动优惠映射
     */
    private Map<String, List<DiscountItem>> activityDiscountMapping;
}
