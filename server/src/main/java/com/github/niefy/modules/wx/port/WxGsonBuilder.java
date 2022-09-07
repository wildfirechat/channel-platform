package com.github.niefy.modules.wx.port;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

public class WxGsonBuilder {
    private static final GsonBuilder INSTANCE = new GsonBuilder();
    private static volatile Gson GSON_INSTANCE;

    static {
        INSTANCE.disableHtmlEscaping();
//        INSTANCE.registerTypeAdapter(WxAccessToken.class, new WxAccessTokenAdapter());
//        INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
//        INSTANCE.registerTypeAdapter(WxMenu.class, new WxMenuGsonAdapter());
//        INSTANCE.registerTypeAdapter(WxMediaUploadResult.class, new WxMediaUploadResultAdapter());
//        INSTANCE.registerTypeAdapter(WxNetCheckResult.class, new WxNetCheckResultGsonAdapter());

    }

    public static Gson create() {
        if (Objects.isNull(GSON_INSTANCE)) {
            synchronized (INSTANCE) {
                if (Objects.isNull(GSON_INSTANCE)) {
                    GSON_INSTANCE = INSTANCE.create();
                }
            }
        }
        return GSON_INSTANCE;
    }

}

