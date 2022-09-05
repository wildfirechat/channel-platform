package com.github.niefy.modules.wx.port;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Data
@Slf4j
public class WxMpXmlMessage implements Serializable {
    private static final long serialVersionUID = -3586245291677274914L;

    /**
     * 使用dom4j解析的存放所有xml属性和值的map.
     */
    private Map<String, Object> allFieldsMap;

    ///////////////////////
    // 以下都是微信推送过来的消息的xml的element所对应的属性
    ///////////////////////

    private String toUser;

    private String fromUser;

    private Long createTime;

    private String msgType;

    private String content;

    private Long menuId;

    private Long msgId;

    private String picUrl;

    private String mediaId;

    private String format;

    private String thumbMediaId;

    private Double locationX;

    private Double locationY;

    private Double scale;

    private String label;

    private String title;

    private String description;

    private String url;

    private String event;

    private String eventKey;

    private String ticket;

    private Double latitude;

    private Double longitude;

    private Double precision;

    private String recognition;

    private String unionId;

    ///////////////////////////////////////
    // 群发消息返回的结果
    ///////////////////////////////////////
    /**
     * 群发的消息ID
     */
    private Long massMsgId;
    /**
     * 群发的结果.
     */
    private String status;
    /**
     * group_id下粉丝数；或者openid_list中的粉丝数.
     */
    private Integer totalCount;
    /**
     * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数.
     * 原则上，filterCount = sentCount + errorCount
     */
    private Integer filterCount;
    /**
     * 发送成功的粉丝数.
     */
    private Integer sentCount;
    /**
     * 发送失败的粉丝数.
     */
    private Integer errorCount;

    ///////////////////////////////////////
    // 客服会话管理相关事件推送
    ///////////////////////////////////////
    /**
     * 创建或关闭客服会话时的客服帐号.
     */
    private String kfAccount;
    /**
     * 转接客服会话时的转入客服帐号.
     */
    private String toKfAccount;
    /**
     * 转接客服会话时的转出客服帐号.
     */
    private String fromKfAccount;

    ///////////////////////////////////////
    // 卡券相关事件推送
    ///////////////////////////////////////

    private String cardId;

    private String friendUserName;

    /**
     * 是否为转赠，1代表是，0代表否.
     */
    private Integer isGiveByFriend;

    private String userCardCode;

    private String oldUserCardCode;

    private Integer outerId;

    /**
     * 用户删除会员卡后可重新找回，当用户本次操作为找回时，该值为1，否则为0.
     */
    private String isRestoreMemberCard;

    /**
     * <pre>
     * 领取场景值，用于领取渠道数据统计。可在生成二维码接口及添加Addcard接口中自定义该字段的字符串值.
     * 核销卡券时：开发者发起核销时传入的自定义参数，用于进行核销渠道统计
     * 另外：
     * 官网文档中，微信卡券>>卡券事件推送>>2.7 进入会员卡事件推送 user_view_card
     * OuterStr：商户自定义二维码渠道参数，用于标识本次扫码打开会员卡来源来自于某个渠道值的二维码
     * </pre>
     */
    private String outerStr;

    /**
     * 是否转赠退回，0代表不是，1代表是.
     */
    private String isReturnBack;

    /**
     * 是否是群转赠，0代表不是，1代表是.
     */
    private String isChatRoom;

    /**
     * 核销来源.
     * 支持开发者统计API核销（FROM_API）、公众平台核销（FROM_MP）、卡券商户助手核销（FROM_MOBILE_HELPER）（核销员微信号）
     */
    private String consumeSource;

    /**
     * 门店名称.
     * 当前卡券核销的门店名称（只有通过自助核销和买单核销时才会出现该字段）
     */
    private String locationName;

    /**
     * 核销该卡券核销员的openid（只有通过卡券商户助手核销时才会出现）.
     */
    private String staffOpenId;

    /**
     * 自助核销时，用户输入的验证码.
     */
    private String verifyCode;

    /**
     * 自助核销时，用户输入的备注金额.
     */
    private String remarkAmount;

    /**
     * <pre>
     * 官网文档中，微信卡券>>卡券事件推送>>2.10 库存报警事件card_sku_remind
     * Detail：报警详细信息.
     * </pre>
     */
    private String detail;

    /**
     * <pre>
     * 官网文档中，微信卡券>>卡券事件推送>>2.9 会员卡内容更新事件 update_member_card
     * ModifyBonus：变动的积分值.
     * </pre>
     */
    private String modifyBonus;

    /**
     * <pre>
     * 官网文档中，微信卡券>>卡券事件推送>>2.9 会员卡内容更新事件 update_member_card
     * ModifyBalance：变动的余额值.
     * </pre>
     */
    private String modifyBalance;

    /**
     * <pre>
     * 官网文档中，微信卡券>>卡券事件推送>>2.6 买单事件推送 User_pay_from_pay_cell
     * TransId：微信支付交易订单号（只有使用买单功能核销的卡券才会出现）.
     * </pre>
     */
    private String transId;

    /**
     * <pre>
     * 官网文档中，微信卡券>>卡券事件推送>>2.6 买单事件推送 User_pay_from_pay_cell
     * LocationId：门店ID，当前卡券核销的门店ID（只有通过卡券商户助手和买单核销时才会出现）
     * </pre>
     */
    private String locationId;

    /**
     * <pre>
     * 官网文档中，微信卡券>>卡券事件推送>>2.6 买单事件推送 User_pay_from_pay_cell
     * Fee：实付金额，单位为分
     * </pre>
     */
    private String fee;

    /**
     * <pre>
     * 官网文档中，微信卡券>>卡券事件推送>>2.6 买单事件推送 User_pay_from_pay_cell
     * OriginalFee：应付金额，单位为分
     * </pre>
     */
    private String originalFee;

//
//    private ScanCodeInfo scanCodeInfo = new ScanCodeInfo();
//
//    //@XStreamAlias("SendPicsInfo")
//    //@JacksonXmlProperty(localName = "SendPicsInfo")
//    private SendPicsInfo sendPicsInfo = new SendPicsInfo();
//
//    //@XStreamAlias("SendLocationInfo")
//    //@JacksonXmlProperty(localName = "SendLocationInfo")
//    private SendLocationInfo sendLocationInfo = new SendLocationInfo();
//
//    //@XStreamAlias("ArticleUrlResult")
//    //@JacksonXmlProperty(localName = "ArticleUrlResult")
//    private ArticleUrlResult articleUrlResult = new ArticleUrlResult();

    /**
     * 审核不通过原因
     */
    private String refuseReason;

    /**
     * 是否为朋友推荐，0代表否，1代表是
     */
//    //@XStreamAlias("IsRecommendByFriend")
//    //@JacksonXmlProperty(localName = "IsRecommendByFriend")
    private String isRecommendByFriend;

    /**
     * 购买券点时，实际支付成功的时间
     */
//    //@XStreamAlias("PayFinishTime")
//    //@JacksonXmlProperty(localName = "PayFinishTime")
    private String payFinishTime;

    /**
     * 购买券点时，支付二维码的生成时间
     */
    //@XStreamAlias("CreateOrderTime")
    //@JacksonXmlProperty(localName = "CreateOrderTime")
    private String createOrderTime;

    /**
     * 购买券点时，支付二维码的生成时间
     */
    //@XStreamAlias("Desc")
    //@JacksonXmlProperty(localName = "Desc")
    private String desc;

    /**
     * 剩余免费券点数量
     */
    //@XStreamAlias("FreeCoinCount")
    //@JacksonXmlProperty(localName = "FreeCoinCount")
    private String freeCoinCount;

    /**
     * 剩余付费券点数量
     */
    //@XStreamAlias("PayCoinCount")
    //@JacksonXmlProperty(localName = "PayCoinCount")
    private String payCoinCount;

    /**
     * 本次变动的免费券点数量
     */
    //@XStreamAlias("RefundFreeCoinCount")
    //@JacksonXmlProperty(localName = "RefundFreeCoinCount")
    private String refundFreeCoinCount;

    /**
     * 本次变动的付费券点数量
     */
    //@XStreamAlias("RefundPayCoinCount")
    //@JacksonXmlProperty(localName = "RefundPayCoinCount")
    private String refundPayCoinCount;

    /**
     * <pre>
     *    所要拉取的订单类型 ORDER_TYPE_SYS_ADD 平台赠送券点 ORDER_TYPE_WXPAY 充值券点 ORDER_TYPE_REFUND 库存未使用回退券点
     *    ORDER_TYPE_REDUCE 券点兑换库存 ORDER_TYPE_SYS_REDUCE 平台扣减
     * </pre>
     */
    //@XStreamAlias("OrderType")
    //@JacksonXmlProperty(localName = "OrderType")
    private String orderType;

    /**
     * 系统备注，说明此次变动的缘由，如开通账户奖励、门店奖励、核销奖励以及充值、扣减。
     */
    //@XStreamAlias("Memo")
    //@JacksonXmlProperty(localName = "Memo")
    private String memo;

    /**
     * 所开发票的详情
     */
    //@XStreamAlias("ReceiptInfo")
    //@JacksonXmlProperty(localName = "ReceiptInfo")
    private String receiptInfo;

    ///////////////////////////////////////
    // 门店审核事件推送
    ///////////////////////////////////////
    /**
     * 商户自己内部ID，即字段中的sid.
     */
    //@XStreamAlias("UniqId")
    //@JacksonXmlProperty(localName = "UniqId")
    private String storeUniqId;

    /**
     * 微信的门店ID，微信内门店唯一标示ID.
     */
    //@XStreamAlias("PoiId")
    //@JacksonXmlProperty(localName = "PoiId")
    private String poiId;

    /**
     * 审核结果，成功succ 或失败fail.
     * <p>
     * 在商品审核结果推送时，verify_ok表示审核通过，verify_not_pass表示审核未通过。
     */
    //@XStreamAlias("Result")
    //@JacksonXmlProperty(localName = "Result")
    private String result;

    /**
     * 成功的通知信息，或审核失败的驳回理由.
     */
    //@XStreamAlias("msg")
    //@JacksonXmlProperty(localName = "msg")
    private String msg;

    ///////////////////////////////////////
    // 微信认证事件推送
    ///////////////////////////////////////
    /**
     * 资质认证成功/名称认证成功: 有效期 (整形)，指的是时间戳，将于该时间戳认证过期.
     * 年审通知: 有效期 (整形)，指的是时间戳，将于该时间戳认证过期，需尽快年审
     * 认证过期失效通知: 有效期 (整形)，指的是时间戳，表示已于该时间戳认证过期，需要重新发起微信认证
     */
    //@XStreamAlias("ExpiredTime")
    //@JacksonXmlProperty(localName = "ExpiredTime")
    private Long expiredTime;
    /**
     * 失败发生时间 (整形)，时间戳.
     */
    //@XStreamAlias("FailTime")
    //@JacksonXmlProperty(localName = "FailTime")
    private Long failTime;
    /**
     * 认证失败的原因.
     */
    //@XStreamAlias("FailReason")
    //@JacksonXmlProperty(localName = "FailReason")
    private String failReason;

    ///////////////////////////////////////
    // 微信小店 6.1订单付款通知
    ///////////////////////////////////////
    /**
     * 订单ID.
     */
    //@XStreamAlias("OrderId")
    //@XStreamConverter(value = XStreamCDataConverter.class)
    //@JacksonXmlProperty(localName = "OrderId")
    //@JacksonXmlCData
    private String orderId;

    /**
     * 订单状态.
     */
    //@XStreamAlias("OrderStatus")
    //@JacksonXmlProperty(localName = "OrderStatus")
    private String orderStatus;

    /**
     * 商品ID.
     */
    //@XStreamAlias("ProductId")
    //@XStreamConverter(value = XStreamCDataConverter.class)
    //@JacksonXmlProperty(localName = "ProductId")
    //@JacksonXmlCData
    private String productId;

    /**
     * 商品SKU信息.
     */
    //@XStreamAlias("SkuInfo")
    //@XStreamConverter(value = XStreamCDataConverter.class)
    //@JacksonXmlProperty(localName = "SkuInfo")
    //@JacksonXmlCData
    private String skuInfo;

    ///////////////////////////////////////
    // 微信硬件平台相关事件推送
    ///////////////////////////////////////
    /**
     * 设备类型.
     * 目前为"公众账号原始ID"
     */
    //@XStreamAlias("DeviceType")
    //@XStreamConverter(value = XStreamCDataConverter.class)
    //@JacksonXmlProperty(localName = "DeviceType")
    //@JacksonXmlCData
    private String deviceType;

    /**
     * 设备ID.
     * 第三方提供
     */
    //@XStreamAlias("DeviceID")
    //@XStreamConverter(value = XStreamCDataConverter.class)
    //@JacksonXmlProperty(localName = "DeviceID")
    //@JacksonXmlCData
    private String deviceId;

    /**
     * 微信客户端生成的session id，用于request和response对应，
     * 因此响应中该字段第三方需要原封不变的带回
     */
    //@XStreamAlias("SessionID")
    //@XStreamConverter(value = XStreamCDataConverter.class)
    //@JacksonXmlProperty(localName = "SessionID")
    //@JacksonXmlCData
    private String sessionId;

    /**
     * 微信用户账号的OpenID.
     */
    //@XStreamAlias("OpenID")
    //@XStreamConverter(value = XStreamCDataConverter.class)
    //@JacksonXmlProperty(localName = "OpenID")
    //@JacksonXmlCData
    private String openId;

    //@XStreamAlias("HardWare")
    //@JacksonXmlProperty(localName = "HardWare")
//    private HardWare hardWare = new HardWare();

    /**
     * 请求类型.
     * 0：退订设备状态；
     * 1：心跳；（心跳的处理方式跟订阅一样）
     * 2：订阅设备状态
     */
    //@XStreamAlias("OpType")
    //@JacksonXmlProperty(localName = "OpType")
    private Integer opType;

    /**
     * 设备状态.
     * 0：未连接；1：已连接
     */
    //@XStreamAlias("DeviceStatus")
    //@JacksonXmlProperty(localName = "DeviceStatus")
    private Integer deviceStatus;

    ///////////////////////////////////////
    // 小程序 审核事件
    ///////////////////////////////////////
    /**
     * 审核成功时的时间（整形），时间戳
     */
    //@XStreamAlias("SuccTime")
    //@JacksonXmlProperty(localName = "SuccTime")
    private Long successTime;

    /**
     * 审核失败的原因
     */
    //@XStreamAlias("Reason")
    //@JacksonXmlProperty(localName = "Reason")
    private String reason;

    ///////////////////////////////////////
    // 扫一扫事件推送
    ///////////////////////////////////////
    /**
     * 商品编码标准
     */
    //@XStreamAlias("KeyStandard")
    //@JacksonXmlProperty(localName = "KeyStandard")
    private String keyStandard;
    /**
     * 商品编码内容
     */
    //@XStreamAlias("KeyStr")
    //@JacksonXmlProperty(localName = "KeyStr")
    private String keyStr;

    /**
     * 用户在微信内设置的国家
     */
    //@XStreamAlias("Country")
    //@JacksonXmlProperty(localName = "Country")
    private String country;

    /**
     * 用户在微信内设置的省份
     */
    //@XStreamAlias("Province")
    //@JacksonXmlProperty(localName = "Province")
    private String province;

    /**
     * 用户在微信内设置的城市
     */
    //@XStreamAlias("City")
    //@JacksonXmlProperty(localName = "City")
    private String city;

    /**
     * 用户的性别，1为男性，2为女性，0代表未知
     */
    //@XStreamAlias("Sex")
    //@JacksonXmlProperty(localName = "Sex")
    private String sex;

    /**
     * 打开商品主页的场景，1为扫码，2为其他打开场景（如会话、收藏或朋友圈）
     */
    //@XStreamAlias("Scene")
    //@JacksonXmlProperty(localName = "Scene")
    private String scene;

    /**
     * 调用“获取商品二维码接口”时传入的extinfo，为标识参数
     */
    //@XStreamAlias("ExtInfo")
    //@JacksonXmlProperty(localName = "ExtInfo")
    private String extInfo;

    /**
     * 用户的实时地理位置信息（目前只精确到省一级），可在国家统计局网站查到对应明细： http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201504/t20150415_712722.html
     */
    //@XStreamAlias("RegionCode")
    //@JacksonXmlProperty(localName = "RegionCode")
    private String regionCode;

    /**
     * 审核未通过的原因.
     */
    //@XStreamAlias("ReasonMsg")
    //@JacksonXmlProperty(localName = "ReasonMsg")
    private String reasonMsg;

    /**
     * 给用户发菜单消息类型的客服消息后，用户所点击的菜单ID.
     */
    //@XStreamAlias("bizmsgmenuid")
    //@JacksonXmlProperty(localName = "bizmsgmenuid")
    private String bizMsgMenuId;

    /*------------------ 电子发票 ------------------*/
    /**
     * 授权成功的订单号，与失败订单号两者必显示其一
     */
    //@XStreamAlias("SuccOrderId")
    //@JacksonXmlProperty(localName = "SuccOrderId")
    private String succOrderId;

    /**
     * 授权失败的订单号，与成功订单号两者必显示其一
     */
    //@XStreamAlias("FailOrderId")
    //@JacksonXmlProperty(localName = "FailOrderId")
    private String failOrderId;

    /**
     * 获取授权页链接的AppId
     */
    //@XStreamAlias("AuthorizeAppId")
    //@JacksonXmlProperty(localName = "AuthorizeAppId")
    private String authorizeAppId;

    /**
     * 授权来源，web：公众号开票，app：app开票，wxa：小程序开票，wap：h5开票
     */
    //@XStreamAlias("source")
    //@JacksonXmlProperty(localName = "source")
    private String source;

    /**
     * 发票请求流水号，唯一识别发票请求的流水号
     */
    //@XStreamAlias("fpqqlsh")
    //@JacksonXmlProperty(localName = "fpqqlsh")
    private String fpqqlsh;

    /**
     * 纳税人识别码
     */
    //@XStreamAlias("nsrsbh")
    //@JacksonXmlProperty(localName = "nsrsbh")
    private String nsrsbh;

    /**
     * 加密消息
     */
    //@XStreamAlias("Encrypt")
    //@JacksonXmlProperty(localName = "Encrypt")
    private String encrypt;

    public static WxMpXmlMessage fromXml(String xml) {
        //修改微信变态的消息内容格式，方便解析
//        xml = xml.replace("</PicList><PicList>", "");
//        final WxMpXmlMessage xmlMessage = XStreamTransformer.fromXml(WxMpXmlMessage.class, xml);
//        xmlMessage.setAllFieldsMap(XmlUtils.xml2Map(xml));
        return null;
    }

    public static WxMpXmlMessage fromXml(InputStream is) {
        return null;
    }

    /**
     * 从加密字符串转换.
     *
     * @param encryptedXml      密文
     * @param wxMpConfigStorage 配置存储器对象
     * @param timestamp         时间戳
     * @param nonce             随机串
     * @param msgSignature      签名串
     */
//    public static WxMpXmlMessage fromEncryptedXml(String encryptedXml, WxMpConfigStorage wxMpConfigStorage,
//                                                  String timestamp, String nonce, String msgSignature) {
//        WxMpCryptUtil cryptUtil = new WxMpCryptUtil(wxMpConfigStorage);
//        String plainText = cryptUtil.decryptXml(msgSignature, timestamp, nonce, encryptedXml);
//        log.debug("解密后的原始xml消息内容：{}", plainText);
//        return fromXml(plainText);
//    }
//
//    public static WxMpXmlMessage fromEncryptedXml(InputStream is, WxMpConfigStorage wxMpConfigStorage, String timestamp,
//                                                  String nonce, String msgSignature) {
//        try {
//            return fromEncryptedXml(IOUtils.toString(is, StandardCharsets.UTF_8), wxMpConfigStorage, timestamp, nonce, msgSignature);
//        } catch (IOException e) {
//            throw new WxRuntimeException(e);
//        }
//    }
//
//    public WxMpXmlMessage decryptField(WxMpConfigStorage wxMpConfigStorage,
//                                       String timestamp, String nonce, String msgSignature) {
//        WxMpCryptUtil cryptUtil = new WxMpCryptUtil(wxMpConfigStorage);
//        String plainText = cryptUtil.decryptContent(msgSignature, timestamp, nonce, this.encrypt);
//        log.debug("解密后的原始xml消息内容：{}", plainText);
//        return fromXml(plainText);
//    }

    /**
     * <pre>
     * 当接受用户消息时，可能会获得以下值：
     * {@link WxConsts.XmlMsgType#TEXT}
     * {@link WxConsts.XmlMsgType#IMAGE}
     * {@link WxConsts.XmlMsgType#VOICE}
     * {@link WxConsts.XmlMsgType#VIDEO}
     * {@link WxConsts.XmlMsgType#LOCATION}
     * {@link WxConsts.XmlMsgType#LINK}
     * {@link WxConsts.XmlMsgType#EVENT}
     * </pre>
     */
    public String getMsgType() {
        return this.msgType;
    }

    /**
     * <pre>
     * 当发送消息的时候使用：
     * {@link WxConsts.XmlMsgType#TEXT}
     * {@link WxConsts.XmlMsgType#IMAGE}
     * {@link WxConsts.XmlMsgType#VOICE}
     * {@link WxConsts.XmlMsgType#VIDEO}
     * {@link WxConsts.XmlMsgType#NEWS}
     * {@link WxConsts.XmlMsgType#MUSIC}
     * </pre>
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}
