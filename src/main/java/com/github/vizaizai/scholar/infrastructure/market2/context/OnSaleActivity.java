package com.github.vizaizai.scholar.infrastructure.market2.context;

import com.github.vizaizai.scholar.infrastructure.market2.context.impl.OnSaleStrategy;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 打折活动
 * @author liaochongwei
 * @date 2022/10/24 20:51
 */
public class OnSaleActivity extends Activity {
    /**
     * 折扣比例[0-1]
     */
    private final BigDecimal ratio;
    /**
     * 参与项限制获取器
     */
    private Function<String,Integer> maxLimitGetter;

    private OnSaleActivity(String id, BigDecimal value) {
        super(id, new OnSaleStrategy());
        this.ratio = value;
    }

    @Override
    public Activity newActivity() {
        OnSaleActivity onSaleActivity = new OnSaleActivity(this.getId(), this.getRatio());
        onSaleActivity.setMaxLimitGetter(this.maxLimitGetter);
        return onSaleActivity;
    }

    public static OnSaleActivity create(String id, BigDecimal ratio) {
        if (ratio == null
                || ratio.compareTo(BigDecimal.ZERO) < 0
                || ratio.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("打折活动参数错误~");
        }
        return new OnSaleActivity(id, ratio);
    }

    /**
     * 获取最大限制数量
     * @return
     */
    public int getMaxLimit(String itemId) {
        if (this.maxLimitGetter != null) {
            Integer max = maxLimitGetter.apply(itemId);
            return max == null ? -1 : max;
        }
        return -1;
    }

    public void setMaxLimitGetter(Function<String,Integer> maxLimitGetter) {
        this.maxLimitGetter = maxLimitGetter;
    }

    public BigDecimal getRatio() {
        return ratio;
    }


}
