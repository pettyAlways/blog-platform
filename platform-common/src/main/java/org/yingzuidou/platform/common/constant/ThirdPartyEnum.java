package org.yingzuidou.platform.common.constant;

/**
 * 类功能描述
 * 第三方登录
 *
 * @author 鹰嘴豆
 * @date 2019/7/13
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public enum ThirdPartyEnum {

    /**
     * 账号已绑定
     */
    YES("绑定", "Y"),

    /**
     * 账号未绑定
     */
    NO("未绑定", "N");

    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    ThirdPartyEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
