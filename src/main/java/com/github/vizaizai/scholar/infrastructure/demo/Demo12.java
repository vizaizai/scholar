package com.github.vizaizai.scholar.infrastructure.demo;

import com.github.vizaizai.scholar.infrastructure.net.codec.JacksonEncoder;
import com.github.vizaizai.scholar.web.dto.Book;
import feign.Feign;
import feign.codec.StringDecoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liaochongwei
 * @date 2020/8/25 15:26
 */
public class Demo12 {
    public static void main(String[] args) {


        DemoRpc1 bookHttpService = Feign.builder()
                                               .decoder(new StringDecoder())
                                               .encoder(new JacksonEncoder())
                                               .target(DemoRpc1.class,"http://127.0.0.1:8888");
        Map<String, String> params = new HashMap<>();
        params.put("author","tom");

        Book book = new Book();
        book.setName("name");
        book.setAuthor("author");

        String listApiResult = bookHttpService.listBooksByAuthor(params,book);
        System.out.println(listApiResult);

    }
}
