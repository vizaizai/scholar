package com.github.vizaizai.scholar.infrastructure.demo.byte1;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;

/**
 * @author liaochongwei
 * @date 2020/12/30 9:39
 */
public class Demo1 {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("org.itstack.demo.bytebuddy.HelloWorld")
                .defineMethod("main", String.class, Modifier.PUBLIC + Modifier.STATIC)
                .withParameter(String[].class, "args")
                .intercept(FixedValue.value("Hello World!"))
                .make();

        // 输出类字节码
        outputClazz(dynamicType.getBytes());
    }


    public static void outputClazz(byte[] bytes) {
        FileOutputStream out = null;
        try {
            String pathName = Demo1.class.getResource("/").getPath() + "AutoByte.class";
            out = new FileOutputStream(new File(pathName));
            System.out.println("类输出路径：" + pathName);
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
