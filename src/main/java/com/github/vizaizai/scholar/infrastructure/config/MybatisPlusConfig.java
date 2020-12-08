package com.github.vizaizai.scholar.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liaochongwei
 * @date 2020/12/4 16:21
 */
@Configuration
@MapperScan("com.github.vizaizai.scholar.infrastructure.persistence.database.**")
@EnableTransactionManagement
public class MybatisPlusConfig {

}
