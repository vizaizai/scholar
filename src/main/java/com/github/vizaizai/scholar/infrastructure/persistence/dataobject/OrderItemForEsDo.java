package com.github.vizaizai.scholar.infrastructure.persistence.dataobject;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 书数据类
 * @author liaochongwei
 * @date 2020/7/23 15:09
 */
@Data
public class OrderItemForEsDo {

    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    public OrderItemForEsDo() {
    }

    public OrderItemForEsDo(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
