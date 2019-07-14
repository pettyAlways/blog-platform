package org.yingzuidou.platform.common.constant;

public enum ArticleStatusEnum {

    /**
     * 审核中
     */
    PASSING("审核中", "1"),

    /**
     * 审核不通过
     */
    NOPASS("审核不通过", "2");

    /**
     * 状态的键
     */
    private String key;

    /**
     * 状态的值
     */
    private String value;

    private ArticleStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
