package com.github.vizaizai.scholar.infrastructure.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @author liaochongwei
 * @date 2020/12/4 17:15
 */
@Configuration
@Slf4j
public class ShardingDataSourceConfig {

    @Bean
    public DataSource dataSource() throws SQLException {

        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置第一个数据源
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://127.0.0.1:3306/ds1?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false");
        dataSource1.setUsername("root");
        dataSource1.setPassword("123456");
        dataSourceMap.put("ds1", dataSource1);

        // 配置第二个数据源
        DruidDataSource dataSource2 = new DruidDataSource();
        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://127.0.0.1:3306/ds2?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false");
        dataSource2.setUsername("root");
        dataSource2.setPassword("123456");
        dataSourceMap.put("ds2", dataSource2);

        // 配置User表规则
        TableRuleConfiguration userRuleConfig = new TableRuleConfiguration("user","ds${1..2}.user_${1..2}");
        // 分库策略
        userRuleConfig.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new MyShardingAlgorithm("ds",1)));
        // 分表策略
        userRuleConfig.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new MyShardingAlgorithm("user_")));
        //.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "ds${id % 2}"));
        //.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order${order_id % 2}"));

        // 配置Order表规则
        TableRuleConfiguration orderRuleConfig = new TableRuleConfiguration("t_order","ds${1..2}.t_order_${1..2}");
        // 分库策略
        orderRuleConfig.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new MyShardingAlgorithm("ds", 1)));
        // 分表策略
        orderRuleConfig.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new MyShardingAlgorithm("t_order_")));

        // 配置OrderItem表规则
        TableRuleConfiguration orderItemRuleConfig = new TableRuleConfiguration("order_item","ds${1..2}.order_item_${1..2}");
        // 分库策略
        orderItemRuleConfig.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", new MyShardingAlgorithm("ds", 1)));
        // 分表策略
        orderItemRuleConfig.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", new MyShardingAlgorithm("order_item_")));



        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        // 默认分库策略
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new MyShardingAlgorithm("ds")));
        Collection<TableRuleConfiguration> tableRuleConfigs = shardingRuleConfig.getTableRuleConfigs();
        tableRuleConfigs.add(userRuleConfig);
        tableRuleConfigs.add(orderItemRuleConfig);
        tableRuleConfigs.add(orderRuleConfig);
        // 路由绑定
        shardingRuleConfig.getBindingTableGroups().addAll(Arrays.asList("t_order","order_item"));

        // 获取数据源对象
       return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());

    }

    public static class MyShardingAlgorithm implements PreciseShardingAlgorithm<String>{
        private final String name;
        private int type = 2;
        public MyShardingAlgorithm(String name) {
            this.name = name;
        }

        public MyShardingAlgorithm(String name, int type) {
            this.name = name;
            this.type = type;
        }

        @Override
        public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
            int hash;
            String value = preciseShardingValue.getValue();
            if (this.type == 1) {
                hash = Math.abs(value.hashCode());
            }else {
                hash = hash(value);
            }
            return this.name + ((hash & collection.size() - 1) + 1);
        }

        private static int hash(Object key) {
            int h;
            return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        }
    }


}
