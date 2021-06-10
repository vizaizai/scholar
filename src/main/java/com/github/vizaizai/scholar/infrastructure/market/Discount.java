package com.github.vizaizai.scholar.infrastructure.market;

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

    private Discount(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public static Discount create(BigDecimal ratio) {
        return new Discount(ratio);
    }

    public BigDecimal getRatio() {
        return ratio;
    }
}
