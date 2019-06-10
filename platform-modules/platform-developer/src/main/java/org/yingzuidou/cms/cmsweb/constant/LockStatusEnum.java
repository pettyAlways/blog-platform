package org.yingzuidou.cms.cmsweb.constant;

/**
 * 类功能描述
 * 锁定状态枚举
 *
 * @author 鹰嘴豆
 * @date 2018/11/21
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public enum LockStatusEnum {
    // 账户正常状态
    NORMAL("正常", "1"),

    // 账户被锁定
    LOCK("锁定", "2"),

    // 账户状态禁用
    INVAILD("无效", "3");

    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    private LockStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
