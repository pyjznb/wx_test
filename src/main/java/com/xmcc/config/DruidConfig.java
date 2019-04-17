package com.xmcc.config;


import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;


@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.druid")//加载配置文件
    public DruidDataSource dataSource(){
        DruidDataSource source = new DruidDataSource();
        ArrayList<Filter> list = new ArrayList<>();
        list.add(statFilter());
        //设置参数
        source.setProxyFilters(list);
        return source;
    }

    @Bean
    public StatFilter statFilter(){
        StatFilter filter = new StatFilter();
        filter.setLogSlowSql(true);
        filter.setSlowSqlMillis(5);
        filter.setMergeSql(true);
        return filter;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        //当前访问路径：localhost：8888/sell/druid
        return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
    }
}
