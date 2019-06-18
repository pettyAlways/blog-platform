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
public enum InUseEnum {
    // 账户正常状态
    USE("启用", "1"),

    // 账户被锁定
    NONUSE("不启用", "2");

    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    private InUseEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
