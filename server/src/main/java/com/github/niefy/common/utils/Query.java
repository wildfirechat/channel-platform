package com.github.niefy.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 查询参数
 * @author Mark sunlightcs@gmail.com
 */
public class Query<T> {

    /**
     * 排序列只允许字母、数字、下划线，且必须以字母或下划线开头。
     * 不能用黑名单过滤：MyBatis-Plus 会把 sidx 直接拼进 ORDER BY，
     * "id,if(1=1,sleep(5),0)" 这类 payload 不含 select/delete 等关键字，黑名单挡不住。
     */
    private static final Pattern SAFE_ORDER_FIELD = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]{0,63}$");

    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        if (params.get(Constant.PAGE) != null) {
            curPage = Long.parseLong((String) params.get(Constant.PAGE));
        }
        if (params.get(Constant.LIMIT) != null) {
            limit = Long.parseLong((String) params.get(Constant.LIMIT));
        }

        //分页对象
        Page<T> page = new Page<>(curPage, limit);

        //分页参数
        params.put(Constant.PAGE, page);

        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = (String) params.get(Constant.ORDER_FIELD);
        String order = (String) params.get(Constant.ORDER);

        //前端字段排序：必须是合法列名，否则忽略排序参数
        if (StringUtils.isNotEmpty(orderField) && !SAFE_ORDER_FIELD.matcher(orderField).matches()) {
            orderField = null;
        }
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if (Constant.ASC.equalsIgnoreCase(order)) {
                return page.addOrder(OrderItem.asc(orderField));
            } else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        //没有排序字段，则不排序
        if (StringUtils.isBlank(defaultOrderField)) {
            return page;
        }

        //默认排序
        if (isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        } else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }
}
