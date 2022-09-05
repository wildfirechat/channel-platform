package com.github.niefy.modules.wx.config;

import com.github.niefy.modules.wx.port.WxMpService;
import com.github.niefy.modules.wx.port.WxMpServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class WxMpServiceConfiguration {

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService =  new WxMpServiceImpl();
//        wxMpService.setMaxRetryTimes(3);
        return wxMpService;
    }
}
