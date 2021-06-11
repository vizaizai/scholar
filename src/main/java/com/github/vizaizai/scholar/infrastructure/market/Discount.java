package com.github.vizaizai.scholar.infrastructure.market;

import com.github.vizaizai.scholar.infrastructure.market.constants.MultiDiscountHandleMethod;

import java.math.BigDecimal;

/**
 * 折扣参数
 * @author liaochongwei
 * @date 2021/6/9 14:30
 */
public class Discount extends Activity{
    /**
     * 折扣比例
     */
    private final BigDecimal ratio;
    /**
     * 多折扣处理方法
     */
    private MultiDiscountHandleMethod multiDiscountHandleMethod;

    private Discount(BigDecimal ratio) {
        this.ratio = ratio;
        this.multiDiscountHandleMethod = MultiDiscountHandleMethod.PRICE;
    }

    public static Discount create(BigDecimal ratio) {
        return new Discount(ratio);
    }

    public void setMultiDiscountHandleMethod(MultiDiscountHandleMethod multiDiscountHandleMethod) {
        this.multiDiscountHandleMethod = multiDiscountHandleMethod;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public MultiDiscountHandleMethod getMultiDiscountHandleMethod() {
        return multiDiscountHandleMethod;
    }
}
