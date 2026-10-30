package com.github.niefy.modules.sys.form;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 密码表单
 * @author Mark sunlightcs@gmail.com
 */
@Data
public class PasswordForm {
    /**
     * 原密码
     */
    @JSONField(serialize = false)
    private String password;
    /**
     * 新密码
     */
    @JSONField(serialize = false)
    private String newPassword;

}
