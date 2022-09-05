package com.github.niefy.modules.wx.port;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TicketType {
    /**
     * jsapi
     */
    JSAPI("jsapi"),
    /**
     * sdk
     */
    SDK("2"),
    /**
     * 微信卡券
     */
    WX_CARD("wx_card");

    /**
     * type代码
     */
    private final String code;

}
