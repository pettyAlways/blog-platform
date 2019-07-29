package org.yingzuidou.platform.common.constant;

/**
 * 类功能描述
 * 审核类型
 *
 * @author 鹰嘴豆
 * @date 2019/7/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public enum AuditEnum {

    /**
     * 成为作者
     */
    AUTHOR("成为作者", "1"),

    /**
     * 加入知识库
     */
    JOIN("加入知识库", "2");

    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    AuditEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
