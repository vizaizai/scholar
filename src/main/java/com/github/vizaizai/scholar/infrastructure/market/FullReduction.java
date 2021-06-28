package com.github.vizaizai.scholar.infrastructure.market;

import com.github.vizaizai.scholar.infrastructure.market.constants.ActivityType;
import com.github.vizaizai.scholar.infrastructure.market.constants.MutexType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 满减参数
 * @author liaochongwei
 * @date 2021/6/9 15:21
 */
public class FullReduction extends Activity{

    /**
     * 满减级别
     */
    private List<Level> levels;


    public FullReduction() {
        // 默认禁用互斥
        this.setMutexType(MutexType.DISABLED);
        // 默认全部都参与活动
        this.setItems(Collections.singletonList(Item.createAll()));
        this.setType(ActivityType.FULL_REDUCTION);
    }

    public static FullReduction create() {
        return new FullReduction();
    }
    public static FullReduction create(BigDecimal meet, BigDecimal reduce) {
        FullReduction fullReduction = new FullReduction();
        fullReduction.addLevel(meet, reduce);
        return fullReduction;
    }
    /**
     * 添加满减级别
     * @param meet 满
     * @param reduce 减
     */
    public void addLevel(BigDecimal meet, BigDecimal reduce) {
        if (this.levels == null || this.levels.isEmpty()) {
            this.levels = new ArrayList<>();
        }
        this.levels.add(new Level(meet,reduce));
        // 满减排序
        if (this.levels.size() > 1) {
            this.levels.sort(Comparator.comparing(Level::getMeet).reversed());
        }

    }

    public List<Level> getLevels() {
        return levels;
    }

    /**
     * 满减级别
     */
    public static class Level {
        /**
         * 满
         */
        private final BigDecimal meet;
        /**
         * 减
         */
        private final BigDecimal reduce;

        public Level(BigDecimal meet, BigDecimal reduce) {
            this.meet = meet;
            this.reduce = reduce;
        }

        public BigDecimal getMeet() {
            return meet;
        }

        public BigDecimal getReduce() {
            return reduce;
        }
    }



}
