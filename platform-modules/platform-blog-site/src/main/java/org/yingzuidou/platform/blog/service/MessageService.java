package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.common.entity.MessageEntity;

import java.util.List;

public interface MessageService {

    /**
     * 增加一条消息通知
     *
     * @param type 消息类型
     * @param message 消息内容
     * @param userId 通知用户ID
     * @param reserve 额外的信息
     */
    void addMessage(String type, String message, Integer userId, String reserve);

    /**
     * 增加一条消息通知
     *
     * @param type 消息类型
     * @param message 消息内容
     * @param userId 通知用户ID
     */
    void addMessage(String type, String message, Integer userId);

    /**
     * 获取指定用户下的所有消息
     *
     * @param userId 指定用户ID
     * @return 该用户下所有消息
     */
    List<MessageEntity> listMessage(Integer userId);

    /**
     * 获取指定用户下的已读或未读消息
     *
     * @param userId 指定用户ID
     * @param isRead 是否已读
     * @return 已读或未读消息列表
     */
    List<MessageEntity> listMessage(Integer userId, String isRead);

    /**
     * 设置指定消息已读
     * @param messageId 消息ID
     */
    void setMessageRead(Integer messageId);

    /**
     * 用户的未读消息数量
     *
     * @param userId 用户ID
     * @return 未读消息数量
     */
    Integer countOfMessage(int userId);
}
