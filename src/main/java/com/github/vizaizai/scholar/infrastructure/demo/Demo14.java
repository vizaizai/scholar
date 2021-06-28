package com.github.vizaizai.scholar.infrastructure.demo;

import org.springframework.beans.BeanUtils;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

/**
 *
 * if(a==1且a==2且a==3)，有没有可能为true？
 * @author liaochongwei
 * @date 2021/5/26 14:35
 */
public class Demo14 {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Class cache = Integer.class.getDeclaredClasses()[0];
        Field c = cache.getDeclaredField("cache");
        c.setAccessible(true);
        Integer[] array = (Integer[]) c.get(cache);
        // array[129] is 1
        array[130] = array[129];
        // Set 2 to be 1
        array[131] = array[129];
        // Set 3 to be 1
        Integer a = 1;
        if(a == (Integer)1 && a == (Integer)2 && a == (Integer)3){
            System.out.println("Success");
        }


        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        String efw = restTemplate.patchForObject("http://127.0.0.1:8888/", "efw", String.class);
        System.out.println(efw);


    }
}
