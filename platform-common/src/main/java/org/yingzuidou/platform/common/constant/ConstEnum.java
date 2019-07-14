package org.yingzuidou.platform.common.constant;

/**
 * 类功能描述
 * 系统常量列表
 *
 * @author 鹰嘴豆
 * @date 2019/7/13
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public enum ConstEnum {
    // 账户正常状态
    CONST("常量配置", "1"),

    // 账户被锁定
    ENUM("枚举配置", "2");

    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    private ConstEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
