package com.github.vizaizai.scholar.infrastructure.persistence.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author liaochongwei
 * @date 2020/12/4 16:17
 */
@Data
@TableName("user")
public class UserDo {
    @TableId
    private String id;

    private String name;
}
