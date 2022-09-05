package com.github.niefy.modules.wx.handler;

import java.util.Map;

import com.github.niefy.modules.wx.port.WxMpService;
import com.github.niefy.modules.wx.port.WxMpXmlMessage;
import com.github.niefy.modules.wx.port.WxMpXmlOutMessage;
import com.github.niefy.modules.wx.port.WxSessionManager;
import com.github.niefy.modules.wx.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * @author Binary Wang
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {
    @Autowired
    WxUserService userService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
//        String openid = wxMessage.getFromUser();
//        this.logger.info("取消关注用户 OPENID: " + openid);
//        userService.unsubscribe(openid);
        return null;
    }

}
