package org.yingzuidou.platform.common.constant;

/**
 * 类功能描述
 * 知识库访问方式
 *
 * @author 鹰嘴豆
 * @date 2019/7/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public enum AccessEnum {
    // 账户正常状态
    PRIVATE("私有", "1"),

    // 账户被锁定
    PUBLIC("公开", "2"),

    ENCRYPTION("加密", "3");

    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    private AccessEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
