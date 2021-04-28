package com.github.vizaizai.scholar.infrastructure.demo.type;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

/**
 * @author liaochongwei
 * @date 2021/3/17 15:40
 */
public class TestTypeVer <K extends Number & Serializable, T>{

    //K有指定了上边界Number
    private K key;
    //T没有指定上边界，其默认上边界为Object
    private T value;

    public static void main(String[] args){
      Type[] types = TestTypeVer.class.getTypeParameters();
      for (Type type : types){
            TypeVariable t = (TypeVariable) type;
            //输出上边界
            System.out.println("--getBounds()-- " + Arrays.toString(t.getBounds()));
            //输出名称
            System.out.println("--getName()--" + t.getName());
            //输出所在的类的类型
            System.out.println("--getGenericDeclaration()--" + t.getGenericDeclaration());

          System.out.println("===================");
      }
    }
}
