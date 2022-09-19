package com.github.niefy.modules.wx.manage;

import cn.wildfirechat.pojos.SendMessageResult;
import cn.wildfirechat.sdk.model.IMResult;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.R;
import com.github.niefy.modules.wx.entity.WxMsg;
import com.github.niefy.modules.wx.form.WxMsgReplyForm;
import com.github.niefy.modules.wx.service.MsgReplyService;
import com.github.niefy.modules.wx.service.WxMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.headers.Header;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 消息
 *
 * @author niefy
 * @date 2020-05-14 17:28:34
 */
@RestController
@RequestMapping("/manage/wxMsg")
@Api(tags = {"公众号消息记录-管理后台"})
public class WxMsgManageController {
    @Autowired
    private WxMsgService wxMsgService;
    @Autowired
    private MsgReplyService msgReplyService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("wx:wxmsg:list")
    @ApiOperation(value = "列表")
    public R list(@RequestHeader String appid, @RequestParam Map<String, Object> params){
        params.put("appid",appid);
        PageUtils page = wxMsgService.queryPage(params);

        return R.ok().put("page", page);
    }

    @PostMapping("/send/{appid}")
    @RequiresPermissions("wx:wxmsg:save")
    @ApiOperation(value = "发送")
    public R send(@PathVariable("appid") String appid, @RequestBody String textMessageContent){
        IMResult<SendMessageResult> result = wxMsgService.send(appid, textMessageContent);
        if (result != null){
            if (result.code == 0){
                return R.ok();
            }else {
                return R.error(result.code, result.msg);
            }
        }
        return R.error(-1, "unknown error");
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("wx:wxmsg:info")
    @ApiOperation(value = "详情")
    public R info(@RequestHeader String appid,@PathVariable("id") Long id){
		WxMsg wxMsg = wxMsgService.getById(id);

        return R.ok().put("wxMsg", wxMsg);
    }

    /**
     * 回复
     */
    @PostMapping("/reply")
    @RequiresPermissions("wx:wxmsg:save")
    @ApiOperation(value = "回复")
    public R reply(@RequestHeader String appid,@RequestBody WxMsgReplyForm form){

        msgReplyService.reply(appid, form.getOpenid(),form.getReplyType(),form.getReplyContent());
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("wx:wxmsg:delete")
    @ApiOperation(value = "删除")
    public R delete(@RequestHeader String appid,@RequestBody Long[] ids){
		wxMsgService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
