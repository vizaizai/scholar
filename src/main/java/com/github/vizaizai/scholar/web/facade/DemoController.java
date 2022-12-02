package com.github.vizaizai.scholar.web.facade;

import com.alibaba.fastjson.JSON;
import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.BookDo;
import com.github.vizaizai.scholar.web.dto.Result;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liaochongwei
 * @date 2020/7/28 17:57
 */
@RestController
@RequestMapping("/books")
public class DemoController {


    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @PostMapping("/batch")
    public Result<Void> addBooks(@RequestBody List<BookDo> bookDos,HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            String header = request.getHeader(s);
            System.out.println(s + " -> " + header);
        }
        System.out.println(JSON.toJSONString(bookDos));
        return Result.ok();
    }

    @PostMapping
    public Result<Void> addBook(@RequestBody BookDo bookDo) {
        // 创建索引和映射
        IndexOperations indexOps = elasticsearchOperations.indexOps(BookDo.class);
        if (!indexOps.exists()) {
            indexOps.createMapping();
        }
        elasticsearchOperations.save(bookDo);

        return Result.ok();
    }

    @GetMapping
    public Result<List<BookDo>> listAll() {
        Query searchQuery = new NativeSearchQueryBuilder()
                                .withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC))
                                .build();
        SearchHits<BookDo> searchHits = elasticsearchOperations.search(searchQuery, BookDo.class);
        return Result.ok(searchHits.get().map(SearchHit::getContent).collect(Collectors.toList()));
    }

    @PutMapping
    public Result<Void> editBook(@RequestBody BookDo bookDo) {
        elasticsearchOperations.save(bookDo);
        return Result.ok();
    }

    @GetMapping("/{id}")
    public Result<BookDo> getById(@PathVariable String id) {
        return Result.ok(elasticsearchOperations.get(id,BookDo.class));
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteBook(@PathVariable String id) {
        return Result.ok(elasticsearchOperations.delete(id,BookDo.class));
    }

    @GetMapping("/search")
    public Result<List<BookDo>> search(@RequestParam String keyword, HttpServletRequest request) {
        System.out.println(request.getHeader("client"));
        Query searchQuery = new NativeSearchQueryBuilder()
                                .withQuery(QueryBuilders
                                            .boolQuery()
                                            .must(QueryBuilders.queryStringQuery(keyword).field("name").field("description"))
                                )
                                .withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC))
                                .build();
        SearchHits<BookDo> searchHits = elasticsearchOperations.search(searchQuery, BookDo.class);
        return Result.ok(searchHits.get().map(SearchHit::getContent).collect(Collectors.toList()));
    }

    @GetMapping("/bar")
    public List<String> bar() {
        return Arrays.asList("hello", "world");
    }

    @DeleteMapping("/body")
    public Result<String> deleteBookBody(@RequestBody String id) {
        System.out.println("id:" + id);
        return Result.ok();
    }
}
