package com.github.vizaizai.scholar.infrastructure.market2;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 商品-活动参与项
 * @author liaochongwei
 * @date 2022/10/24 16:49
 */
public class Commodity extends Item {
    /**
     * 每多少个
     */
    private Integer everyPackage;
    /**
     * 包装费
     */
    private BigDecimal packagePrice;


    public Commodity(String id, BigDecimal price, Integer num, Integer everyPackage, BigDecimal packagePrice) {
        super(id, price, num);
        this.everyPackage = everyPackage;
        this.packagePrice = packagePrice;
        this.resetTotalPrice();
    }

    public Commodity(String id, BigDecimal price, Integer num) {
       super(id, price, num);
    }

    /**
     * 重设总金额（加入打包费）
     */
    private void resetTotalPrice() {
        if (this.everyPackage != null && this.packagePrice != null) {
            // 总包装费
            BigDecimal totalPackagePrice = BigDecimal.valueOf(this.getNum())
                    .divide(BigDecimal.valueOf(this.everyPackage), 0, RoundingMode.UP)
                    .multiply(packagePrice)
                    .setScale(2, RoundingMode.HALF_UP);
            // 把打包费作为附加费用
            this.setAdditionPrice(totalPackagePrice);
            // 初始化总金额
            this.initTotalPrice();
        }
    }
}
