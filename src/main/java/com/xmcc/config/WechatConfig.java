package com.xmcc.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;

import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WechatConfig {

    @Autowired
    private WinXinProperties winXinProperties;

    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage memoryConfigStorage = new WxMpInMemoryConfigStorage();//数据库

        memoryConfigStorage.setAppId("wxcec0b9e65c084712");
        memoryConfigStorage.setSecret("05a7e861c1985ced86af77fb8f7163bc");

        return memoryConfigStorage;
    }

}
