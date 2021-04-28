package com.github.vizaizai.scholar.web.dto;

import lombok.Data;

/**
 * @author liaochongwei
 * @date 2020/8/3 10:53
 */
@Data
public class Book {
    private String id;

    private String name;

    private String author;

    private String lang;

    public static Book create() {
        Book book = new Book();
        book.setId("11111233");
        book.setName("西游记");
        book.setAuthor("吴承恩");
        book.setLang("chinese");
        return book;
    }
}
