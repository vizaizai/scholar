package com.github.vizaizai.scholar.infrastructure.demo.arithmetic;

import com.github.vizaizai.retry.util.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 有一组数，求第k个最大值
 * @author liaochongwei
 * @date 2020/12/28 15:48
 */
public class Demo1 {

    public static void main(String[] args) {


        int num = 20000000;
        int k = 15000000;
        System.out.println("正在准备数据...");
        List<Integer> data = initData(num, k);
        List<Integer> dataK = new ArrayList<>(k);

        long startTime = System.currentTimeMillis();
        // 将第k个值读到数组里
        for (int i = 0; i < k; i++) {
            dataK.add(data.get(i));
        }
        // dataK降序排序
        long startSortTime = System.currentTimeMillis();
        dataK.sort(Comparator.reverseOrder());
        System.out.println("排序耗时：" + (System.currentTimeMillis() - startSortTime) + "ms");
        // 剩下的元素挨个比较，判断是否需要替换dataK里的元素
        for (int i = k; i < num; i++) {
            // 小于等于k，则忽略
            if (data.get(i) <= dataK.get(k-1)) {
                continue;
            }
            // 否则放在正确的位置
            for (int j = 0; j < k; j++) {
                if (data.get(i) > dataK.get(j)) {
                    dataK.set(j, data.get(i));
                    break;
                }
            }
        }

        System.out.println("执行耗时：" + (System.currentTimeMillis() - startTime) + "ms, 结果为:" + dataK.get(k - 1));
    }


    private static List<Integer> initData(int num, int k) {
        List<Integer> data = new ArrayList<>(num);
        long startTime = System.currentTimeMillis();
        List<Integer> dataK = new ArrayList<>(k);
        boolean sorted = false;
        for (int i = 0; i < num; i++) {
            int random = Utils.getRandom(num * 7, 0);
            data.add(random);
//            if (dataK.size()< k) {
//                dataK.add(random);
//            }else if (dataK.size() == k && !sorted) {
//                dataK.sort(Comparator.reverseOrder());
//                sorted = true;
//            }else {
//                // 放在正确的位置
//                for (int j = 0; j < k; j++) {
//                    if (data.get(i) > dataK.get(j)) {
//                        dataK.set(j, data.get(i));
//                        break;
//                    }
//                }
//            }
        }

        System.out.println("初始化数据耗时:" + (System.currentTimeMillis() - startTime) + "ms");
        //System.out.println("第" + k +"个最大值:" + dataK.get(k - 1));
        return data;
    }
}
