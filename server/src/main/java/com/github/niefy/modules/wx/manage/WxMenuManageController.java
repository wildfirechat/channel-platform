package com.github.niefy.modules.wx.manage;

import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.OutputGetChannelInfo;
import cn.wildfirechat.proto.ProtoConstants;
import cn.wildfirechat.sdk.ChannelServiceApi;
import cn.wildfirechat.sdk.model.IMResult;
import com.github.niefy.common.utils.R;
import com.github.niefy.modules.wx.port.WxErrorException;
import com.github.niefy.modules.wx.port.WxMenu;
import com.github.niefy.modules.wx.port.WxMenuButton;
import com.github.niefy.modules.wx.port.WxMpMenu;
import com.github.niefy.modules.wx.service.WxAccountService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * 公众号菜单管理
 * 官方文档：https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Creating_Custom-Defined_Menu.html
 * WxJava开发文档：https://github.com/-Group/WxJava/wiki/MP_自定义菜单管理
 */
@RestController
@RequestMapping("/manage/wxMenu")
@RequiredArgsConstructor
@Api(tags = {"公众号菜单-管理后台"})
public class WxMenuManageController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WxAccountService wxAccountService;
    /**
     * 获取公众号菜单
     */
    @GetMapping("/getMenu")
    @ApiOperation(value = "获取公众号菜单")
    public R getMenu(@CookieValue String appid) throws Exception {
        ChannelServiceApi api = wxAccountService.getApi(appid);
        IMResult<OutputGetChannelInfo> imResult = api.getChannelInfo();
        if (imResult != null && imResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
            WxMpMenu wxMpMenu = WxMpMenu.fromWFMenu(imResult.getResult().menus);
            return R.ok().put(wxMpMenu);
        }

        return R.ok();
    }

    /**
     * 创建、更新菜单
     */
    @PostMapping("/updateMenu")
    @RequiresPermissions("wx:menu:save")
    @ApiOperation(value = "创建、更新菜单")
    public R updateMenu(@CookieValue String appid,@RequestBody WxMenu wxMenu) throws Exception {
        ChannelServiceApi api = wxAccountService.getApi(appid);

        List<OutputGetChannelInfo.OutputMenu> menus = new ArrayList<>();
        if (wxMenu != null && wxMenu.getButtons() != null) {
            wxMenu.getButtons().forEach(wxMenuButton -> {
                if (StringUtils.isEmpty(wxMenuButton.getMenuId())) {
                    wxMenuButton.setMenuId(UUID.randomUUID().toString());
                }
                menus.add(wxMenuButton.toWFMenu());
            });
        }
        String menuStr = new Gson().toJson(menus);

        api.modifyChannelInfo(ProtoConstants.ModifyChannelInfoType.Modify_Channel_Menu, menuStr);
        return R.ok();
    }
}
