package com.github.niefy.modules.wx.entity;

import cn.wildfirechat.pojos.OutputMessageData;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.niefy.modules.wx.port.WxConsts;
import com.github.niefy.modules.wx.port.WxMpConfigStorageHolder;
import com.github.niefy.modules.wx.port.WxMpXmlMessage;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 消息
 *
 * @author niefy
 * @date 2020-05-14 17:28:34
 */
@Data
@TableName("wx_msg")
public class WxMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	private String appid;
	/**
	 * 用户ID
	 */
	private String openid;
	/**
	 * 消息方向
	 */
	private byte inOut;
	/**
	 * 消息类型
	 */
	private String msgType;
	/**
	 * 消息详情
	 */
	private JSONObject detail;
	/**
	 * 创建时间
	 */
	private Date createTime;

	public static class WxMsgInOut{
		static final byte IN=0;
		static final byte OUT=1;
	}

	public WxMsg() {
	}
	public WxMsg(OutputMessageData messageData, boolean in) {
		this.openid = messageData.getSender();
		this.appid = messageData.getConv().getTarget();
		this.inOut = in ? WxMsgInOut.IN : WxMsgInOut.OUT;
		this.createTime = new Date(messageData.getTimestamp());
		this.detail = new JSONObject();
		this.detail.put("type", messageData.getPayload().getType());
		if (messageData.getPayload().getSearchableContent() != null) {
			this.detail.put("searchableContent", messageData.getPayload().getSearchableContent());
		}

		if (messageData.getPayload().getPushContent() != null)
			this.detail.put("pushContent", messageData.getPayload().getPushContent());
		if (messageData.getPayload().getPushData() != null)
			this.detail.put("pushData", messageData.getPayload().getPushData());
		if (messageData.getPayload().getContent() != null)
			this.detail.put("content", messageData.getPayload().getContent());
		if (messageData.getPayload().getBase64edData() != null)
			this.detail.put("base64edData", messageData.getPayload().getBase64edData());
		if (messageData.getPayload().getMediaType() > 0) {
			this.detail.put("mediaType", messageData.getPayload().getMediaType());
			this.detail.put("remoteMediaUrl", messageData.getPayload().getRemoteMediaUrl());
		}
		this.detail.put("persistFlag", messageData.getPayload().getPersistFlag());
		if(messageData.getPayload().getExpireDuration() > 0) {
			this.detail.put("expireDuration", messageData.getPayload().getExpireDuration());
		}
		if(messageData.getPayload().getMentionedType() > 0) {
			this.detail.put("mentionedType", messageData.getPayload().getMentionedType());
			if (messageData.getPayload().getMentionedTarget() != null && !messageData.getPayload().getMentionedTarget().isEmpty())
				this.detail.put("mentionedTarget", messageData.getPayload().getMentionedTarget());
		}
		this.detail.put("extra", messageData.getPayload().getExtra());

		if (messageData.getPayload().getType() == 1) {
			this.msgType = WxConsts.XmlMsgType.TEXT;
			this.detail.put("content", messageData.getPayload().getSearchableContent());
		} else if (messageData.getPayload().getType() == 2) {
			this.msgType = WxConsts.XmlMsgType.VOICE;
		} else if (messageData.getPayload().getType() == 3) {
			this.detail.put("picUrl",messageData.getPayload().getRemoteMediaUrl());
			this.msgType = WxConsts.XmlMsgType.IMAGE;
		} else if (messageData.getPayload().getType() == 4) {
			this.msgType = WxConsts.XmlMsgType.LOCATION;
		} else if (messageData.getPayload().getType() == 5) {
			this.msgType = WxConsts.XmlMsgType.FILE;
		} else if (messageData.getPayload().getType() == 6) {
			this.msgType = WxConsts.XmlMsgType.VIDEO;
		} else if (messageData.getPayload().getType() == 7) {
			this.msgType = WxConsts.XmlMsgType.IMAGE;
		} else if (messageData.getPayload().getType() == 8) {
			this.msgType = WxConsts.XmlMsgType.LINK;
		} else if (messageData.getPayload().getType() == 13) {
			this.msgType = WxConsts.XmlMsgType.NEWS;
		} else if (messageData.getPayload().getType() == 73) {
			this.msgType = WxConsts.XmlMsgType.EVENT;
            String content = messageData.getPayload().getContent();
            Map<String, Object> obj = (Map<String, Object>) JSONObject.parse(content);
            this.detail.put("eventKey", obj.get("name"));
		} else {
			this.msgType = WxConsts.XmlMsgType.TEXT;
		}
	}

	public WxMsg(WxMpXmlMessage wxMessage) {
		this.openid=wxMessage.getFromUser();
		this.appid= WxMpConfigStorageHolder.get();
		this.inOut = WxMsgInOut.IN;
		this.msgType = wxMessage.getMsgType();
		this.detail = new JSONObject();
		Long createTime = wxMessage.getCreateTime();
		this.createTime = createTime==null?new Date():new Date(createTime*1000);
		if(WxConsts.XmlMsgType.TEXT.equals(this.msgType)){
			this.detail.put("content",wxMessage.getContent());
		}else if(WxConsts.XmlMsgType.IMAGE.equals(this.msgType)){
			this.detail.put("picUrl",wxMessage.getPicUrl());
			this.detail.put("mediaId",wxMessage.getMediaId());
		}else if(WxConsts.XmlMsgType.VOICE.equals(this.msgType)){
			this.detail.put("format",wxMessage.getFormat());
			this.detail.put("mediaId",wxMessage.getMediaId());
		}else if(WxConsts.XmlMsgType.VIDEO.equals(this.msgType) ||
				WxConsts.XmlMsgType.SHORTVIDEO.equals(this.msgType)){
			this.detail.put("thumbMediaId",wxMessage.getThumbMediaId());
			this.detail.put("mediaId",wxMessage.getMediaId());
		}else if(WxConsts.XmlMsgType.LOCATION.equals(this.msgType)){
			this.detail.put("locationX",wxMessage.getLocationX());
			this.detail.put("locationY",wxMessage.getLocationY());
			this.detail.put("scale",wxMessage.getScale());
			this.detail.put("label",wxMessage.getLabel());
		}else if(WxConsts.XmlMsgType.LINK.equals(this.msgType)){
			this.detail.put("title",wxMessage.getTitle());
			this.detail.put("description",wxMessage.getDescription());
			this.detail.put("url",wxMessage.getUrl());
		}else if(WxConsts.XmlMsgType.EVENT.equals(this.msgType)){
			this.detail.put("event",wxMessage.getEvent());
			this.detail.put("eventKey",wxMessage.getEventKey());
		}
	}
	public static WxMsg buildOutMsg(String appid, String msgType,String openid,JSONObject detail){
		WxMsg wxMsg = new WxMsg();
		wxMsg.appid= appid;
		wxMsg.msgType = msgType;
		wxMsg.openid = openid;
		wxMsg.detail = detail;
		wxMsg.createTime=new Date();
		wxMsg.inOut = WxMsgInOut.OUT;
		return wxMsg;
	}
}
