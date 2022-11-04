package com.github.vizaizai.scholar.infrastructure.market2;

/**
 * 同享类型
 * @author liaochongwei
 * @date 2022/10/24 16:00
 */
public enum ShareType {
    /**
     * 禁用（不参与互斥互斥）
     */
    DISABLED(){
        @Override
        public boolean shareTo(ShareType type) {
            return true;
        }
    },

    /**
     * 同享 (与DISABLED、SHARE、ONLY_SHARE同享）
     */
    SHARE() {
        @Override
        public boolean shareTo(ShareType type) {
            return !type.equals(NON_SHARE);
        }
    },
    /**
     * 非同享（与DISABLED同享）
     */
    NON_SHARE() {
        @Override
        public boolean shareTo(ShareType type) {
            return type.equals(DISABLED);
        }
    },
    /**
     * 只与同享活动同享（与DISABLED、SHARE同享）
     */
    ONLY_SHARE() {
        @Override
        public boolean shareTo(ShareType type) {
            return type.equals(SHARE) || type.equals(DISABLED);
        }
    };

    /**
     * 两者是否可以同享
     * @param type
     * @return boolean
     */
    public abstract boolean shareTo(ShareType type);
}
