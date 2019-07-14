package org.yingzuidou.platform.common.constant;

import java.util.Objects;

public enum OperTypeEnum {

    /**
     * 新增
     */
    ADD("新增", "1"),

    /**
     * 删除
     */
    DEL("删除", "2"),

    /**
     * 修改
     */
    EDIT("修改", "3"),

    /**
     * 审核
     */
    AUDIT("审核", "4"),

    /**
     * 移除
     */
    REMOVE("移除", "5"),

    /**
     * 申请
     */
    APPLY("申请", "6");

    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    private OperTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static String getKey(String value) {
        for (OperTypeEnum operTypeEnum :OperTypeEnum.values()) {
            if (Objects.equals(operTypeEnum.value, value)) {
                return operTypeEnum.key;
            }
        }
        return null;
    }
}
