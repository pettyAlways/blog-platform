package org.yingzuidou.platform.common.constant;

/**
 * 类功能描述
 * 消息是否已读
 *
 * @author 鹰嘴豆
 * @date 2019/7/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public enum IsReadEnum {

    /**
     * 已读
     */
    READ("已读", "Y"),

    /**
     * 未读
     */
    UNREAD("未读", "N");

    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    IsReadEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
