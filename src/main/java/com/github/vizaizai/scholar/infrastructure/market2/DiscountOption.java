package com.github.vizaizai.scholar.infrastructure.market2;

import java.util.List;

/**
 * 优惠选项
 * @author liaochongwei
 * @date 2022/10/31 19:54
 */
public class DiscountOption {
    /**
     * 最佳优惠
     */
    public static final int MODE_BAST = 1;
    /**
     * 第一个
     */
    public static final int MODE_FIRST = 2;
    /**
     * 最后一个
     */
    public static final int MODE_LAST = 3;

    /**
     * 选择模式
     */
    private final int mode;
    /**
     * 必要活动（id）
     */
    private List<String> must;

    private DiscountOption(int mode) {
        this.mode = mode;
    }

    public static DiscountOption best(){
        return new DiscountOption(MODE_BAST);
    }
    public static DiscountOption first(){
        return new DiscountOption(MODE_FIRST);
    }
    public static DiscountOption last(){
        return new DiscountOption(MODE_LAST);
    }

    public DiscountOption must(List<String> must) {
        this.must = must;
        return this;
    }

    public int getMode() {
        return mode;
    }

    public List<String> getMust() {
        return must;
    }

}
