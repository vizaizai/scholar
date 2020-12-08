package com.github.vizaizai.scholar.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author liaochongwei
 * @date 2020/8/14 10:59
 */
@Getter
@Setter
public class User {

    private String id;

    private String name;

    private LocalDate birthday;

    private BigDecimal balance;

    private LocalDateTime createTime;
}
