package com.github.vizaizai.scholar.infrastructure.persistence.dataobject;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.List;

/**
 * 书数据类
 * @author liaochongwei
 * @date 2020/7/23 15:09
 */
@Data
@Document(indexName = "idx_order")
public class OrderForEsDo {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Nested)
    private List<OrderItemForEsDo> items;
}
