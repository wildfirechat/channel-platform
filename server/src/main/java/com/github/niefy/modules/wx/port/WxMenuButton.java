package com.github.niefy.modules.wx.port;

import cn.wildfirechat.pojos.OutputGetChannelInfo;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Data
public class WxMenuButton implements Serializable {
    private static final long serialVersionUID = -1070939403109776555L;

    public OutputGetChannelInfo.OutputMenu toWFMenu() {
        OutputGetChannelInfo.OutputMenu wfMenu = new OutputGetChannelInfo.OutputMenu();
        wfMenu.menuId = menuId;
        wfMenu.type = type;
        wfMenu.name = name;
        wfMenu.url = url;
        wfMenu.key = key;
        wfMenu.mediaId = mediaId;
        wfMenu.articleId = articleId;
        wfMenu.appId = appId;
        wfMenu.appPage = pagePath;
        wfMenu.extra = extra;
        if (subButtons != null && !subButtons.isEmpty()) {
            wfMenu.subMenus = new ArrayList<>();
            subButtons.forEach(wxMenuButton -> wfMenu.subMenus.add(wxMenuButton.toWFMenu()));
        }
        return wfMenu;
    }

    public static WxMenuButton fromWFMenu(OutputGetChannelInfo.OutputMenu wfMenu) {
        WxMenuButton button = new WxMenuButton();
        button.menuId = wfMenu.menuId;
        button.type = wfMenu.type;
        button.name = wfMenu.name;
        button.key = wfMenu.key;
        button.url = wfMenu.url;
        button.mediaId = wfMenu.mediaId;
        button.articleId = wfMenu.articleId;
        button.appId = wfMenu.appId;
        button.pagePath = wfMenu.appPage;
        button.extra = wfMenu.extra;
        button.subButtons = new ArrayList<>();
        if (wfMenu.subMenus != null) {
            wfMenu.subMenus.forEach(outputMenu -> button.subButtons.add(fromWFMenu(outputMenu)));
        }
        return button;
    }

    @SerializedName("menu_id")
    private String menuId;
    /**
     * <pre>
     * 菜单的响应动作类型.
     * view表示网页类型，
     * click表示点击类型，
     * miniprogram表示小程序类型
     * </pre>
     */
    private String type;

    /**
     * 菜单标题，不超过16个字节，子菜单不超过60个字节.
     */
    private String name;

    /**
     * <pre>
     * 菜单KEY值，用于消息接口推送，不超过128字节.
     * click等点击类型必须
     * </pre>
     */
    private String key;

    /**
     * <pre>
     * 网页链接.
     * 用户点击菜单可打开链接，不超过1024字节。type为miniprogram时，不支持小程序的老版本客户端将打开本url。
     * view、miniprogram类型必须
     * </pre>
     */
    private String url;

    /**
     * <pre>
     * 调用新增永久素材接口返回的合法media_id.
     * media_id类型和view_limited类型必须
     * </pre>
     */
    @SerializedName("media_id")
    private String mediaId;

    /**
     * <pre>
     * 调用发布图文接口获得的article_id.
     * article_id类型和article_view_limited类型必须
     * </pre>
     */
    @SerializedName("article_id")
    private String articleId;

    /**
     * <pre>
     * 小程序的appid.
     * miniprogram类型必须
     * </pre>
     */
    @SerializedName("appid")
    private String appId;

    /**
     * <pre>
     * 小程序的页面路径.
     * miniprogram类型必须
     * </pre>
     */
    @SerializedName("pagepath")
    private String pagePath;

    private String extra;

    @SerializedName("sub_button")
    private List<WxMenuButton> subButtons = new ArrayList<>();

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }

}