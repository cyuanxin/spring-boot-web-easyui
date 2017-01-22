package com.zyx.admin.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by layne.luo on 2016/8/24.
 */
@Configuration
//@MapperScan(basePackages = {"com.zyx.admin.mapper"} ,sqlSessionFactoryRef="mybatisSqlSessionFactory")
@EnableTransactionManagement
public class DBConfig {

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix="spring.datasource.core", ignoreInvalidFields = true)
    public DataSource derivedDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }


    @Bean(name="scheduler")
    public SchedulerFactoryBean schedulerFactoryBean()
            throws Exception {

        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        schedulerFactoryBean.setDataSource(dataSource);
//        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");

        Resource resource = new PathMatchingResourcePatternResolver().getResource("quartz.properties");
        schedulerFactoryBean.setConfigLocation(resource);


        return schedulerFactoryBean;

    }




}
