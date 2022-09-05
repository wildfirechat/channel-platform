package com.github.niefy.modules.wx.service;

import com.github.niefy.modules.wx.form.TemplateMsgBatchForm;
import com.github.niefy.modules.wx.port.WxMpTemplateMessage;

public interface TemplateMsgService {
    /**
     * 发送微信模版消息
     */
    void sendTemplateMsg(WxMpTemplateMessage msg, String appid);

    /**
     * 批量消息发送
     * @param form
     * @param appid
     */
    void sendMsgBatch(TemplateMsgBatchForm form,String appid);
}
