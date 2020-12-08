package com.github.vizaizai.scholar.infrastructure.persistence.dataobject;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

/**
 * 书数据类
 * @author liaochongwei
 * @date 2020/7/23 15:09
 */
@Data
@Document(indexName = "books")
public class BookDo {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    /**
     * 价格
     */
    @Field(type = FieldType.Double)
    private BigDecimal price;
    /**
     * 书名
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String name;
    /**
     * 作者
     */
    @Field(type = FieldType.Keyword)
    private String author;
    /**
     * 描述
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String description;
}
