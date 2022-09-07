package com.github.niefy.modules.wx.handler;

import java.util.Map;

import com.github.niefy.modules.wx.port.WxMpService;
import com.github.niefy.modules.wx.port.WxMpXmlMessage;
import com.github.niefy.modules.wx.port.WxMpXmlOutMessage;
import com.github.niefy.modules.wx.port.WxSessionManager;
import com.github.niefy.modules.wx.service.MsgReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * @author Binary Wang
 */
@Component
public class MenuHandler extends AbstractHandler {
    @Autowired
    MsgReplyService msgReplyService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
//        if (WxConsts.EventType.VIEW.equals(wxMessage.getEvent())) {
//            return null;
//        }
//        String appid = WxMpConfigStorageHolder.get();
//        logger.info("菜单事件：" + wxMessage.getEventKey());
//        msgReplyService.tryAutoReply(appid, true, wxMessage.getFromUser(), wxMessage.getEventKey());
        return null;
    }


}
