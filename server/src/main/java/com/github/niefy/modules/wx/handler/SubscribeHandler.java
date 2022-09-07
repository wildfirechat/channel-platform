package com.github.niefy.modules.wx.handler;

import java.util.Map;

import com.github.niefy.modules.wx.port.WxMpService;
import com.github.niefy.modules.wx.port.WxMpXmlMessage;
import com.github.niefy.modules.wx.port.WxMpXmlOutMessage;
import com.github.niefy.modules.wx.port.WxSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.niefy.modules.wx.service.WxUserService;
import com.github.niefy.modules.wx.service.MsgReplyService;

import org.springframework.util.StringUtils;

/**
 * @author Binary Wang
 */
@Component
public class SubscribeHandler extends AbstractHandler {
    @Autowired
    MsgReplyService msgReplyService;
    @Autowired
    WxUserService userService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {

//        this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser() + "，事件：" + wxMessage.getEventKey());
//        String appid = WxMpConfigStorageHolder.get();
//        this.logger.info("appid:{}",appid);
//        userService.refreshUserInfo(wxMessage.getFromUser(),appid);
//
//        msgReplyService.tryAutoReply(appid, true, wxMessage.getFromUser(), wxMessage.getEvent());
//
//        if (StringUtils.hasText(wxMessage.getEventKey())) {// 处理特殊事件，如用户扫描带参二维码关注
//            msgReplyService.tryAutoReply(appid, true, wxMessage.getFromUser(), wxMessage.getEventKey());
//        }
        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    protected WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) {
//        this.logger.info("特殊请求-新关注用户 OPENID: " + wxMessage.getFromUser());
//        //对关注事件和扫码事件分别处理
//        String appid = WxMpConfigStorageHolder.get();
//        userService.refreshUserInfo(wxMessage.getFromUser(),appid);
//        msgReplyService.tryAutoReply(appid, true, wxMessage.getFromUser(), wxMessage.getEvent());
//        if (StringUtils.hasText(wxMessage.getEventKey())) {
//            msgReplyService.tryAutoReply(appid, true, wxMessage.getFromUser(), wxMessage.getEventKey());
//        }
        return null;
    }

}
