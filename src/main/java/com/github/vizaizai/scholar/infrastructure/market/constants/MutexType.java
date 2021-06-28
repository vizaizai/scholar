package com.github.vizaizai.scholar.infrastructure.market.constants;

/**
 * 互斥类型
 * @author liaochongwei
 * @date 2021/6/9 16:00
 */
public enum MutexType {
    /**
     * 禁用（不参与互斥互斥）
     */
    DISABLED(){
        @Override
        public boolean shareTo(MutexType type) {
            return true;
        }
    },

    /**
     * 不互斥（与DISABLED、NONE、WITH_MUTEX同享）
     */
    NONE() {
        @Override
        public boolean shareTo(MutexType type) {
            return !type.equals(ALL);
        }
    },
    /**
     * 全部互斥（与DISABLED同享）
     */
    ALL() {
        @Override
        public boolean shareTo(MutexType type) {
            return type.equals(DISABLED);
        }
    },
    /**
     * 与互斥活动互斥（与DISABLED、NONE同享）
     */
    WITH_MUTEX() {
        @Override
        public boolean shareTo(MutexType type) {
            return !type.equals(WITH_MUTEX) && !type.equals(ALL);
        }
    };

    /**
     * 两者是否可以同享
     * @param type
     * @return boolean
     */
    public abstract boolean shareTo(MutexType type);
}
