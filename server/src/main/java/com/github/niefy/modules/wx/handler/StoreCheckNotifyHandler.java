package com.github.niefy.modules.wx.handler;

import java.util.Map;

import com.github.niefy.modules.wx.port.WxMpService;
import com.github.niefy.modules.wx.port.WxMpXmlMessage;
import com.github.niefy.modules.wx.port.WxMpXmlOutMessage;
import com.github.niefy.modules.wx.port.WxSessionManager;
import org.springframework.stereotype.Component;



/**
 * 门店审核事件处理
 *
 * @author 王彬 (Binary Wang)
 */
@Component
public class StoreCheckNotifyHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        // TODO 处理门店审核事件
        return null;
    }

}
