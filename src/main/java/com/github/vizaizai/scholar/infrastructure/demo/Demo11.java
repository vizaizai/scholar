package com.github.vizaizai.scholar.infrastructure.demo;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;

/**
 * @author liaochongwei
 * @date 2020/8/14 11:41
 */
@Slf4j
public class Demo11 {
    public static void main(String[] args) {
        System.out.println(new DecimalFormat("#.00").format(3.14554));
//
//
//        Mono.just("Tom")
//            .map(e-> {
//                log.debug("---------1");
//                return e.toUpperCase();
//            })
//            .map(e-> {
//                log.debug("----------2");
//                return "hello," + e + "!";
//            })
//            .subscribe(System.out::println);
//
//
//        Flux<String> flux1 = Flux.just("1", "2", "3", "4");
//                                 //.delayElements(Duration.ofMillis(500));
//
//        flux1.subscribe(System.out::println);
//
//        Flux<String> flux2 = Flux.just("11", "22", "33")
//                                 .delaySubscription(Duration.ofMillis(100))
//                                 .delayElements(Duration.ofMillis(500));
//
//        Flux<String> flux = flux1.mergeWith(flux2);
//
//        flux.subscribe(System.out::println);
//
//
//
//        System.out.println("------------------");
//        String[] words = new String[]{"Hello","World"};
//        List<String> a = Arrays.stream(words)
//                .map(word -> word.split(""))
//                .flatMap(Arrays::stream)
//                .distinct()
//                .collect(toList());
//        a.forEach(System.out::println);
    }
}
