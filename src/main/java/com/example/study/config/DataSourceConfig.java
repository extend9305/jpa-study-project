package com.example.study.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class DataSourceConfig.
 *
 * @author dongsulee
 * @date 2024/05/08
 */
@Slf4j
@Configuration
public class DataSourceConfig {
    @ConfigurationProperties(prefix = "spring.datasource.hikari.write")
    @Bean(name = "writeDataSource")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @ConfigurationProperties(prefix = "spring.datasource.hikari.read")
    @Bean(name = "readDataSource")
    public DataSource readDataSource() {return DataSourceBuilder.create().type(HikariDataSource.class).build();}

    @Bean(name = "routingDataSource" )
    public DataSource routingDataSource(
            @Qualifier("writeDataSource") DataSource writeDataSource,
            @Qualifier("readDataSource") DataSource readDataSource) {
        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();

        dataSourceMap.put(AppConstant.WRITE, writeDataSource);
        dataSourceMap.put(AppConstant.READ, readDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writeDataSource);

        return routingDataSource;
    }

    @DependsOn({"routingDataSource"})
    @Primary
    @Bean
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        log.info("datasource >>>>>>>>>>>>>>");
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

}
