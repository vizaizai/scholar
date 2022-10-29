package com.github.vizaizai.scholar.infrastructure.persistence.elastic;

import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.BookDo;
import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.OrderForEsDo;
import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.OrderItemDo;
import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.OrderItemForEsDo;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
@RunWith(SpringRunner.class)
class BookRepositoryTest {

    //@Autowired
    //private BookRepository bookRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;


    @Test
    void demo() {

        OrderForEsDo orderForEsDo = new OrderForEsDo();
        orderForEsDo.setId("id_4444444");
        orderForEsDo.setItems(Arrays.asList(new OrderItemForEsDo("item_2","订单项3")
                ,new OrderItemForEsDo("item_1","订单项4")));

        elasticsearchOperations.save(orderForEsDo);
        //bookRepository.save(bookDo1);
    }
    @Test
    void findByNameAndDescription() {

//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.matchQuery("name","Python编程"))
//                .withFields("name","id")
//                .withPageable(PageRequest.of(0, 10))
//                .build();


        //Page<BookDo> searchPage = bookRepository.search(searchQuery);
        //System.out.println(searchPage.getTotalElements());
        //List<BookDo> list = bookRepository.findByNameAndDescription("python编程", "python编程");
        //System.out.println(list.size());

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("items.name","132123"))
                .withPageable(PageRequest.of(0, 10))
                .build();

        SearchHits<OrderForEsDo> searchHits = elasticsearchOperations.search(searchQuery, OrderForEsDo.class);

        List<OrderForEsDo> collect = searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
        System.out.println(searchHits.getTotalHits());
        System.out.println(collect.size());

    }

    @Test
    void demo3() {

        BookDo bookDo1 = new BookDo();
        bookDo1.setId("book-160554514534");
        bookDo1.setName("Java核心技术 卷I 基础知识（原书第11版）");
        bookDo1.setDescription("PCore Java 第11版，Jolt大奖获奖作品，针对Java SE9、10、11全面更新，与Java编程思想、Effective Java、深入理解Java虚拟机 堪称：Java四大名著");


        BookDo bookDo2 = new BookDo();
        bookDo2.setId("book-110554574513");
        bookDo2.setName("Java从入门到精通（第5版）");
        bookDo2.setDescription("297个应用实例+37个典型应用+30小时教学视频+海量开发资源库，丛书累计销量200多万册");

        elasticsearchOperations.save(bookDo1, bookDo2);
    }
    @Test
    void demo2() {
        Query searchQuery = new NativeSearchQueryBuilder()
                                    .withQuery(QueryBuilders
                                                    .boolQuery()
                                                        .must(QueryBuilders.queryStringQuery("我想学习编程").field("name").field("description"))
                                    )
                                    .withFields("name","id","description")
                                    .withPageable(PageRequest.of(0, 10))
                                    .build();
        SearchHits<BookDo> searchHits = elasticsearchOperations.search(searchQuery, BookDo.class);

        List<BookDo> collect = searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
        System.out.println(searchHits.getTotalHits());
        System.out.println(collect.size());

    }
}