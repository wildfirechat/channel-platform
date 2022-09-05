package com.github.niefy.modules.wx.port;

import cn.wildfirechat.pojos.OutputGetChannelInfo;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.checkerframework.checker.units.qual.C;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Data
public class WxMpMenu implements Serializable {
    private static final long serialVersionUID = -5794350513426702252L;

    @SerializedName("menu")
    private WxMpConditionalMenu menu;

    @SerializedName("conditionalmenu")
    private List<WxMpConditionalMenu> conditionalMenu;

    public static WxMpMenu fromWFMenu(List<OutputGetChannelInfo.OutputMenu> menus) {
        WxMpMenu mpMenu = new WxMpMenu();
        mpMenu.menu = new WxMpConditionalMenu();
        mpMenu.menu.buttons = new ArrayList<>();
        if (menus != null) {
            menus.forEach(outputMenu -> mpMenu.menu.buttons.add(WxMenuButton.fromWFMenu(outputMenu)));
        }
        return mpMenu;
    }

    public static WxMpMenu fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, WxMpMenu.class);
    }

    @Override
    public String toString() {
        return this.toJson();
    }

    public String toJson() {
        return WxGsonBuilder.create().toJson(this);
    }

    @Data
    public static class WxMpConditionalMenu implements Serializable {
        private static final long serialVersionUID = -2279946921755382289L;

        @SerializedName("button")
        private List<WxMenuButton> buttons;
        @SerializedName("matchrule")
        private WxMenuRule rule;
        @SerializedName("menuid")
        private String menuId;

        @Override
        public String toString() {
            return WxMpGsonBuilder.create().toJson(this);
        }

    }

}
