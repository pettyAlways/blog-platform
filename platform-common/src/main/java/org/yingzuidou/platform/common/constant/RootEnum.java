package org.yingzuidou.platform.common.constant;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/12
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public enum  RootEnum {

    /**
     * 知识库
     */
    KNOWLEDGE("知识库", "1");

    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    private RootEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
