package com.github.niefy.modules.wx.config;

import com.github.niefy.modules.wx.handler.*;
import com.github.niefy.modules.wx.port.WxConsts;
import com.github.niefy.modules.wx.port.WxMpMessageRouter;
import com.github.niefy.modules.wx.port.WxMpService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.niefy.modules.wx.port.WxConsts.EventType.*;
import static com.github.niefy.modules.wx.port.WxConsts.XmlMsgType.EVENT;

@RequiredArgsConstructor
@Configuration
public class WxMpMessageRouterConfiguration {
    private final LogHandler logHandler;
    private final NullHandler nullHandler;
    private final KfSessionHandler kfSessionHandler;
    private final MenuHandler menuHandler;
    private final MsgHandler msgHandler;
    private final ScanHandler scanHandler;
    private final UnsubscribeHandler unsubscribeHandler;
    private final SubscribeHandler subscribeHandler;
    public static final String KF_CREATE_SESSION = "kf_create_session";

    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志
        newRouter.rule().async(false).handler(this.logHandler).next();

        // 接收客服会话管理事件
        newRouter.rule().async(false).msgType(EVENT).event(KF_CREATE_SESSION).handler(this.kfSessionHandler).end();
        // 自定义菜单事件
        newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.CLICK).handler(this.menuHandler).end();
        // 关注事件
        newRouter.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.subscribeHandler).end();
        // 取消关注事件
        newRouter.rule().async(false).msgType(EVENT).event(UNSUBSCRIBE).handler(this.unsubscribeHandler).end();
        //扫描带参二维码事件
        newRouter.rule().async(false).msgType(EVENT).event(SCAN).handler(this.scanHandler).end();
        //其他事件
        newRouter.rule().async(false).msgType(EVENT).handler(this.nullHandler).end();

        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }
}
