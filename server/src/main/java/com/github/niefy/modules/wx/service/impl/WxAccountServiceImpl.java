package com.github.niefy.modules.wx.service.impl;

import cn.wildfirechat.sdk.ChannelServiceApi;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import com.github.niefy.modules.wx.dao.WxAccountMapper;
import com.github.niefy.modules.wx.entity.WxAccount;
import com.github.niefy.modules.wx.port.WxMpDefaultConfigImpl;
import com.github.niefy.modules.wx.port.WxMpService;
import com.github.niefy.modules.wx.service.WxAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("wxAccountService")
public class WxAccountServiceImpl extends ServiceImpl<WxAccountMapper, WxAccount> implements WxAccountService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WxMpService wxMpService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        IPage<WxAccount> page = this.page(
                new Query<WxAccount>().getPage(params),
                new QueryWrapper<WxAccount>()
                        .like(StringUtils.hasText(name), "name", name)
        );

        return new PageUtils(page);
    }

    @Override
    public ChannelServiceApi getApi(String appid) {
        WxAccount account = getById(appid);
        if(account != null) {
            ChannelServiceApi api = new ChannelServiceApi(account.getImurl(), account.getAppid(), account.getSecret());
            return api;
        }
        return null;
    }

    @PostConstruct
    public void loadWxMpConfigStorages(){
        logger.info("?????????????????????...");
        List<WxAccount> accountList = this.list();
        if (accountList == null || accountList.isEmpty()) {
            logger.info("??????????????????????????????????????????????????????");
            return;
        }
        logger.info("?????????{}??????????????????",accountList.size());
        accountList.forEach(this::addAccountToRuntime);
        logger.info("???????????????????????????");
    }

    @Override
    public boolean save(WxAccount entity) {
        Assert.notNull(entity,"WxAccount????????????");
        String appid = entity.getAppid();
        if(this.isAccountInRuntime(appid)){ //?????????appid???????????????
            logger.info("?????????????????????");
            this.addAccountToRuntime(entity);
            return SqlHelper.retBool(this.baseMapper.updateById(entity));
        }else {//?????????appid???????????????
            logger.info("?????????????????????");
            this.addAccountToRuntime(entity);
            return SqlHelper.retBool(this.baseMapper.insert(entity));
        }
    }

    @Override
    public boolean removeByIds(Collection<?> idList) {
        Assert.notEmpty(idList,"WxAccount????????????");

        // ??????wxMpService??????
        logger.info("???????????????????????????");
//        idList.forEach(id-> wxMpService.removeConfigStorage((String) id));

        return SqlHelper.retBool(this.baseMapper.deleteBatchIds(idList));
    }

    /**
     * ???????????????????????????
     * @param appid
     * @return
     */
    private boolean isAccountInRuntime(String appid){
        try {
            return this.baseMapper.selectById(appid) != null;
        }catch (NullPointerException e){// sdk bug???????????????????????????configStorageMap???null?????????
            return false;
        }
    }
    /**
     * ?????????????????????????????????????????????????????????configStorageMap
     * @param entity
     */
    private synchronized void addAccountToRuntime(WxAccount entity){
        String appid = entity.getAppid();
//        WxMpDefaultConfigImpl config = entity.toWxMpConfigStorage();
//        try {
//            wxMpService.addConfigStorage(appid,config);
//        }catch (NullPointerException e){
//            logger.info("????????????configStorageMap...");
//            Map<String, WxMpConfigStorage> configStorages = new HashMap<>(4);
//            configStorages.put(appid,config);
//            wxMpService.setMultiConfigStorages(configStorages,appid);
//        }
    }


}