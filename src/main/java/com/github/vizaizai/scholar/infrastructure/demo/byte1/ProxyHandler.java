package com.github.vizaizai.scholar.infrastructure.demo.byte1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author liaochongwei
 * @date 2020/12/30 11:11
 */
public class ProxyHandler implements InvocationHandler {


    @SuppressWarnings("unchecked")
    public static  <T> T getProxy(Class<T> targetClazz) {
        return (T)Proxy.newProxyInstance(targetClazz.getClassLoader(), new Class[]{ targetClazz }, new ProxyHandler());
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return Math.pow((double)args[0],(double)args[1]);
    }
}
