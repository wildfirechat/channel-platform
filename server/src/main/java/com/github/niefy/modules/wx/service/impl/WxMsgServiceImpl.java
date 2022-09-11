package com.github.niefy.modules.wx.service.impl;

import cn.wildfirechat.messagecontentbuilder.TextMessageContentBuilder;
import cn.wildfirechat.pojos.SendMessageResult;
import cn.wildfirechat.sdk.ChannelServiceApi;
import cn.wildfirechat.sdk.model.IMResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import com.github.niefy.modules.wx.dao.WxMsgMapper;
import com.github.niefy.modules.wx.entity.WxMsg;
import com.github.niefy.modules.wx.service.WxAccountService;
import com.github.niefy.modules.wx.service.WxMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;


@Service("wxMsgService")
public class WxMsgServiceImpl extends ServiceImpl<WxMsgMapper, WxMsg> implements WxMsgService {

    @Autowired
    WxAccountService wxAccountService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String msgTypes = (String)params.get("msgTypes");
        String startTime = (String)params.get("startTime");
        String openid = (String)params.get("openid");
        String appid = (String) params.get("appid");
        IPage<WxMsg> page = this.page(
                new Query<WxMsg>().getPage(params),
                new QueryWrapper<WxMsg>()
                        .eq(StringUtils.hasText(appid), "appid", appid)
                        .in(StringUtils.hasText(msgTypes),"msg_type", Arrays.asList(msgTypes.split(",")))
                        .eq(StringUtils.hasText(openid),"openid",openid)
                        .ge(StringUtils.hasText(startTime),"create_time",startTime)
        );

        return new PageUtils(page);
    }

    @Override
    public IMResult<SendMessageResult> send(String appid, String textMessageContent) {
        TextMessageContentBuilder builder = TextMessageContentBuilder.newBuilder(textMessageContent);
            ChannelServiceApi api = wxAccountService.getApi(appid);
        try {
            return api.sendMessage(0, null, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 记录msg，异步入库
     * @param msg
     */
    @Override
    @Async
    public void addWxMsg(WxMsg msg) {
        this.baseMapper.insert(msg);
    }

}