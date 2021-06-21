package com.github.vizaizai.scholar.infrastructure.market;

import java.util.Collection;

/**
 * @author liaochongwei
 * @date 2021/6/21 11:06
 */
public class Utils {
    private Utils() {
    }

    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
    }

    public static boolean isNotEmpty(Collection coll) {
        return !Utils.isEmpty(coll);
    }
}
