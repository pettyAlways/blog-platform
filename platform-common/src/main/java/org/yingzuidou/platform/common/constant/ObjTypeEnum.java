package org.yingzuidou.platform.common.constant;

import java.util.Objects;

public enum ObjTypeEnum {
    /**
     * 知识库
     */
    KNOWLEDGE("知识库", "1"),

    /**
     * 文章
     */
    ARTICLE("文章", "2"),

    /**
     * 分类
     */
    CATEGORY("分类", "3"),

    /**
     * 用户
     */
    USER("用户", "4");

    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    private ObjTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static String getKey(String value) {
        for (ObjTypeEnum operTypeEnum :ObjTypeEnum.values()) {
            if (Objects.equals(operTypeEnum.value, value)) {
                return operTypeEnum.key;
            }
        }
        return null;
    }
}
