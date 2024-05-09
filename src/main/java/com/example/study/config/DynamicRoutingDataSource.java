package com.example.study.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

import static org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly;

/**
 * The Class DynamicRoutingDataSource.
 *
 * @author dongsulee
 * @date 2024/05/08
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);

    }

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = isCurrentTransactionReadOnly() ? AppConstant.READ : AppConstant.WRITE;
        log.info(">>>>>> current data source : {}", dataSourceName);
        return dataSourceName;
    }
}
