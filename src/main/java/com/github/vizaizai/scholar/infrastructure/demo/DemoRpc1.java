package com.github.vizaizai.scholar.infrastructure.demo;

import com.github.vizaizai.scholar.web.dto.Book;
import feign.Headers;
import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

/**
 * @author liaochongwei
 * @date 2020/8/27 15:09
 */
public interface DemoRpc1 {

    @RequestLine("POST /books/getByAuthor")
    @Headers("Content-type:application/json")
    String listBooksByAuthor(@QueryMap Map<String, String> params, Book book);
}
