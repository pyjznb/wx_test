package com.xmcc.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * 这里面配置的是跟网络相关的数据
 */
@Configuration//申明此类是一个配置类
public class DruidConfig {

    @Bean//交给spring管理
    @ConfigurationProperties(prefix = "spring.druid")//加载配置文件
    public DruidDataSource dataSource(){
        DruidDataSource source = new DruidDataSource();
        //设置参数
        source.setProxyFilters(Lists.newArrayList(statFilter()));
        return source;
    }


    //配置过滤数据
    @Bean
    public StatFilter statFilter(){
        StatFilter filter = new StatFilter();
        filter.setLogSlowSql(true);//日志显示sql
        filter.setSlowSqlMillis(5);//如果时间超过5秒 就认为当前这个sql语句是一个有问题的sql语句 是一个慢sql
        filter.setMergeSql(true);//如果查询两次 就把查询语句合并显示
        return filter;
    }

    //配置访问路径
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        //当前项目的访问路径：localhost:8888/sell/druid
        return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        /**
         * 只要是从根路径 druid 下的路径  ，后面随便写什么  都是它的访问路径
         */
    }
}
