package com.github.vizaizai.scholar.infrastructure.demo;

import java.util.*;

/**
 * @author liaochongwei
 * @date 2020/8/31 15:18
 */
public class Demo13 {
    public static void main(String[] args) {
        System.out.println(String[].class.getTypeName());
        System.out.println(String.class.getTypeName());

        Deque<String> deque = new LinkedList<>();


        //Queue

        //Stack

        // 访问顺序 get/put
        Map<String,String> map = new LinkedHashMap<>(16,0.75f,true);
        map.put("1","1");
        map.put("2","2");
        map.put("3","3");
        map.put("4","4");

        map.get("1");
        System.out.println(map);



        // 优先级队列，实现了堆
        //PriorityQueue
    }
}
