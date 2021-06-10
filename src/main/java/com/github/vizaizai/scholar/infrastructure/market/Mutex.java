package com.github.vizaizai.scholar.infrastructure.market;

import com.github.vizaizai.scholar.infrastructure.market.constants.MutexResolveType;
import com.github.vizaizai.scholar.infrastructure.market.constants.MutexType;

/**
 * 互斥设置
 * @author liaochongwei
 * @date 2021/6/9 11:41
 */
public class Mutex {

    /**
     * 互斥类型
     */
    private MutexType type;
    /**
     * 互斥处理方案
     */
    private MutexResolveType resolveType;

    public static Mutex createNone() {
        Mutex mutex = new Mutex();
        mutex.type = MutexType.NONE;
        return mutex;
    }

    public static Mutex create(MutexType type, MutexResolveType resolveType) {
        Mutex mutex = new Mutex();
        mutex.type = type;
        mutex.resolveType = resolveType;
        return mutex;
    }

    public MutexType getType() {
        return type;
    }

    public void setType(MutexType type) {
        this.type = type;
    }

    public MutexResolveType getResolveType() {
        return resolveType;
    }

    public void setResolveType(MutexResolveType resolveType) {
        this.resolveType = resolveType;
    }
}
