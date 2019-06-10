package org.yingzuidou.cms.cmsweb.constant;

/**
 * 类功能描述
 * 是否删除枚举
 *
 * @author 鹰嘴豆
 * @date 2018/11/21
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public enum IsDeleteEnum {
    // 账户正常状态
    DELETE("删除", "Y"),

    // 账户被锁定
    NOTDELETE("不删除", "N");

    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    private IsDeleteEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
