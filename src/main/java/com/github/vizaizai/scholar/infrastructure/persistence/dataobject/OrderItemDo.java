package com.github.vizaizai.scholar.infrastructure.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author liaochongwei
 * @date 2020/12/4 16:17
 */
@Data
@TableName("order_item")
public class OrderItemDo {
    @TableId
    private String id;

    private String orderId;

    private BigDecimal price;

    private Integer num;
}
