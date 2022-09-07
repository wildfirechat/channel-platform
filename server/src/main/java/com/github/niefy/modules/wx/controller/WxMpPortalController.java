package com.github.niefy.modules.wx.controller;

import cn.wildfirechat.pojos.OutputGetChannelInfo;
import cn.wildfirechat.pojos.OutputMessageData;
import cn.wildfirechat.pojos.OutputNotifyChannelSubscribeStatus;
import com.github.niefy.common.utils.Json;
import com.github.niefy.modules.wx.entity.WxMsg;
import com.github.niefy.modules.wx.service.MsgReplyService;
import com.github.niefy.modules.wx.service.WxMsgService;
import com.github.niefy.modules.wx.service.WxUserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信消息
 * @author Binary Wang
 */
@RequiredArgsConstructor
@RestController
@Api(tags = {"微信消息 - 腾讯会调用"})
public class WxMpPortalController {
//    private final WxMpService wxService;
//    private final WxMpMessageRouter messageRouter;
    @Autowired
    WxUserService userService;

    @Autowired
    MsgReplyService msgReplyService;

    @Autowired
    WxMsgService wxMsgService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @PostMapping(path = "/{appid}/message", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "微信各类消息",notes = "公众号接入开发模式后才有效")
    public String post(@PathVariable String appid,
                       @RequestBody OutputMessageData event) {
//		logger.debug("\n接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
//						+ " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
//				openid, signature, encType, msgSignature, timestamp, nonce, requestBody);
//        this.wxService.switchoverTo(appid);
//        if (!wxService.checkSignature(timestamp, nonce, signature)) {
//            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
//        }

        try {
            this.logger.debug("\n接收到请求消息，内容：{}", Json.toJsonString(event));
            if (event.getPayload().getType() == 91) {
                this.logger.debug("\n忽略类型91的消息");
                return "ok";
            }
            wxMsgService.addWxMsg(new WxMsg(event, true));
        } catch (Exception e) {
            this.logger.error("记录消息异常",e);
        }

        if (event.getPayload().getType() == 1) { //文本，自动回复
            String textContent = event.getPayload().getSearchableContent();
            String fromUser = event.getSender();
            msgReplyService.tryAutoReply(appid,false, fromUser, textContent, true);
        } else if (event.getPayload().getType() == 73) { //菜单事件，只处理点击
            OutputGetChannelInfo.OutputMenu menu = new Gson().fromJson(event.getPayload().getContent(), OutputGetChannelInfo.OutputMenu.class);
            if (menu.type.equals("click")) {
                msgReplyService.tryAutoReply(appid, true, event.getSender(), menu.key, false);
            }
        }

        return "ok";
    }

    @PostMapping(path = "/{appid}/subscribe", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "微信各类消息",notes = "公众号接入开发模式后才有效")
    public String subscribe(@PathVariable String appid,
                            @RequestBody OutputNotifyChannelSubscribeStatus event) {
        try {
            this.logger.debug("\n接收到请求消息，内容：{}", Json.toJsonString(event));

            this.logger.info("新关注用户 OPENID: " + event.getUserId() + "，事件：" + event.getStatus());
            if (event.getStatus() > 0) {
                userService.refreshUserInfo(event.getUserId(), appid);
                msgReplyService.tryAutoReply(appid, true, event.getUserId(), "subscribe", false);
            } else {
            userService.unsubscribe(event.getUserId());
            }
        } catch (Exception e) {
            this.logger.error("记录消息异常",e);
        }

        return "ok";
    }
}
