package org.yingzuidou.platform.common.constant;

public enum HandleResultEnum {

    /**
     * 新增
     */
    PASS("通过", "1"),

    /**
     * 删除
     */
    NOPASS("不通过", "2");

    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    private HandleResultEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
