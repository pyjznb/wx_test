package com.xmcc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat") //配置文件 获取前缀为wechat
@Data
public class WinXinProperties {

    private String appid;

    private String secret;
}
