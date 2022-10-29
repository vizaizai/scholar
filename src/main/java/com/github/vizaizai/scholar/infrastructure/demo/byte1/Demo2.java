package com.github.vizaizai.scholar.infrastructure.demo.byte1;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author liaochongwei
 * @date 2020/12/30 11:10
 */
public class Demo2 {
    public static void main(String[] args) throws Throwable{


        /* jdk */
        TimeConsuming.mark("jdk-create");
        ProxyDemo proxyJdk = ProxyHandler.getProxy(ProxyDemo.class);
        TimeConsuming.printMS("jdk-create");

        TimeConsuming.mark("byte-create");
        DynamicType.Unloaded<?> dynamicType= new ByteBuddy(ClassFileVersion.JAVA_V8)
                .subclass(Object.class)
                .implement(ProxyDemo.class)
                .name("com.xxx.ProxyDemoImpl")
                .method(ElementMatchers.isAbstract())
                .intercept(MethodDelegation.to(new SingerAgentInterceptor()))
                .make();
        TimeConsuming.printMS("byte-create");
        //dynamicType.saveIn(new File("./target/"));
        ProxyDemo proxyByte = (ProxyDemo)dynamicType
                .load(Demo2.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded()
                .newInstance();
        TimeConsuming.printMS("byte-create");

        int n = 10000000;



        TimeConsuming.mark("jdk-run");
        for (int i = 0; i < n; i++) {
            proxyJdk.power(2,3);
        }
        TimeConsuming.printMS("jdk-run");



        TimeConsuming.mark("byte-run");
        for (int i = 0; i < n; i++) {
            proxyByte.power(2,3);
        }
        TimeConsuming.printMS("byte-run");



    }


    public static class SingerAgentInterceptor {

        // @SuperCall Callable<?> callable
        @RuntimeType
        public Object interceptor(@This Object proxy, @Origin Method method,
                                  @AllArguments Object[] args) throws Exception {
            return Math.pow((double)args[0],(double)args[1]);
        }
    }





}
