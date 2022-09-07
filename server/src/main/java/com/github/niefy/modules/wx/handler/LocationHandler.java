package com.github.niefy.modules.wx.handler;



import com.github.niefy.modules.wx.port.WxMpService;
import com.github.niefy.modules.wx.port.WxMpXmlMessage;
import com.github.niefy.modules.wx.port.WxMpXmlOutMessage;
import com.github.niefy.modules.wx.port.WxSessionManager;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang
 */
@Component
public class LocationHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
//        if (wxMessage.getMsgType().equals(XmlMsgType.LOCATION)) {
//            //TODO 接收处理用户发送的地理位置消息
//
//        }
//
//        //上报地理位置事件
//        this.logger.info("\n上报地理位置 。。。 ");
//        this.logger.info("\n纬度 : " + wxMessage.getLatitude());
//        this.logger.info("\n经度 : " + wxMessage.getLongitude());
//        this.logger.info("\n精度 : " + wxMessage.getPrecision());
//
//        //TODO  可以将用户地理位置信息保存到本地数据库，以便以后使用

        return null;
    }

}
