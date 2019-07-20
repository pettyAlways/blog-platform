package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.MessageDTO;
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
     * 批量增加消息
     *
     * @param type 消息类型
     * @param message 消息内容
     * @param userIdList 通知用户列表
     */
    void addBatchMessage(String type, String message, List<Integer> userIdList);

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
     * 设置用户下所有未读消息为可读
     *
     * @param userId 用户ID
     */
    void setMessageAllRead(Integer userId);

    /**
     * 用户的未读消息数量
     *
     * @param userId 用户ID
     * @return 未读消息数量
     */
    Integer countOfMessage(int userId);

    /**
     * 获取用户下的所有已读或未读
     *
     * @param userId 用户ID
     * @param isRead 是否已读
     * @return 已读或未读消息
     */
    List<MessageEntity> userMessages(int userId, String isRead);

    /**
     * 获取用户的所有审核的消息
     *
     * @param userId 用户ID
     * @param isRead 是否已读
     * @return 所有审核的消息
     */
    List<MessageEntity> retrieveAuditMessage(int userId, String isRead);

    /**
     * 删除用户下所有已读的消息
     *
     * @param userId 用户ID
     * @param isRead 已读
     */
    void deleteMessage(int userId, String isRead);
}
