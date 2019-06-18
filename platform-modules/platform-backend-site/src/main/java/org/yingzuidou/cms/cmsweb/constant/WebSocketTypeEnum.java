package org.yingzuidou.cms.cmsweb.constant;

/**
 * 类功能描述
 * WebSocket发送的数据类型
 *
 * @author 鹰嘴豆
 * @date 2018/11/26
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public enum WebSocketTypeEnum {

    // 心跳包类型
    HEART("心跳", "heart"),

    // 账户被锁定
    TIP("消息提示", "tip");

    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    private WebSocketTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
