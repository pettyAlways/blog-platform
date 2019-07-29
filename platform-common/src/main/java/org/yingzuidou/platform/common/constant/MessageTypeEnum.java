package org.yingzuidou.platform.common.constant;

/**
 * 类功能描述
 * 消息类型
 *
 * @author 鹰嘴豆
 * @date 2019/7/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public enum MessageTypeEnum {

    /**
     * 文章修改
     */
    ARTICLEEDIT("文章修改", "1"),

    /**
     * 文章删除
     */
    ARTICLEDEL("文章删除", "2"),

    /**
     * 知识库修改
     */
    KNOWLEDGEEDIT("知识库修改", "3"),

    /**
     * 知识库删除
     */
    KNOWLEDGEDEL("知识库删除", "4"),

    /**
     * 分类修改
     */
    CATEGORYEDIT("分类修改", "5"),

    /**
     * 分类删除
     */
    CATEGORYDEL("分类删除", "6"),

    /**
     * 知识库加入
     */
    JOINKNOWLEDGE("知识库加入", "7"),

    /**
     * 申请作者
     */
    BEAUTHOR("申请作者", "8"),

    /**
     * 移除参与者
     */
    REMOVEPARTICIPANT("参与者移除", "9");


    /**
     * 用户状态
     */
    private String key;

    /**
     * 状态值
     */
    private String value;

    MessageTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
