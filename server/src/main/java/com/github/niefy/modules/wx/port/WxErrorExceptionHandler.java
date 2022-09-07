package com.github.niefy.modules.wx.port;

/**
 * WxErrorException处理器.
 *
 * @author Daniel Qian
 */
public interface WxErrorExceptionHandler {

    void handle(WxErrorException e);

}

