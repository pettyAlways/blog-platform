package org.yingzuidou.platform.common.constant;

/**
 * 类功能描述
 * 审核结果
 *
 * @author 鹰嘴豆
 * @date 2019/7/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public enum AuditResultEnum {

    /**
     * 申请中
     */
    AUDITING("申请中", "0"),

    /**
     * 通过
     */
    PASS("通过", "1"),

    /**
     * 不通过
     */
    NOPASS("不通过", "1");

    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    AuditResultEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
