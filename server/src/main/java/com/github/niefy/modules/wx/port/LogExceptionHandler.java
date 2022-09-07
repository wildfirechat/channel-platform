package com.github.niefy.modules.wx.port;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogExceptionHandler implements WxErrorExceptionHandler {
    @Override
    public void handle(WxErrorException e) {
        log.error("Error happens", e);
    }

}