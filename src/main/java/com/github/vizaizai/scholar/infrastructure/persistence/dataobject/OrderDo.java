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
@TableName("t_order")
public class OrderDo {
    @TableId
    private String id;

    private String buyerId;

    private BigDecimal totalPrice;
}
