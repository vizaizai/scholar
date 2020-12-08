package com.github.vizaizai.scholar.infrastructure.guava;

import com.google.common.reflect.TypeToken;
import com.github.vizaizai.scholar.web.dto.Result;

/**
 * @author liaochongwei
 * @date 2020/9/2 14:47
 */
public class Demo01 {
    public static void main(String[] args) {
        TypeToken<Result<String>> typeToken = new TypeToken<Result<String>>() {};
        System.out.println(typeToken.getType());
    }
}
