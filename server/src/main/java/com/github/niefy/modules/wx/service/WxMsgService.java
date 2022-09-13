package com.github.niefy.modules.wx.service;

import cn.wildfirechat.pojos.SendMessageResult;
import cn.wildfirechat.sdk.model.IMResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.modules.wx.entity.WxMsg;

import java.util.Map;

/**
 * 消息
 *
 * @author niefy
 * @date 2020-05-14 17:28:34
 */
public interface WxMsgService extends IService<WxMsg> {
    /**
     * 分页查询用户数据
     * @param params 查询参数
     * @return PageUtils 分页结果
     */
    PageUtils queryPage(Map<String, Object> params);

    IMResult<SendMessageResult> send(String appid, String textMessageContent);

    /**
     * 记录msg，异步入库
     * @param msg
     */
    void addWxMsg(WxMsg msg);
}

